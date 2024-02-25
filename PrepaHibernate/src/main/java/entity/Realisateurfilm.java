package entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class Realisateurfilm {
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Id
    @javax.persistence.Column(name = "IDPERSONNE")

    @OneToOne
    @JoinColumn(name = "IDPERSONNE")
    private Realisateur realisateur;

    public Realisateur rgetRealisateur() {
        return realisateur;
    }

    public void setRealisateur(Realisateur realisateur) {
        this.realisateur = realisateur;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @ManyToOne
    @JoinColumn(name = "IDFILM")
    private Film film;

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}
