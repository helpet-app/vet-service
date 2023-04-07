package com.helpet.service.vet.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class TagResponse {
    private UUID id;

    private String name;
}
