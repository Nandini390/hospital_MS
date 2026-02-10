package org.example.hospitalmanagementsystem_securitylearning.Dto;

import lombok.Data;
import org.example.hospitalmanagementsystem_securitylearning.Entity.Type.BloodGroupType;

import java.time.LocalDate;

@Data
public class PatientResponseDto {
    private Long id;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private BloodGroupType bloodGroup;
}
