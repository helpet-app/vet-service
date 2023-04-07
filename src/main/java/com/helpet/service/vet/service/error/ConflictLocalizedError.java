package com.helpet.service.vet.service.error;

import com.helpet.exception.util.DefaultEnumLocalizedError;

public enum ConflictLocalizedError implements DefaultEnumLocalizedError {
    VET_WITH_SUCH_ID_ALREADY_EXISTS;

    @Override
    public String getErrorKeyPrefix() {
        return "errors.conflict";
    }
}
