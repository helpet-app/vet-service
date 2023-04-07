package com.helpet.service.vet.dto.request;

import com.helpet.validation.annotation.Name;
import com.helpet.validation.annotation.NotBlankOrNull;
import lombok.Data;

@Data
public class UpdateVetProfileRequest {
    @Name(message = "{validations.name.name-is-invalid}")
    private String name;

    @NotBlankOrNull(message = "{validations.not-blank-or-null.bio-cannot-be-blank}")
    private String bio;
}
