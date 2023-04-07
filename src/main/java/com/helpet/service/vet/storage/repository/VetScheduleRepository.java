package com.helpet.service.vet.storage.repository;

import com.helpet.service.vet.storage.model.VetScheduleSlot;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface VetScheduleRepository extends JpaRepository<VetScheduleSlot, UUID> {
    @EntityGraph(attributePaths = "timeSlot")
    List<VetScheduleSlot> findAllByVetIdOrderByTimeSlotId(UUID vetId);

    @EntityGraph(attributePaths = "timeSlot")
    List<VetScheduleSlot> findAllByVetIdAndTimeSlotIdIn(UUID vetId, Set<Integer> timeSlotIds);
}
