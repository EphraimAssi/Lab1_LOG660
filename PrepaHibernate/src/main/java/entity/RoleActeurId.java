package entity;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RoleActeurId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "IDPERSONNE")
    private Acteur personne;

    @ManyToOne
    @JoinColumn(name = "IDFILM")
    private Film film;

    public Acteur getPersonne() {
        return personne;
    }

    public void setPersonne(Acteur personne) {
        this.personne = personne;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleActeurId)) return false;
        RoleActeurId that = (RoleActeurId) o;
        return personne.equals(that.personne) &&
                film.equals(that.film);
    }

    public int hashCode() {
        return Objects.hash(personne, film);
    }
}