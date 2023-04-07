package com.helpet.service.vet.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class DeleteScheduleSlotsRequest {
    @NotEmpty(message = "{validations.not-empty.time-slot-ids-cannot-be-empty}")
    Set<@NotNull(message = "{validations.not-null.time-slot-id-cannot-be-null}") Integer> timeSlotIds;
}
