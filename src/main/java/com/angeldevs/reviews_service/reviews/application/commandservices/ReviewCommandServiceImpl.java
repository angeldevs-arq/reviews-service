package com.angeldevs.reviews_service.reviews.application.commandservices;

import com.angeldevs.reviews_service.reviews.domain.model.aggregates.Review;
import com.angeldevs.reviews_service.reviews.domain.model.commands.CreateReviewCommand;
import com.angeldevs.reviews_service.reviews.domain.services.ReviewCommandService;
import com.angeldevs.reviews_service.reviews.infrastructure.persistence.jpa.repositories.ReviewRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final ReviewRepository reviewRepository;

    public ReviewCommandServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Optional<Review> handle(CreateReviewCommand command) {
        Review review = new Review(command);
        Review saved = reviewRepository.save(review);
        return Optional.ofNullable(saved);
    }
}
