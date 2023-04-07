package com.helpet.service.vet.storage.repository;

import com.helpet.service.vet.storage.model.Vet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface VetRepository extends JpaRepository<Vet, UUID> {
    Optional<Vet> findVetById(UUID id);

    @EntityGraph(attributePaths = "tags")
    Optional<Vet> findVetWithTagsById(UUID id);

    @Query(value = "SELECT * FROM find_all_vets_by_filter(:tagIds)",
           countQuery = "SELECT COUNT(*) FROM find_all_vets_by_filter(:tagIds)",
           nativeQuery = true)
    Page<UUID> findAllVetIdsByFilter(UUID[] tagIds, Pageable pageable);

    @EntityGraph(attributePaths = "tags")
    List<Vet> findAllByIdIn(Collection<UUID> ids);

    default Page<Vet> findAllByFilter(Set<UUID> tagIds, Pageable pageable) {
        Page<UUID> vetIds = findAllVetIdsByFilter(Objects.nonNull(tagIds) ? tagIds.toArray(UUID[]::new) : null,
                                                  PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        return new PageImpl<>(findAllByIdIn(vetIds.getContent()), pageable, vetIds.getTotalElements());
    }
}


