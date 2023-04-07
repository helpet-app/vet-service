package com.helpet.service.vet.controller;

import com.helpet.security.Role;
import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.vet.dto.request.AddVetTagsRequest;
import com.helpet.service.vet.dto.request.DeleteVetTagsRequest;
import com.helpet.service.vet.mapper.TagMapper;
import com.helpet.service.vet.service.VetTagService;
import com.helpet.service.vet.storage.model.Tag;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RolesAllowed(Role.VET)
@RequestMapping("/vet/tags")
@RestController
public class VetTagController {
    private final VetTagService vetTagService;

    private final TagMapper tagMapper;

    @Autowired
    public VetTagController(VetTagService vetTagService, TagMapper tagMapper) {
        this.vetTagService = vetTagService;
        this.tagMapper = tagMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getVetTags(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Set<Tag> vetTags = vetTagService.getVetTags(vetId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(tagMapper.mapCollection(vetTags));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> addVetTags(@RequestBody @Valid AddVetTagsRequest addVetTagsRequest,
                                                   JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Set<Tag> tags = vetTagService.addVetTags(vetId, addVetTagsRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(tagMapper.mapCollection(tags));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<ResponseBody> deleteVetTags(@RequestBody @Valid DeleteVetTagsRequest deleteVetTagsRequest,
                                                      JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        vetTagService.deleteVetTags(vetId, deleteVetTagsRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
