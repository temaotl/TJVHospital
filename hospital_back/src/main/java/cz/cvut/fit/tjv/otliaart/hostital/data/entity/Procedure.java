package cz.cvut.fit.tjv.otliaart.hostital.data.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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
    private List<Patient> patients = new ArrayList<>();

    public Procedure() {
    }

    public Procedure(Long procedureID, String name, int duration, List<Patient> patients) {
        this.procedureID = procedureID;
        this.name = name;
        this.duration = duration;
        this.patients = patients;
    }

    public Long getProcedureID() {
        return procedureID;
    }

    public void setProcedureID(Long procedureID) {
        this.procedureID = procedureID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Procedure procedure = (Procedure) o;

        if (duration != procedure.duration) return false;
        if (!procedureID.equals(procedure.procedureID)) return false;
        if (!name.equals(procedure.name)) return false;
        return patients.equals(procedure.patients);
    }

    @Override
    public int hashCode() {
        int result = procedureID.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + duration;
        result = 31 * result + patients.hashCode();
        return result;
    }
}
