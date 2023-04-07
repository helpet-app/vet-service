package com.helpet.service.vet.storage.repository;

import com.helpet.service.vet.storage.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
}
