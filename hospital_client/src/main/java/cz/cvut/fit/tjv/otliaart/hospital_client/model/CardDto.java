package cz.cvut.fit.tjv.otliaart.hospital_client.model;

public class CardDto {
    private Long cardID;
    private String diagnosis;
    private PatientDto patient;
    private String doctorRecommendations;

    public Long getCardID() {
        return cardID;
    }

    public void setCardID(Long cardID) {
        this.cardID = cardID;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public PatientDto getPatient() {
        return patient;
    }

    public void setPatient(PatientDto patient) {
        this.patient = patient;
    }

    public String getDoctorRecommendations() {
        return doctorRecommendations;
    }

    public void setDoctorRecommendations(String doctorRecommendations) {
        this.doctorRecommendations = doctorRecommendations;
    }
}
