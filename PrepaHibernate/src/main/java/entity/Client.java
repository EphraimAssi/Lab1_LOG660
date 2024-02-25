package entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class Client {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IDPERSONNE")
    private BigInteger idpersonne;

    public BigInteger getIdpersonne() {
        return idpersonne;
    }

    public void setIdpersonne(BigInteger idpersonne) {
        this.idpersonne = idpersonne;
    }

    @Basic
    @Column(name = "NUMEROCARTECREDIT")
    private String numerocartecredit;

    public String getNumerocartecredit() {
        return numerocartecredit;
    }

    public void setNumerocartecredit(String numerocartecredit) {
        this.numerocartecredit = numerocartecredit;
    }

    @Basic
    @Column(name = "TYPECARTECREDIT")
    private String typecartecredit;

    public String getTypecartecredit() {
        return typecartecredit;
    }

    public void setTypecartecredit(String typecartecredit) {
        this.typecartecredit = typecartecredit;
    }

    @Basic
    @Column(name = "CVV")
    private String cvv;

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Basic
    @Column(name = "EXPMOIS")
    private BigInteger expmois;

    public BigInteger getExpmois() {
        return expmois;
    }

    public void setExpmois(BigInteger expmois) {
        this.expmois = expmois;
    }

    @Basic
    @Column(name = "EXPANNEE")
    private BigInteger expannee;

    public BigInteger getExpannee() {
        return expannee;
    }

    public void setExpannee(BigInteger expannee) {
        this.expannee = expannee;
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
