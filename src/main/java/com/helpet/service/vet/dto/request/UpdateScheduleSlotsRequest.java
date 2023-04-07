package com.helpet.service.vet.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateScheduleSlotsRequest {
    @NotEmpty(message = "{validations.not-empty.schedule-slots-cannot-be-empty}")
    Set<@Valid UpdateScheduleSlotRequest> scheduleSlots;
}
