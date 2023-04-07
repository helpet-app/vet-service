package com.helpet.service.vet.service;

import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.vet.dto.request.CreateVetTariffRequest;
import com.helpet.service.vet.dto.request.UpdateVetTariffRequest;
import com.helpet.service.vet.service.error.NotFoundLocalizedError;
import com.helpet.service.vet.storage.model.Vet;
import com.helpet.service.vet.storage.model.VetTariff;
import com.helpet.service.vet.storage.repository.VetTariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VetTariffService {
    private final VetService vetService;

    private final VetTariffRepository vetTariffRepository;

    @Autowired
    public VetTariffService(VetService vetService, VetTariffRepository vetTariffRepository) {
        this.vetService = vetService;
        this.vetTariffRepository = vetTariffRepository;
    }

    public List<VetTariff> getVetTariffs(UUID vetId) throws NotFoundLocalizedException {
        if (!vetService.vetExists(vetId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_EXIST);
        }

        return vetTariffRepository.findAllByVetIdOrderByName(vetId);
    }

    public VetTariff getVetTariff(UUID vetId, UUID tariffId) throws NotFoundLocalizedException {
        if (!vetService.vetExists(vetId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_EXIST);
        }

        return vetTariffRepository.findVetTariffByVetIdAndId(vetId, tariffId)
                                  .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_HAVE_THIS_TARIFF));
    }

    public VetTariff createVetTariff(UUID vetId, CreateVetTariffRequest vetTariffInfo) throws NotFoundLocalizedException {
        Vet vet = vetService.getVet(vetId);

        VetTariff newVetTariff = VetTariff.builder()
                                          .name(vetTariffInfo.getName())
                                          .description(vetTariffInfo.getDescription())
                                          .minPrice(Math.min(vetTariffInfo.getMinPrice(), vetTariffInfo.getMaxPrice()))
                                          .maxPrice(Math.max(vetTariffInfo.getMinPrice(), vetTariffInfo.getMaxPrice()))
                                          .vet(vet)
                                          .build();

        return vetTariffRepository.save(newVetTariff);
    }

    public VetTariff updateVetTariff(UUID vetId, UUID tariffId, UpdateVetTariffRequest vetTariffInfo) throws NotFoundLocalizedException {
        VetTariff vetTariff = getVetTariff(vetId, tariffId);

        vetTariff.setName(vetTariffInfo.getName());
        vetTariff.setDescription(vetTariffInfo.getDescription());
        vetTariff.setMinPrice(Math.min(vetTariffInfo.getMinPrice(), vetTariffInfo.getMaxPrice()));
        vetTariff.setMaxPrice(Math.max(vetTariffInfo.getMinPrice(), vetTariffInfo.getMaxPrice()));

        return vetTariffRepository.save(vetTariff);
    }

    public void deleteVetTariff(UUID vetId, UUID tariffId) throws NotFoundLocalizedException {
        VetTariff vetTariff = getVetTariff(vetId, tariffId);

        vetTariffRepository.delete(vetTariff);
    }
}
