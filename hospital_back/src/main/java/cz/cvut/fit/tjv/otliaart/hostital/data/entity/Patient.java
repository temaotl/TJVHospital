package cz.cvut.fit.tjv.otliaart.hostital.data.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patient")
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long patientID;

    private String firstName;

    private String lastName;

    private String documentNumber;


    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL)
    private Card card;

    @ManyToMany(mappedBy = "patients", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Procedure> procedures = new ArrayList<>();

    public Patient() {
    }

    public Patient(Long patientID, String firstName, String lastName, String documentNumber, Card card, List<Procedure> procedures) {
        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.card = card;
        this.procedures = procedures;
    }

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

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
        card.setPatient(this);
    }

    public List<Procedure> getProcedures() {
        return procedures;
    }
    public void setProcedures(List<Procedure> procedures) {
        if (this.procedures == null) {
            this.procedures = procedures;
        } else {
            this.procedures.clear();
            if (procedures != null) {
                this.procedures.addAll(procedures);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        if (!patientID.equals(patient.patientID)) return false;
        if (!firstName.equals(patient.firstName)) return false;
        if (!lastName.equals(patient.lastName)) return false;
        if (!documentNumber.equals(patient.documentNumber)) return false;
        if (!card.equals(patient.card)) return false;
        return procedures.equals(patient.procedures);
    }

    @Override
    public int hashCode() {
        int result = patientID.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + documentNumber.hashCode();
        result = 31 * result + card.hashCode();
        result = 31 * result + procedures.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientID=" + patientID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                ", card=" + card +
                '}';
    }
}
