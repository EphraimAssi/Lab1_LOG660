package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity(name = "Roleacteur")
public class Role {

    @Embeddable
    public class RoleActeurId implements Serializable {
        private BigInteger idPersonne;
        private BigInteger idFilm;
    }

    @EmbeddedId
    private RoleActeurId id;

    @ManyToOne
    @JoinColumn(name = "IDPERSONNE")
    private Acteur acteur;

    public Acteur getActeur() {
        return this.acteur;
    }

    public void setActeur(Acteur acteur) {
        this.acteur = acteur;
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
