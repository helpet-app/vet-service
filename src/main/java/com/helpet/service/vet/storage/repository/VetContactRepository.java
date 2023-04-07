package com.helpet.service.vet.storage.repository;

import com.helpet.service.vet.storage.model.VetContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VetContactRepository extends JpaRepository<VetContact, UUID> {
    List<VetContact> findAllByVetIdOrderByName(UUID vetId);

    Optional<VetContact> findVetContactByVetIdAndId(UUID vetId, UUID contactId);
}
