package com.helpet.service.vet.mapper;

import com.helpet.service.vet.dto.response.VetContactResponse;
import com.helpet.service.vet.storage.model.VetContact;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VetContactMapper extends ClassMapper<VetContact, VetContactResponse> {
}
