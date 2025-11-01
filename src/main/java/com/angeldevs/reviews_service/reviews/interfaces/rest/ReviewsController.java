package com.angeldevs.reviews_service.reviews.interfaces.rest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.angeldevs.reviews_service.reviews.domain.model.queries.GetAllReviewsQuery;
import com.angeldevs.reviews_service.reviews.domain.model.queries.GetReviewByIdQuery;
import com.angeldevs.reviews_service.reviews.domain.services.ReviewCommandService;
import com.angeldevs.reviews_service.reviews.domain.services.ReviewQueryService;
import com.angeldevs.reviews_service.reviews.interfaces.rest.resources.CreateReviewResource;
import com.angeldevs.reviews_service.reviews.interfaces.rest.resources.ReviewResource;
import com.angeldevs.reviews_service.reviews.interfaces.rest.transform.CreateReviewCommandFromResourceAssembler;
import com.angeldevs.reviews_service.reviews.interfaces.rest.transform.ReviewResourceFromEntityAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/reviews")
@Tag(name = "Reviews")
public class ReviewsController {

    private final ReviewCommandService commandService;
    private final ReviewQueryService queryService;

    public ReviewsController(
            ReviewCommandService commandService,
            ReviewQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create a new review")
    public ResponseEntity<ReviewResource> create(@Valid @RequestBody CreateReviewResource resource) {
        var command = CreateReviewCommandFromResourceAssembler.toCommand(resource);
        var savedOpt = commandService.handle(command);
        return savedOpt.map(saved -> {
            ReviewResource r = ReviewResourceFromEntityAssembler.toResource(saved);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(saved.getId()).toUri();
            return ResponseEntity.created(location).body(r);
        }).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get review by id")
    public ResponseEntity<ReviewResource> getById(@PathVariable Long id) {
        var opt = queryService.handle(new GetReviewByIdQuery(id));
        return opt.map(e -> ResponseEntity.ok(ReviewResourceFromEntityAssembler.toResource(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "List all reviews")
    public ResponseEntity<List<ReviewResource>> getAll() {
        var list = queryService.handle(new GetAllReviewsQuery());
        var resources = list.stream().map(ReviewResourceFromEntityAssembler::toResource).collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }
}
