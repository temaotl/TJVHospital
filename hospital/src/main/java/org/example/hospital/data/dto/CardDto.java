package org.example.hospital.data.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    private Long cardID;

    @NotBlank(message = "you need to set diagnosis")
    private String diagnosis;

    private String doctorRecommendations;

    @JsonBackReference
    PatientDto patient;
}
