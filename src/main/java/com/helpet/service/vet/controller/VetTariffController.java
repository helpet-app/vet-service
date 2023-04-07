package com.helpet.service.vet.controller;

import com.helpet.security.Role;
import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.vet.dto.request.CreateVetTariffRequest;
import com.helpet.service.vet.dto.request.UpdateVetTariffRequest;
import com.helpet.service.vet.mapper.VetTariffMapper;
import com.helpet.service.vet.service.VetTariffService;
import com.helpet.service.vet.storage.model.VetTariff;
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
@RequestMapping("/vet/tariffs")
@RestController
public class VetTariffController {
    private final VetTariffService vetTariffService;

    private final VetTariffMapper vetTariffMapper;

    @Autowired
    public VetTariffController(VetTariffService vetTariffService, VetTariffMapper vetTariffMapper) {
        this.vetTariffService = vetTariffService;
        this.vetTariffMapper = vetTariffMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getVetTariffs(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        List<VetTariff> vetTariffs = vetTariffService.getVetTariffs(vetId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetTariffMapper.mapCollection(vetTariffs));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{tariff-id}")
    public ResponseEntity<ResponseBody> getVetTariff(@PathVariable("tariff-id") UUID tariffId, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        VetTariff vetTariff = vetTariffService.getVetTariff(vetId, tariffId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetTariffMapper.map(vetTariff));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createVetTariff(@RequestBody @Valid CreateVetTariffRequest createVetTariffRequest,
                                                        JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        VetTariff newVetTariff = vetTariffService.createVetTariff(vetId, createVetTariffRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetTariffMapper.map(newVetTariff));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PutMapping("/{tariff-id}")
    public ResponseEntity<ResponseBody> updateVetTariff(@PathVariable("tariff-id") UUID tariffId,
                                                        @RequestBody @Valid UpdateVetTariffRequest updateVetTariffRequest,
                                                        JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        VetTariff vetTariff = vetTariffService.updateVetTariff(vetId, tariffId, updateVetTariffRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetTariffMapper.map(vetTariff));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping("/{tariff-id}")
    public ResponseEntity<ResponseBody> deleteVetTariff(@PathVariable("tariff-id") UUID tariffId, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        vetTariffService.deleteVetTariff(vetId, tariffId);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
