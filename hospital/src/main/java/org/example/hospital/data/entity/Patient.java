package org.example.hospital.data.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient")
public class Patient {

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
    private Set<Procedure> procedures = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        return Objects.equals(patientID, patient.patientID);
    }

    @Override
    public int hashCode() {
        return patientID != null ? patientID.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientID=" + patientID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                ", card=" + card +
                ", procedures=" + procedures +
                '}';
    }
}
