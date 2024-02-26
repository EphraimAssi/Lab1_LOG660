package entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Paysproduction {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "NOMPAYS")
    private String nompays;

    public String getNompays() {
        return nompays;
    }

    public void setNompays(String nompays) {
        this.nompays = nompays;
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "PAYSPRODUCTIONFILM",
            joinColumns = { @JoinColumn(name = "NOMPAYS") },
            inverseJoinColumns = { @JoinColumn(name = "IDFILM") }
    )
    private Set<Film> film;


}
