package com.helpet.service.vet.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateVetContactRequest {
    @NotBlank(message = "{validations.not-blank.name-cannot-be-blank}")
    private String name;

    @NotBlank(message = "{validations.not-blank.value-cannot-be-blank}")
    private String value;
}
