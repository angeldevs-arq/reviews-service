package com.angeldevs.reviews_service.reviews.interfaces.rest.resources;

public record ProfileResource(
        Long id,
        String firstName,
        String lastName,
        String fullName,
        String email,
        String street,
        String number,
        String city,
        String postalCode,
        String country,
        String fullAddress,
        String type) {

}
/*
 * {
 * "id": 0,
 * "firstName": "string",
 * "lastName": "string",
 * "fullName": "string",
 * "email": "string",
 * "street": "string",
 * "number": "string",
 * "city": "string",
 * "postalCode": "string",
 * "country": "string",
 * "fullAddress": "string",
 * "type": "string"
 * }
 */