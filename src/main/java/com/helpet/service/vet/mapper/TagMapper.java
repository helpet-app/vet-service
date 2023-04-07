package com.helpet.service.vet.mapper;

import com.helpet.service.vet.dto.response.TagResponse;
import com.helpet.service.vet.storage.model.Tag;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper extends ClassMapper<Tag, TagResponse> {
}

