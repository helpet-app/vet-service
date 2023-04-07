package com.helpet.service.vet.service;

import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.vet.service.error.NotFoundLocalizedError;
import com.helpet.service.vet.storage.model.Vet;
import com.helpet.service.vet.storage.repository.VetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class VetService {
    private final VetRepository vetRepository;

    public VetService(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    public Page<Vet> getVetsByFilter(Set<UUID> tagIds, Pageable pageable) {
        return vetRepository.findAllByFilter(tagIds, pageable);
    }

    public Vet getVet(UUID vetId) throws NotFoundLocalizedException {
        return vetRepository.findVetById(vetId)
                            .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_EXIST));
    }

    public boolean vetExists(UUID vetId) {
        return vetRepository.existsById(vetId);
    }

    public Vet getVetWithTagsById(UUID vetId) throws NotFoundLocalizedException {
        return vetRepository.findVetWithTagsById(vetId)
                            .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_EXIST));
    }

    public Vet saveVet(Vet vet) {
        return vetRepository.save(vet);
    }
}
