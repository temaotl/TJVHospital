package org.example.hospital.data.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;


import java.util.HashSet;
import java.util.Set;

@Data
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
