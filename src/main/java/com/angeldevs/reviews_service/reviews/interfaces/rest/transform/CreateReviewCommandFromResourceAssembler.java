package com.angeldevs.reviews_service.reviews.interfaces.rest.transform;

import com.angeldevs.reviews_service.reviews.domain.model.commands.CreateReviewCommand;
import com.angeldevs.reviews_service.reviews.interfaces.rest.resources.CreateReviewResource;

public class CreateReviewCommandFromResourceAssembler {

    public static CreateReviewCommand toCommand(CreateReviewResource resource, String reviewer) {
        return new CreateReviewCommand(
                reviewer,
                resource.getEventName(),
                resource.getEventDate(),
                resource.getContent(),
                resource.getRating(),
                resource.getReviewDate());
    }
}
