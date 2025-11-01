package com.angeldevs.reviews_service.reviews.domain.services;

import com.angeldevs.reviews_service.reviews.domain.model.aggregates.Review;
import com.angeldevs.reviews_service.reviews.domain.model.commands.CreateReviewCommand;

import java.util.Optional;

public interface ReviewCommandService {
    Optional<Review> handle(CreateReviewCommand command);
}
