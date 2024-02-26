package entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Scenariste {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @javax.persistence.Column(name = "NOMSCENARISTE")
    private String nomscenariste;

    public String getNomscenariste() {
        return nomscenariste;
    }

    public void setNomscenariste(String nomscenariste) {
        this.nomscenariste = nomscenariste;
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "SCENARISTEFILM",
            joinColumns = { @JoinColumn(name = "NOMSCENARISTE") },
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
