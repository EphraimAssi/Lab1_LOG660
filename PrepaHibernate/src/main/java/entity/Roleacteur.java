package entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigInteger;

@Entity
public class Roleacteur {
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

    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Id
    @javax.persistence.Column(name = "IDFILM")
   // private BigInteger idfilm;
    @ManyToOne
    @JoinColumn(name = "IDFILM")
    private Film film;


    public Film getIdfilm() {
        return film;
    }

    public void setIdfilm(BigInteger idfilm) {
        this.film = film;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "PERSONNAGE")
    private String personnage;

    public String getPersonnage() {
        return personnage;
    }

    public void setPersonnage(String personnage) {
        this.personnage = personnage;
    }
}
