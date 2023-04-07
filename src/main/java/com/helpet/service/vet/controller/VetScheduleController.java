package com.helpet.service.vet.controller;

import com.helpet.security.Role;
import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.vet.dto.request.CreateScheduleSlotsRequest;
import com.helpet.service.vet.dto.request.DeleteScheduleSlotsRequest;
import com.helpet.service.vet.dto.request.UpdateScheduleSlotsRequest;
import com.helpet.service.vet.mapper.VetScheduleSlotMapper;
import com.helpet.service.vet.service.VetScheduleService;
import com.helpet.service.vet.storage.model.VetScheduleSlot;
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
@RequestMapping("/vet/schedule")
@RestController
public class VetScheduleController {
    private final VetScheduleService vetScheduleService;

    private final VetScheduleSlotMapper vetScheduleSlotMapper;

    @Autowired
    public VetScheduleController(VetScheduleService vetScheduleService, VetScheduleSlotMapper vetScheduleSlotMapper) {
        this.vetScheduleService = vetScheduleService;
        this.vetScheduleSlotMapper = vetScheduleSlotMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getSchedule(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        List<VetScheduleSlot> vetSchedule = vetScheduleService.getVetSchedule(vetId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetScheduleSlotMapper.mapCollection(vetSchedule));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createScheduleSlots(@RequestBody @Valid CreateScheduleSlotsRequest createScheduleSlotsRequest,
                                                            JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        List<VetScheduleSlot> vetSchedule = vetScheduleService.createScheduleSlots(vetId, createScheduleSlotsRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetScheduleSlotMapper.mapCollection(vetSchedule));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ResponseBody> updateScheduleSlots(@RequestBody @Valid UpdateScheduleSlotsRequest updateScheduleSlotsRequest,
                                                            JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        List<VetScheduleSlot> vetSchedule = vetScheduleService.updateScheduleSlots(vetId, updateScheduleSlotsRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetScheduleSlotMapper.mapCollection(vetSchedule));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseBody> deleteScheduleSlots(@RequestBody @Valid DeleteScheduleSlotsRequest deleteScheduleSlotsRequest,
                                                            JwtAuthenticationToken jwtAuthenticationToken) {
        UUID vetId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        vetScheduleService.deleteScheduleSlots(vetId, deleteScheduleSlotsRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
