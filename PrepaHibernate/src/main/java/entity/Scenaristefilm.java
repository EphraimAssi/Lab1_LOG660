package entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigInteger;

@Entity
public class Scenaristefilm {
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Id
    @javax.persistence.Column(name = "NOMSCENARISTE")
    @ManyToOne
    @JoinColumn(name = "NOMSCENARISTE")
    private Scenariste scenariste;

    public Scenariste getScenariste() {
        return scenariste;
    }

    public void setScenariste(Scenariste scenariste) {
        this.scenariste = scenariste;
    }

    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Id
    @javax.persistence.Column(name = "IDFILM")
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
