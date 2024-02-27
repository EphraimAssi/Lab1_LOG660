package entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Entity
public class Realisateur {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IDPERSONNE")
    private BigInteger idpersonne;

    @OneToOne
    @JoinColumn(name = "IDPERSONNE")
    private PersonneFilm personne;

    public PersonneFilm getPersonne() {
        return personne;
    }

    public void setPersonne(PersonneFilm personne) {
        this.personne = personne;
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "REALISATEURFILM",
            joinColumns = { @JoinColumn(name = "IDPERSONNE") },
            inverseJoinColumns = { @JoinColumn(name = "IDFILM") }
    )
    private List<Film> films;

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
}
