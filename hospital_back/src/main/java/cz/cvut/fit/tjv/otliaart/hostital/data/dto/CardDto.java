package cz.cvut.fit.tjv.otliaart.hostital.data.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Patient;

public class CardDto {
    private Long cardID;
    private String diagnosis;
    private Patient patient;

    private String doctorRecommendations;

    public Long getCardID() {
        return cardID;
    }

    public void setCardID(Long cardID) {
        this.cardID = cardID;
    }

    public String getDoctorRecommendations() {
        return doctorRecommendations;
    }

    public void setDoctorRecommendations(String doctorRecommendations) {
        this.doctorRecommendations = doctorRecommendations;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Patient getPatient() {
        return patient;
    }

    @JsonBackReference
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
