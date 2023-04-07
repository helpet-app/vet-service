package com.helpet.service.vet.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateScheduleSlotRequest {
    @NotNull(message = "{validations.not-null.time-slot-id-cannot-be-null}")
    private Integer timeSlotId;

    private Boolean monday = false;

    private Boolean tuesday = false;

    private Boolean wednesday = false;

    private Boolean thursday = false;

    private Boolean friday = false;

    private Boolean saturday = false;

    private Boolean sunday = false;
}
