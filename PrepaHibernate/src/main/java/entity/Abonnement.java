package entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Entity
public class Abonnement {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IDABONNEMENT")
    private BigInteger idabonnement;

    public BigInteger getIdabonnement() {
        return idabonnement;
    }


    @ManyToOne
    @JoinColumn(name = "CODEFORFAIT")
    private Forfait forfait;

    public Forfait getForfait() {
        return forfait;
    }

    public void setForfait(Forfait forfait) {
        this.forfait = forfait;
    }

    @OneToMany
    @JoinColumn(name = "IDLOCATION")
    private List<Location> locations;

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
