package entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class Exemplairefilm {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CODEEXEMPLAIRE")
    private BigInteger codeexemplaire;

    public BigInteger getCodeexemplaire() {
        return codeexemplaire;
    }

    public void setCodeexemplaire(BigInteger codeexemplaire) {
        this.codeexemplaire = codeexemplaire;
    }

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
