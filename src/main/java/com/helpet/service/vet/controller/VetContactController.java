package com.helpet.service.vet.controller;

import com.helpet.security.Role;
import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.vet.dto.request.CreateVetContactRequest;
import com.helpet.service.vet.dto.request.UpdateVetContactRequest;
import com.helpet.service.vet.mapper.VetContactMapper;
import com.helpet.service.vet.service.VetContactService;
import com.helpet.service.vet.storage.model.VetContact;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RolesAllowed(Role.VET)
@RequestMapping("/vet/contacts")
@RestController
public class VetContactController {
    private final VetContactService vetContactService;

    private final VetContactMapper vetContactMapper;

    @Autowired
    public VetContactController(VetContactService vetContactService, VetContactMapper vetContactMapper) {
        this.vetContactService = vetContactService;
        this.vetContactMapper = vetContactMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getVetContacts(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        List<VetContact> vetContacts = vetContactService.getVetContacts(vetId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetContactMapper.mapCollection(vetContacts));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{contact-id}")
    public ResponseEntity<ResponseBody> getVetContact(@PathVariable("contact-id") UUID contactId, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        VetContact vetContact = vetContactService.getVetContact(vetId, contactId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetContactMapper.map(vetContact));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createVetContact(@RequestBody @Valid CreateVetContactRequest createVetContactRequest,
                                                         JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        VetContact newVetContact = vetContactService.createVetContact(vetId, createVetContactRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetContactMapper.map(newVetContact));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PutMapping("/{contact-id}")
    public ResponseEntity<ResponseBody> updateVetContact(@PathVariable("contact-id") UUID contactId,
                                                         @RequestBody @Valid UpdateVetContactRequest updateVetContactRequest,
                                                         JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        VetContact vetContact = vetContactService.updateVetContact(vetId, contactId, updateVetContactRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetContactMapper.map(vetContact));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping("/{contact-id}")
    public ResponseEntity<ResponseBody> deleteVetContact(@PathVariable("contact-id") UUID contactId, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        vetContactService.deleteVetContact(vetId, contactId);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
