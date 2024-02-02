package org.example.hospital.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcedureDto {

    Long procedureID;
    String name;
    int duration;
    private Set<Long> patientPatientIds = new HashSet<>();

}
