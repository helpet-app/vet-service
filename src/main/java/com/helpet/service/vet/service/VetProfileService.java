package com.helpet.service.vet.service;

import com.helpet.exception.ConflictLocalizedException;
import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.vet.dto.request.CreateVetProfileRequest;
import com.helpet.service.vet.dto.request.UpdateVetProfileRequest;
import com.helpet.service.vet.service.error.ConflictLocalizedError;
import com.helpet.service.vet.storage.model.Account;
import com.helpet.service.vet.storage.model.Vet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VetProfileService {
    private final AccountService accountService;

    private final VetService vetService;

    @Autowired
    public VetProfileService(AccountService accountService, VetService vetService) {
        this.accountService = accountService;
        this.vetService = vetService;
    }

    public Vet getProfile(UUID vetId) throws NotFoundLocalizedException {
        return vetService.getVet(vetId);
    }

    public Vet createProfile(UUID accountId, CreateVetProfileRequest profileInfo) throws NotFoundLocalizedException, ConflictLocalizedException {
        Account account = accountService.getAccount(accountId);

        if (vetService.vetExists(accountId)) {
            throw new ConflictLocalizedException(ConflictLocalizedError.VET_WITH_SUCH_ID_ALREADY_EXISTS);
        }

        Vet newProfile = Vet.builder()
                            .account(account)
                            .name(profileInfo.getName())
                            .bio(profileInfo.getBio())
                            .build();

        return vetService.saveVet(newProfile);
    }

    public Vet updateProfile(UUID vetId, UpdateVetProfileRequest profileInfo) throws NotFoundLocalizedException {
        Vet profile = getProfile(vetId);

        profile.setName(profileInfo.getName());
        profile.setBio(profileInfo.getBio());

        return vetService.saveVet(profile);
    }

    public void toggleAvailability(UUID vetId) throws NotFoundLocalizedException {
        Vet profile = getProfile(vetId);

        profile.setAvailable(!profile.getAvailable());

        vetService.saveVet(profile);
    }
}
