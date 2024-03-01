package entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Entity
public class Acteur {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IDPERSONNE")
    private BigInteger idpersonne;

    @OneToOne
    @JoinColumn(name = "IDPERSONNE")
    private PersonneFilm personne;

    @OneToMany
    @JoinColumn(name = "IDPERSONNE")
    private List<Role> roles;

    public PersonneFilm getPersonne() {
        return personne;
    }

    public void setPersonne(PersonneFilm personne) {
        this.personne = personne;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> role) {
        this.roles = role;
    }
}