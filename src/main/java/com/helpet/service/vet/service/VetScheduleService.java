package com.helpet.service.vet.service;

import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.vet.dto.request.*;
import com.helpet.service.vet.service.error.NotFoundLocalizedError;
import com.helpet.service.vet.storage.model.TimeSlot;
import com.helpet.service.vet.storage.model.Vet;
import com.helpet.service.vet.storage.model.VetScheduleSlot;
import com.helpet.service.vet.storage.repository.TimeSlotRepository;
import com.helpet.service.vet.storage.repository.VetScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VetScheduleService {
    private final VetService vetService;

    private final VetScheduleRepository vetScheduleRepository;

    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    public VetScheduleService(VetService vetService, VetScheduleRepository vetScheduleRepository, TimeSlotRepository timeSlotRepository) {
        this.vetService = vetService;
        this.vetScheduleRepository = vetScheduleRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    public List<VetScheduleSlot> getVetSchedule(UUID vetId) throws NotFoundLocalizedException {
        if (!vetService.vetExists(vetId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_EXIST);
        }

        return vetScheduleRepository.findAllByVetIdOrderByTimeSlotId(vetId);
    }

    public List<VetScheduleSlot> createScheduleSlots(UUID vetId, CreateScheduleSlotsRequest scheduleSlotsInfo) throws NotFoundLocalizedException {
        Vet vet = vetService.getVet(vetId);

        scheduleSlotsInfo.getScheduleSlots()
                         .removeIf(slot -> !(slot.getMonday() || slot.getTuesday() || slot.getWednesday() || slot.getThursday() || slot.getFriday() || slot.getSaturday() || slot.getSunday()));

        Map<Integer, TimeSlot> timeSlots = timeSlotRepository.findAllById(scheduleSlotsInfo.getScheduleSlots()
                                                                                           .stream()
                                                                                           .map(CreateScheduleSlotRequest::getTimeSlotId)
                                                                                           .toList())
                                                             .stream()
                                                             .collect(Collectors.toMap(TimeSlot::getId, timeSlot -> timeSlot));


        List<VetScheduleSlot> newVetScheduleSlots = scheduleSlotsInfo.getScheduleSlots()
                                                                     .stream()
                                                                     .map(scheduleSlot -> VetScheduleSlot.builder()
                                                                                                         .timeSlot(timeSlots.get(scheduleSlot.getTimeSlotId()))
                                                                                                         .monday(scheduleSlot.getMonday())
                                                                                                         .tuesday(scheduleSlot.getTuesday())
                                                                                                         .wednesday(scheduleSlot.getWednesday())
                                                                                                         .thursday(scheduleSlot.getThursday())
                                                                                                         .friday(scheduleSlot.getFriday())
                                                                                                         .saturday(scheduleSlot.getSaturday())
                                                                                                         .sunday(scheduleSlot.getSunday())
                                                                                                         .vet(vet)
                                                                                                         .build())
                                                                     .toList();

        return vetScheduleRepository.saveAll(newVetScheduleSlots);
    }

    public List<VetScheduleSlot> updateScheduleSlots(UUID vetId, UpdateScheduleSlotsRequest scheduleSlotsInfo) throws NotFoundLocalizedException {
        if (!vetService.vetExists(vetId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_EXIST);
        }

        scheduleSlotsInfo.getScheduleSlots()
                         .removeIf(slot -> !(slot.getMonday() || slot.getTuesday() || slot.getWednesday() || slot.getThursday() || slot.getFriday() || slot.getSaturday() || slot.getSunday()));

        Map<Integer, VetScheduleSlot> vetScheduleSlots = vetScheduleRepository.findAllByVetIdAndTimeSlotIdIn(vetId,
                                                                                                             scheduleSlotsInfo.getScheduleSlots()
                                                                                                                              .stream()
                                                                                                                              .map(UpdateScheduleSlotRequest::getTimeSlotId)
                                                                                                                              .collect(Collectors.toSet()))
                                                                              .stream()
                                                                              .collect(Collectors.toMap(vetScheduleSlot -> vetScheduleSlot.getTimeSlot()
                                                                                                                                          .getId(),
                                                                                                        vetScheduleSlot -> vetScheduleSlot));

        for (UpdateScheduleSlotRequest updateSlotInfo : scheduleSlotsInfo.getScheduleSlots()) {
            VetScheduleSlot vetScheduleSlot = vetScheduleSlots.get(updateSlotInfo.getTimeSlotId());
            vetScheduleSlot.setMonday(updateSlotInfo.getMonday());
            vetScheduleSlot.setTuesday(updateSlotInfo.getTuesday());
            vetScheduleSlot.setWednesday(updateSlotInfo.getWednesday());
            vetScheduleSlot.setThursday(updateSlotInfo.getThursday());
            vetScheduleSlot.setFriday(updateSlotInfo.getFriday());
            vetScheduleSlot.setSaturday(updateSlotInfo.getSaturday());
            vetScheduleSlot.setSunday(updateSlotInfo.getSunday());
        }

        return vetScheduleRepository.saveAll(vetScheduleSlots.values());
    }

    public void deleteScheduleSlots(UUID vetId, DeleteScheduleSlotsRequest scheduleSlotsInfo) throws NotFoundLocalizedException {
        if (!vetService.vetExists(vetId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_EXIST);
        }

        List<VetScheduleSlot> vetScheduleSlots = vetScheduleRepository.findAllByVetIdAndTimeSlotIdIn(vetId, scheduleSlotsInfo.getTimeSlotIds());

        vetScheduleRepository.deleteAll(vetScheduleSlots);
    }
}
