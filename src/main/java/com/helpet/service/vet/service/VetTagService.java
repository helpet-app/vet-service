package com.helpet.service.vet.service;

import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.vet.dto.request.AddVetTagsRequest;
import com.helpet.service.vet.dto.request.CreateTagRequest;
import com.helpet.service.vet.dto.request.DeleteVetTagsRequest;
import com.helpet.service.vet.storage.model.Tag;
import com.helpet.service.vet.storage.model.Vet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VetTagService {
    private final VetService vetService;

    private final TagService tagService;

    @Autowired
    public VetTagService(VetService vetService, TagService tagService) {
        this.vetService = vetService;
        this.tagService = tagService;
    }

    public Set<Tag> getVetTags(UUID vetId) throws NotFoundLocalizedException {
        Vet vet = vetService.getVetWithTagsById(vetId);
        return vet.getTags();
    }

    public Set<Tag> addVetTags(UUID vetId, AddVetTagsRequest vetTagsInfo) throws NotFoundLocalizedException {
        Vet vet = vetService.getVetWithTagsById(vetId);

        Set<CreateTagRequest> newTagsRequests = vetTagsInfo.getTags()
                                                           .stream()
                                                           .map(tag -> CreateTagRequest.builder().name(tag).build())
                                                           .collect(Collectors.toSet());

        List<Tag> tags = tagService.createTagsIfNeeded(newTagsRequests);

        vet.getTags().addAll(tags);
        vetService.saveVet(vet);

        return vet.getTags();
    }

    public void deleteVetTags(UUID vetId, DeleteVetTagsRequest vetTagsInfo) throws NotFoundLocalizedException {
        Vet vet = vetService.getVetWithTagsById(vetId);
        List<Tag> vetTags = tagService.getTagsInNames(vetTagsInfo.getTags());
        vetTags.forEach(vet.getTags()::remove);
        vetService.saveVet(vet);
    }
}
