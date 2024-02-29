package entity;

import javax.persistence.*;

@Entity(name = "Roleacteur")
public class Role {
    @EmbeddedId
    private RoleActeurId role = new RoleActeurId();

    public RoleActeurId getRole() {
        return role;
    }

    @ManyToOne
    @JoinColumn(name = "IDPERSONNE", insertable = false, updatable = false)
    private Acteur acteur;

    public Acteur getActeur() {
        return acteur;
    }

    public void setActeur(Acteur acteur) {
        this.acteur = acteur;
    }

    @ManyToOne
    @JoinColumn(name = "IDFILM", insertable = false, updatable = false)
    private Film film;

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    @Basic
    @Column(name = "PERSONNAGE")
    private String personnage;

    public String getPersonnage() {
        return personnage;
    }

    public void setPersonnage(String personnage) {
        this.personnage = personnage;
    }
}
