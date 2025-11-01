package com.angeldevs.reviews_service.reviews.domain.services;

import com.angeldevs.reviews_service.reviews.domain.model.aggregates.Review;
import com.angeldevs.reviews_service.reviews.domain.model.queries.GetAllReviewsQuery;
import com.angeldevs.reviews_service.reviews.domain.model.queries.GetReviewByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ReviewQueryService {
    Optional<Review> handle(GetReviewByIdQuery query);

    List<Review> handle(GetAllReviewsQuery query);
}
