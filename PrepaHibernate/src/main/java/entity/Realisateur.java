package entity;

import entity.utilisateur.model.Personne;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Set;

@Entity
public class Realisateur extends Personne {
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

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "REALISATEURFILM",
            joinColumns = { @JoinColumn(name = "IDPERSONNE") },
            inverseJoinColumns = { @JoinColumn(name = "IDFILM") }
    )
    private Set<Film> films;

    public Set<Film> getFilms() {
        return films;
    }
    public void setFilms(Set<Film> films) {
        this.films = films;
    }
}
