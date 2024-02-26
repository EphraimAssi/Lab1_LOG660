package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Genre implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "NOMGENRE")
    private String nomgenre;

    public String getNomgenre() {
        return nomgenre;
    }

    public void setNomgenre(String nomgenre) {
        this.nomgenre = nomgenre;
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "GENREFILM",
            joinColumns = { @JoinColumn(name = "NOMGENRE") },
            inverseJoinColumns = { @JoinColumn(name = "IDFILM") }
    )
    private Set<Film> films;

    public Set<Film> getGenres() {
        return films;
    }

    public void setGenres(Set<Film> films) {
        this.films = films;
    }




}
