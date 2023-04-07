package com.helpet.service.vet.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class VetResponse {
    private UUID id;

    private String name;

    private String bio;

    private String avatarUrl;

    private Boolean available;
}
