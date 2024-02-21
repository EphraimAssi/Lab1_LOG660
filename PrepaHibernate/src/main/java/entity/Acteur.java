package entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class Acteur {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IDPERSONNE")
    private BigInteger idpersonne;

    public BigInteger getIdpersonne() {
        return idpersonne;
    }

    public void setIdpersonne(BigInteger idpersonne) {
        this.idpersonne = idpersonne;
    }
}
