package entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Acteur")
@PrimaryKeyJoinColumn(name = "idPersonne")
public class Acteur extends PersonneFilm {
    @OneToMany
    @JoinColumn(name = "IDPERSONNE")
    private List<Role> role;
}
