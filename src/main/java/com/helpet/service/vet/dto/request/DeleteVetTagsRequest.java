package com.helpet.service.vet.dto.request;

import com.helpet.validation.annotation.Word;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class DeleteVetTagsRequest {
    @NotEmpty(message = "{validations.not-empty.tags-cannot-be-empty}")
    Set<@Word(message = "{validations.word.tag-must-be-word}") String> tags;
}