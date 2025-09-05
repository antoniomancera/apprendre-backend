package com.antonio.apprendrebackend.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SettingsOptionsDTO {
    List<CourseDTO> courses;
}
