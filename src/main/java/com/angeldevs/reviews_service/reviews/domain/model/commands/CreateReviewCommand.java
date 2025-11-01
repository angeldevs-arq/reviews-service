package com.angeldevs.reviews_service.reviews.domain.model.commands;

import java.time.LocalDate;

public record CreateReviewCommand(
        String reviewer,
        String eventName,
        LocalDate eventDate,
        String content,
        Integer rating,
        LocalDate reviewDate) {
}
