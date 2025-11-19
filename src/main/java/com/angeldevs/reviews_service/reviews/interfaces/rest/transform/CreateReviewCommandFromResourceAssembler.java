package com.angeldevs.reviews_service.reviews.interfaces.rest.transform;

import com.angeldevs.reviews_service.reviews.domain.model.commands.CreateReviewCommand;
import com.angeldevs.reviews_service.reviews.interfaces.rest.resources.CreateReviewResource;
import com.angeldevs.reviews_service.reviews.interfaces.rest.resources.EventResource;
import com.angeldevs.reviews_service.reviews.interfaces.rest.resources.ProfileResource;

public class CreateReviewCommandFromResourceAssembler {

    public static CreateReviewCommand toCommand(CreateReviewResource resource, ProfileResource reviewer,
            EventResource event) {
        return new CreateReviewCommand(
                reviewer.fullName(),
                event.title(),
                event.date(),
                resource.content(),
                resource.rating(),
                resource.reviewDate());
    }
}
