package com.angeldevs.reviews_service.reviews.interfaces.rest.resources;

import java.time.LocalDate;

public record EventResource(
        Long id,
        String title,
        LocalDate date,
        String customerName,
        String place,
        String status) {

}
/*
 * {
 * "id": 0,
 * "title": "string",
 * "date": "2025-11-19",
 * "customerName": "string",
 * "place": "string",
 * "status": "string"
 * }
 */