package com.angeldevs.reviews_service.reviews.interfaces.rest;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.angeldevs.reviews_service.reviews.domain.model.queries.GetAllReviewsQuery;
import com.angeldevs.reviews_service.reviews.domain.model.queries.GetReviewByIdQuery;
import com.angeldevs.reviews_service.reviews.domain.services.ReviewCommandService;
import com.angeldevs.reviews_service.reviews.domain.services.ReviewQueryService;
import com.angeldevs.reviews_service.reviews.interfaces.rest.resources.CreateReviewResource;
import com.angeldevs.reviews_service.reviews.interfaces.rest.resources.ProfileResource;
import com.angeldevs.reviews_service.reviews.interfaces.rest.resources.ReviewResource;
import com.angeldevs.reviews_service.reviews.interfaces.rest.transform.CreateReviewCommandFromResourceAssembler;
import com.angeldevs.reviews_service.reviews.interfaces.rest.transform.ReviewResourceFromEntityAssembler;
import com.angeldevs.reviews_service.shared.infrastructure.JwtUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/reviews")
@Tag(name = "Reviews", description = "Operaciones para crear, obtener y listar reseñas (reviews)")
public class ReviewsController {
        private final ReviewCommandService commandService;
        private final ReviewQueryService queryService;
        private RestClient client;
        @Value("${PROFILE_SERVICE_URL}")
        private String profileServiceUrl;

        public ReviewsController(
                        ReviewCommandService commandService,
                        ReviewQueryService queryService) {
                this.commandService = commandService;
                this.queryService = queryService;
                this.client = RestClient.create();
        }

        @PostMapping
        @Operation(summary = "Create a new review", description = "Crea una nueva reseña. Devuelve 201 con la reseña creada o 400 si los datos son inválidos.", tags = {
                        "Reviews" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Review created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewResource.class))),
                        @ApiResponse(responseCode = "400", description = "Bad request - invalid input", content = @Content)
        })
        public ResponseEntity<ReviewResource> create(@Valid @RequestBody CreateReviewResource resource,
                        @RequestHeader(value = "Authorization") String authHeader) {
                // Decode JWT to get user information
                var token = JwtUtils.decode(authHeader);

                // Get user profile from Profile Service
                var profileResponse = client.get()
                                .uri(profileServiceUrl + "/api/v1/profiles/email/{id}", token.sub())
                                .retrieve()
                                .toEntity(ProfileResource.class);

                ProfileResource profile = profileResponse.getBody();

                var command = CreateReviewCommandFromResourceAssembler.toCommand(resource, profile.fullName());
                var savedOpt = commandService.handle(command);
                return savedOpt.map(saved -> {
                        ReviewResource r = ReviewResourceFromEntityAssembler.toResource(saved);
                        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                        .buildAndExpand(saved.getId()).toUri();
                        return ResponseEntity.created(location).body(r);
                }).orElseGet(() -> ResponseEntity.badRequest().build());
        }

        @GetMapping("/{id}")
        @Operation(summary = "Get review by id", description = "Obtiene una reseña por su identificador. Devuelve 200 con la reseña o 404 si no existe.", tags = {
                        "Reviews" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Review found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewResource.class))),
                        @ApiResponse(responseCode = "404", description = "Review not found", content = @Content)
        })
        public ResponseEntity<ReviewResource> getById(
                        @Parameter(description = "ID de la review", required = true) @PathVariable Long id) {
                var opt = queryService.handle(new GetReviewByIdQuery(id));
                return opt.map(e -> ResponseEntity.ok(ReviewResourceFromEntityAssembler.toResource(e)))
                                .orElseGet(() -> ResponseEntity.notFound().build());
        }

        @GetMapping
        @Operation(summary = "List all reviews", description = "Devuelve la lista completa de reseñas.", tags = {
                        "Reviews" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "List of reviews", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewResource.class)))
        })
        public ResponseEntity<List<ReviewResource>> getAll() {
                var list = queryService.handle(new GetAllReviewsQuery());
                var resources = list.stream().map(ReviewResourceFromEntityAssembler::toResource)
                                .collect(Collectors.toList());
                return ResponseEntity.ok(resources);
        }
}
