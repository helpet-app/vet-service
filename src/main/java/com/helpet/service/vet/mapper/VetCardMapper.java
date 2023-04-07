package com.helpet.service.vet.mapper;

import com.helpet.service.vet.dto.response.VetCardResponse;
import com.helpet.service.vet.storage.model.Vet;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VetCardMapper extends ClassMapper<Vet, VetCardResponse> {
}

