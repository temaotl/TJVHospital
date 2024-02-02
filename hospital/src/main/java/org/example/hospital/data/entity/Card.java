package org.example.hospital.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        return Objects.equals(cardID, card.cardID);
    }

    @Override
    public int hashCode() {
        return cardID != null ? cardID.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardID=" + cardID +
                ", diagnosis='" + diagnosis + '\'' +
                ", doctorRecommendations='" + doctorRecommendations + '\'' +
                ", patient=" + patient +
                '}';
    }
}
