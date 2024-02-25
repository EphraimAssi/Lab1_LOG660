package entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class Paysproductionfilm {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @javax.persistence.Column(name = "NOMPAYS")
    @ManyToOne
    @JoinColumn(name = "NOMPAYS")

    private Paysproduction paysproduction;

    public Paysproduction PAYS() {
        return paysproduction;
    }

    public void PAYS(Paysproduction pays) {
        this.paysproduction = paysproduction;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
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
