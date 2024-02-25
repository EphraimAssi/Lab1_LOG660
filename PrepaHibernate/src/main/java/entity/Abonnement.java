package entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class Abonnement {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IDABONNEMENT")
    private BigInteger idabonnement;

    public BigInteger getIdabonnement() {
        return idabonnement;
    }

    public void setIdabonnement(BigInteger idabonnement) {
        this.idabonnement = idabonnement;
    }

    @ManyToOne
    @JoinColumn(name = "CODEFORFAIT")
    private Forfait forfait;

    public Forfait getForfait() {
        return forfait;
    }

    public void setForfait(Forfait forfait) {
        this.forfait = forfait;
    }



}
