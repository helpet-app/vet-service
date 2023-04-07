package com.helpet.service.vet.service;

import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.vet.dto.request.CreateVetContactRequest;
import com.helpet.service.vet.dto.request.UpdateVetContactRequest;
import com.helpet.service.vet.service.error.NotFoundLocalizedError;
import com.helpet.service.vet.storage.model.Vet;
import com.helpet.service.vet.storage.model.VetContact;
import com.helpet.service.vet.storage.repository.VetContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VetContactService {
    private final VetService vetService;

    private final VetContactRepository vetContactRepository;

    @Autowired
    public VetContactService(VetService vetService, VetContactRepository vetContactRepository) {
        this.vetService = vetService;
        this.vetContactRepository = vetContactRepository;
    }

    public List<VetContact> getVetContacts(UUID vetId) throws NotFoundLocalizedException {
        if (!vetService.vetExists(vetId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_EXIST);
        }

        return vetContactRepository.findAllByVetIdOrderByName(vetId);
    }

    public VetContact getVetContact(UUID vetId, UUID contactId) throws NotFoundLocalizedException {
        if (!vetService.vetExists(vetId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_EXIST);
        }

        return vetContactRepository.findVetContactByVetIdAndId(vetId, contactId)
                                   .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.VET_DOES_NOT_HAVE_THIS_CONTACT));
    }

    public VetContact createVetContact(UUID vetId, CreateVetContactRequest vetContactInfo) throws NotFoundLocalizedException {
        Vet vet = vetService.getVet(vetId);

        VetContact newVetContact = VetContact.builder()
                                             .name(vetContactInfo.getName())
                                             .value(vetContactInfo.getValue())
                                             .vet(vet)
                                             .build();

        return vetContactRepository.save(newVetContact);
    }

    public VetContact updateVetContact(UUID vetId, UUID contactId, UpdateVetContactRequest vetContactInfo) throws NotFoundLocalizedException {
        VetContact vetContact = getVetContact(vetId, contactId);

        vetContact.setName(vetContactInfo.getName());
        vetContact.setValue(vetContactInfo.getValue());

        return vetContactRepository.save(vetContact);
    }

    public void deleteVetContact(UUID vetId, UUID contactId) throws NotFoundLocalizedException {
        VetContact vetContact = getVetContact(vetId, contactId);

        vetContactRepository.delete(vetContact);
    }
}
