package com.helpet.service.vet.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class VetTariffResponse {
    private UUID id;

    private String name;

    private String description;

    private Double minPrice;

    private Double maxPrice;
}
