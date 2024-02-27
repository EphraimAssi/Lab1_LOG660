package entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class Client {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IDPERSONNE")
    private BigInteger idpersonne;

    @OneToOne
    @JoinColumn(name = "IDPERSONNE")
    private PersonneDossier personne;

    public PersonneDossier getPersonne() {
        return personne;
    }

    public void setPersonne(PersonneDossier personne) {
        this.personne = personne;
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "numero", column = @Column(name = "NUMEROCARTECREDIT")),
            @AttributeOverride(name = "type", column = @Column(name = "TYPECARTECREDIT")),
            @AttributeOverride(name = "cvv", column = @Column(name = "cvv")),
            @AttributeOverride(name = "expmois", column = @Column(name = "expmois")),
            @AttributeOverride(name = "expannee", column = @Column(name = "expannee")),
    })
    private CarteCredit carteCredit;

    public CarteCredit getCarteCredit() {
        return carteCredit;
    }

    public void setCarteCredit(CarteCredit carteCredit) {
        this.carteCredit = carteCredit;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "IDABONNEMENT")
    private Abonnement abonnement;

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }
}
