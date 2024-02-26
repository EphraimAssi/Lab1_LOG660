package entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "Employe")
@PrimaryKeyJoinColumn(name = "idPersonne")
public class Client extends PersonneDossier {

    @Embedded
    private CarteCredit carteCredit;

    public CarteCredit getCarteCredit() {
        return carteCredit;
    }

    public void setCarteCredit(CarteCredit carteCredit) {
        this.carteCredit = carteCredit;
    }

    @ManyToOne
    @JoinColumn(name = "IDABONNEMENT")
    private Abonnement abonnement;

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }
}
