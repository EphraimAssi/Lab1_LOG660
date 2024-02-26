package entity.RoleActeur.model;

import entity.Film;
import entity.utilisateur.model.Personne;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class roleActeurId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "IDPERSONNE")
    private Personne personne;

    @ManyToOne
    @JoinColumn(name = "IDFILM")
    private Film film;

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
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
        if (!(o instanceof roleActeurId)) return false;
        roleActeurId that = (roleActeurId) o;
        return personne.equals(that.personne) &&
                film.equals(that.film);
    }

    public int hashCode() {
        return Objects.hash(personne, film);
    }



}
