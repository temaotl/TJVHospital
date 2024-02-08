package cz.cvut.fit.tjv.otliaart.hostital.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "Card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardID;

    @Column
    private String diagnosis;

    @Column
    private String doctorRecommendations;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;





    public Card() {
    }

    public Card(Long cardID, String diagnosis, String doctorRecommendations, Patient patient) {
        this.cardID = cardID;
        this.diagnosis = diagnosis;
        this.doctorRecommendations = doctorRecommendations;
        this.patient = patient;
    }

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


    public String getDoctorRecommendations() {
        return doctorRecommendations;
    }

    public void setDoctorRecommendations(String doctorRecommendations) {
        this.doctorRecommendations = doctorRecommendations;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (!cardID.equals(card.cardID)) return false;
        if (!diagnosis.equals(card.diagnosis)) return false;
        return patient.equals(card.patient);
    }

    @Override
    public int hashCode() {
        int result = cardID.hashCode();
        result = 31 * result + diagnosis.hashCode();
        result = 31 * result + patient.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardID=" + cardID +
                ", diagnosis='" + diagnosis + '\'' +
                ", patient=" + patient +
                '}';
    }
}
