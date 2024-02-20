package entity;

import javax.persistence.Entity;
import java.math.BigInteger;

@Entity
public class Client {
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Id
    @javax.persistence.Column(name = "IDPERSONNE")
    private BigInteger idpersonne;

    public BigInteger getIdpersonne() {
        return idpersonne;
    }

    public void setIdpersonne(BigInteger idpersonne) {
        this.idpersonne = idpersonne;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "NUMEROCARTECREDIT")
    private String numerocartecredit;

    public String getNumerocartecredit() {
        return numerocartecredit;
    }

    public void setNumerocartecredit(String numerocartecredit) {
        this.numerocartecredit = numerocartecredit;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "TYPECARTECREDIT")
    private String typecartecredit;

    public String getTypecartecredit() {
        return typecartecredit;
    }

    public void setTypecartecredit(String typecartecredit) {
        this.typecartecredit = typecartecredit;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "CVV")
    private String cvv;

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "EXPMOIS")
    private BigInteger expmois;

    public BigInteger getExpmois() {
        return expmois;
    }

    public void setExpmois(BigInteger expmois) {
        this.expmois = expmois;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "EXPANNEE")
    private BigInteger expannee;

    public BigInteger getExpannee() {
        return expannee;
    }

    public void setExpannee(BigInteger expannee) {
        this.expannee = expannee;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "IDABONNEMENT")
    private BigInteger idabonnement;

    public BigInteger getIdabonnement() {
        return idabonnement;
    }

    public void setIdabonnement(BigInteger idabonnement) {
        this.idabonnement = idabonnement;
    }
}
