package com.helpet.service.vet.storage.repository;

import com.helpet.service.vet.storage.model.VetTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VetTariffRepository extends JpaRepository<VetTariff, UUID> {
    List<VetTariff> findAllByVetIdOrderByName(UUID vetId);

    Optional<VetTariff> findVetTariffByVetIdAndId(UUID vetId, UUID tariffId);
}
