package com.antonio.apprendrebackend.service.dto;

import com.antonio.apprendrebackend.service.model.Success;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessDTO {
    private Success.SuccessEnum successEnum;
    private String name;
    private Double score;
}
