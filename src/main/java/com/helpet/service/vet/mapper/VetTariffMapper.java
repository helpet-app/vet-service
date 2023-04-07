package com.helpet.service.vet.mapper;

import com.helpet.service.vet.dto.response.VetTariffResponse;
import com.helpet.service.vet.storage.model.VetTariff;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VetTariffMapper extends ClassMapper<VetTariff, VetTariffResponse> {
}
