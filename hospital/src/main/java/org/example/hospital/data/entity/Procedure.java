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
@Table(name = "procedures")
public class Procedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "procedure_id")
    private Long procedureID;

    private String name;

    private int duration;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "PATIENT_PROCEDURES",
            joinColumns = @JoinColumn(name = "procedure_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    private Set<Patient> patients = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Procedure that)) return false;
        return Objects.equals(procedureID, that.procedureID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(procedureID);
    }

    @Override
    public String toString() {
        return "Procedure{" +
                "procedureID=" + procedureID +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", patients=" + patients +
                '}';
    }
}
