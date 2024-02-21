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

    @Basic
    @Column(name = "CODEFORFAIT")
    private String codeforfait;

    public String getCodeforfait() {
        return codeforfait;
    }

    public void setCodeforfait(String codeforfait) {
        this.codeforfait = codeforfait;
    }



}
