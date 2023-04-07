package com.helpet.service.vet.dto.response;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class VetCardResponse {
    private UUID id;

    private String name;

    private String bio;

    private String avatarUrl;

    private Boolean available;

    private Set<TagResponse> tags;
}
