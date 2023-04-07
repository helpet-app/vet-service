package com.helpet.service.vet.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum NotFoundLocalizedError implements DefaultEnumLocalizedError {
    ACCOUNT_DOES_NOT_EXIST,
    VET_DOES_NOT_EXIST,
    VET_DOES_NOT_HAVE_THIS_CONTACT,
    VET_DOES_NOT_HAVE_THIS_TARIFF;

    @Override
    public String getErrorKeyPrefix() {
        return "errors.not-found";
    }
}
