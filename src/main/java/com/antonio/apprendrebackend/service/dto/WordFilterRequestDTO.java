package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.Number;
import com.antonio.apprendrebackend.service.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordFilterRequestDTO extends WordSenseFilterRequestDTO {
    private List<String> textFiltered;
    private List<PartSpeech> partSpeeches;
    private List<Level> levels;

    @Override
    public boolean hasAnyFilter() {
        if (super.hasAnyFilter()) {
            return true;
        }

        return this.getTextFiltered() != null && this.getTextFiltered().size() > 0
                || this.getPartSpeeches() != null && this.getPartSpeeches().size() > 0
                || this.getLevels() != null && this.getLevels().size() > 0;
    }
}
