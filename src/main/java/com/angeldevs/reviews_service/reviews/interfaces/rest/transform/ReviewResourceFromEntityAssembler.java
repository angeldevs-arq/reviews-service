package com.angeldevs.reviews_service.reviews.interfaces.rest.transform;

import com.angeldevs.reviews_service.reviews.domain.model.aggregates.Review;
import com.angeldevs.reviews_service.reviews.interfaces.rest.resources.ReviewResource;

public class ReviewResourceFromEntityAssembler {

    public static ReviewResource toResource(Review entity) {
        if (entity == null)
            return null;
        ReviewResource r = new ReviewResource();
        r.setId(entity.getId());
        r.setReviewer(entity.getReviewer());
        r.setEventName(entity.getEventName());
        r.setEventDate(entity.getEventDate());
        r.setContent(entity.getContent());
        r.setRating(entity.getRating());
        r.setReviewDate(entity.getReviewDate());
        return r;
    }
}
