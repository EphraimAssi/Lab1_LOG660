package entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class Roleacteur {
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Id
    @javax.persistence.Column(name = "IDPERSONNE")

    @ManyToOne
    @JoinColumn(name = "IDPERSONNE")
    private Acteur acteur;

    public Acteur getActeur() {
        return this.acteur;
    }

    public void setActeur(Acteur acteur) {
        this.acteur = acteur;
    }

    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Id
    @javax.persistence.Column(name = "IDFILM")
   // private BigInteger idfilm;
    @ManyToOne
    @JoinColumn(name = "IDFILM")
    private Film film;


    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
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
