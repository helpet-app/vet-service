package com.helpet.service.vet.dto.request;

import com.helpet.validation.annotation.NotBlankOrNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateVetTariffRequest {
    @NotBlank(message = "{validations.not-blank.name-cannot-be-blank}")
    private String name;

    @NotBlankOrNull(message = "{validations.not-blank-or-null.description-cannot-be-blank}")
    private String description;

    @NotNull(message = "{validations.not-null.min-price-cannot-be-null}")
    @Min(value = 0, message = "{validations.min.min-price-must-be-at-least-n}")
    private Double minPrice;

    @NotNull(message = "{validations.not-null.max-price-cannot-be-null}")
    @Min(value = 0, message = "{validations.min.max-price-must-be-at-least-n}")
    private Double maxPrice;
}
