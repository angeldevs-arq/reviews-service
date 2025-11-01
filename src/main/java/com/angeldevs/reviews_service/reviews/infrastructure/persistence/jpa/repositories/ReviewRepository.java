package com.angeldevs.reviews_service.reviews.infrastructure.persistence.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.angeldevs.reviews_service.reviews.domain.model.aggregates.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAll();
}
