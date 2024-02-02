package org.example.hospital.data.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

    private Long patientID;
    private String firstName;
    private String lastName;
    private String documentNumber;

    @JsonBackReference
    private CardDto card;

    @JsonBackReference
    private Set<Long> procedureIds = new HashSet<>();
}
