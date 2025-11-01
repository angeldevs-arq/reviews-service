package com.angeldevs.reviews_service.reviews.application.queryservices;

import com.angeldevs.reviews_service.reviews.domain.model.aggregates.Review;
import com.angeldevs.reviews_service.reviews.domain.model.queries.GetAllReviewsQuery;
import com.angeldevs.reviews_service.reviews.domain.model.queries.GetReviewByIdQuery;
import com.angeldevs.reviews_service.reviews.domain.services.ReviewQueryService;
import com.angeldevs.reviews_service.reviews.infrastructure.persistence.jpa.repositories.ReviewRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;

    public ReviewQueryServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Optional<Review> handle(GetReviewByIdQuery query) {
        return reviewRepository.findById(query.reviewId());
    }

    @Override
    public List<Review> handle(GetAllReviewsQuery query) {
        return reviewRepository.findAll();
    }
}
