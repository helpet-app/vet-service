package com.helpet.service.vet.controller;

import com.helpet.security.Role;
import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.vet.dto.request.CreateVetProfileRequest;
import com.helpet.service.vet.dto.request.UpdateVetProfileRequest;
import com.helpet.service.vet.mapper.VetMapper;
import com.helpet.service.vet.service.VetProfileService;
import com.helpet.service.vet.storage.model.Vet;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RolesAllowed(Role.VET)
@RequestMapping("/vet/profile")
@RestController
public class VetProfileController {
    private final VetProfileService vetProfileService;

    private final VetMapper vetMapper;

    @Autowired
    public VetProfileController(VetProfileService vetProfileService, VetMapper vetMapper) {
        this.vetProfileService = vetProfileService;
        this.vetMapper = vetMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getProfile(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Vet profile = vetProfileService.getProfile(vetId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetMapper.map(profile));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createProfile(@RequestBody @Valid CreateVetProfileRequest createVetProfileRequest,
                                                      JwtAuthenticationToken jwtAuthenticationToken) {
        UUID accountId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Vet profile = vetProfileService.createProfile(accountId, createVetProfileRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetMapper.map(profile));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<ResponseBody> updateProfile(@RequestBody @Valid UpdateVetProfileRequest updateVetProfileRequest,
                                                      JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Vet profile = vetProfileService.updateProfile(vetId, updateVetProfileRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetMapper.map(profile));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PatchMapping("/availability/toggle")
    public ResponseEntity<ResponseBody> toggleAvailability(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        vetProfileService.toggleAvailability(vetId);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
