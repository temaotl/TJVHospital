package cz.cvut.fit.tjv.otliaart.hostital.data.dto;

import java.util.List;

public class ProcedureDto {
    private Long procedureID;
    private String name;
    private int duration;
    private List<Long> patientIDs;

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

    public List<Long> getPatientIDs() {
        return patientIDs;
    }

    public void setPatientIDs(List<Long> patientIDs) {
        this.patientIDs = patientIDs;
    }
}
