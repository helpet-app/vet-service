package com.helpet.service.vet.mapper;

import com.helpet.service.vet.dto.response.VetScheduleSlotResponse;
import com.helpet.service.vet.storage.model.VetScheduleSlot;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VetScheduleSlotMapper extends ClassMapper<VetScheduleSlot, VetScheduleSlotResponse> {
}