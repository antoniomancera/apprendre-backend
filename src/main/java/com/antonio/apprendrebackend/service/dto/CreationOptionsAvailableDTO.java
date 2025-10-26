package com.antonio.apprendrebackend.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreationOptionsAvailableDTO {
    private boolean isCreationNewDeckAvailable;
    private boolean isRestoreDeckAvailable;

    public CreationOptionsAvailableDTO() {
        this.isCreationNewDeckAvailable = false;
        this.isRestoreDeckAvailable = false;
    }
}
