package com.angeldevs.reviews_service.reviews.interfaces.rest.resources;

import java.time.LocalDate;

public record CreateReviewResource(Integer rating, String content, LocalDate reviewDate, Integer eventId) {
}
