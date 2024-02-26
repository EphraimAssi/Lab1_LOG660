package entity.RoleActeur.model;

import entity.Film;
import entity.utilisateur.model.Personne;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Set;

@Entity
@Table(name = "Acteur")
@PrimaryKeyJoinColumn(name = "idPersonne")
public class Acteur extends Personne {

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
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "ROLEACTEUR",
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
