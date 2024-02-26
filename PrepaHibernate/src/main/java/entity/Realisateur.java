package entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Realisateur")
@PrimaryKeyJoinColumn(name = "idPersonne")
public class Realisateur extends PersonneFilm{
    @JoinTable(
            name = "RealisateurFilm",
            joinColumns = @JoinColumn(name = "idPersonne"),
            inverseJoinColumns = @JoinColumn(name = "idFilm")
    )
    private List<Film> films;

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
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
