package com.helpet.service.vet.controller;

import com.helpet.service.vet.mapper.*;
import com.helpet.service.vet.service.*;
import com.helpet.service.vet.storage.model.*;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequestMapping("/vets")
@RestController
public class VetCardController {
    private final VetService vetService;

    private final VetContactService vetContactService;

    private final VetTariffService vetTariffService;

    private final VetScheduleService vetScheduleService;

    private final VetTagService vetTagService;

    private final VetCardMapper vetCardMapper;

    private final VetContactMapper vetContactMapper;

    private final VetTariffMapper vetTariffMapper;

    private final VetScheduleSlotMapper vetScheduleSlotMapper;

    private final TagMapper tagMapper;

    @Autowired
    public VetCardController(VetService vetService,
                             VetContactService vetContactService,
                             VetTariffService vetTariffService,
                             VetScheduleService vetScheduleService,
                             VetTagService vetTagService, VetCardMapper vetCardMapper,
                             VetContactMapper vetContactMapper,
                             VetTariffMapper vetTariffMapper,
                             VetScheduleSlotMapper vetScheduleSlotMapper, TagMapper tagMapper) {
        this.vetService = vetService;
        this.vetContactService = vetContactService;
        this.vetTariffService = vetTariffService;
        this.vetScheduleService = vetScheduleService;
        this.vetTagService = vetTagService;
        this.vetCardMapper = vetCardMapper;
        this.vetContactMapper = vetContactMapper;
        this.vetTariffMapper = vetTariffMapper;
        this.vetScheduleSlotMapper = vetScheduleSlotMapper;
        this.tagMapper = tagMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getVetsByFilter(@RequestParam(value = "tag-id", required = false) Set<UUID> tagIds,
                                                        Pageable pageable) {
        Page<Vet> vets = vetService.getVetsByFilter(tagIds, pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetCardMapper.mapPage(vets));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{vet-id}")
    public ResponseEntity<ResponseBody> getVet(@PathVariable("vet-id") UUID vetId) {
        Vet vet = vetService.getVetWithTagsById(vetId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetCardMapper.map(vet));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{vet-id}/contacts")
    public ResponseEntity<ResponseBody> getVetContacts(@PathVariable("vet-id") UUID vetId) {
        List<VetContact> vetContacts = vetContactService.getVetContacts(vetId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetContactMapper.mapCollection(vetContacts));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{vet-id}/tariffs")
    public ResponseEntity<ResponseBody> getVetTariffs(@PathVariable("vet-id") UUID vetId) {
        List<VetTariff> vetTariffs = vetTariffService.getVetTariffs(vetId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetTariffMapper.mapCollection(vetTariffs));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{vet-id}/schedule")
    public ResponseEntity<ResponseBody> getVetSchedule(@PathVariable("vet-id") UUID vetId) {
        List<VetScheduleSlot> vetScheduleSlots = vetScheduleService.getVetSchedule(vetId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vetScheduleSlotMapper.mapCollection(vetScheduleSlots));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{vet-id}/tags")
    public ResponseEntity<ResponseBody> getVetTags(@PathVariable("vet-id") UUID vetId) {
        Set<Tag> vetTags = vetTagService.getVetTags(vetId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(tagMapper.mapCollection(vetTags));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
