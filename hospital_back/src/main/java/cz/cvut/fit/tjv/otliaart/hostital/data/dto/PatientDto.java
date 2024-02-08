package cz.cvut.fit.tjv.otliaart.hostital.data.dto;

import java.util.List;

public class PatientDto {
    private Long patientID;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private CardDto card;
    private List<Long> procedureIds;

    public Long getPatientID() {
        return patientID;
    }

    public void setPatientID(Long patientID) {
        this.patientID = patientID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public CardDto getCard() {
        return card;
    }

    public void setCard(CardDto card) {
        this.card = card;
    }

    public List<Long> getProcedureIds() {
        return procedureIds;
    }

    public void setProcedureIds(List<Long> procedureIds) {
        this.procedureIds = procedureIds;
    }
}
