package entity;

import javax.persistence.Entity;
import java.math.BigInteger;

@Entity
public class Abonnement {
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Id
    @javax.persistence.Column(name = "IDABONNEMENT")
    private BigInteger idabonnement;

    public BigInteger getIdabonnement() {
        return idabonnement;
    }

    public void setIdabonnement(BigInteger idabonnement) {
        this.idabonnement = idabonnement;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "CODEFORFAIT")
    private String codeforfait;

    public String getCodeforfait() {
        return codeforfait;
    }

    public void setCodeforfait(String codeforfait) {
        this.codeforfait = codeforfait;
    }



}
