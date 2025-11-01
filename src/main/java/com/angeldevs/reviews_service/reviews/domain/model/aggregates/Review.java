package com.angeldevs.reviews_service.reviews.domain.model.aggregates;

import java.time.LocalDate;
import java.util.Objects;

import com.angeldevs.reviews_service.reviews.domain.model.commands.CreateReviewCommand;
import com.angeldevs.reviews_service.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Review extends AuditableAbstractAggregateRoot<Review> {
    private String reviewer;

    private String eventName;

    private LocalDate eventDate;

    private String content;

    private Integer rating;

    private LocalDate reviewDate;

    public Review(String reviewer, String eventName, LocalDate eventDate, String content, Integer rating,
            LocalDate reviewDate) {
        validate(reviewer, eventName, content, rating);
        this.reviewer = reviewer;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.content = content;
        this.rating = rating;
        this.reviewDate = reviewDate;
    }

    public Review(CreateReviewCommand command) {
        this(command.reviewer(), command.eventName(), command.eventDate(), command.content(), command.rating(),
                command.reviewDate());
    }

    private void validate(String reviewer, String eventName, String content, Integer rating) {
        if (Objects.isNull(reviewer) || reviewer.isBlank())
            throw new IllegalArgumentException("reviewer cannot be blank");
        if (Objects.isNull(eventName) || eventName.isBlank())
            throw new IllegalArgumentException("eventName cannot be blank");
        if (Objects.isNull(content) || content.isBlank())
            throw new IllegalArgumentException("content cannot be blank");
        if (Objects.isNull(rating) || rating < 1 || rating > 5)
            throw new IllegalArgumentException("rating must be between 1 and 5");
    }
}
