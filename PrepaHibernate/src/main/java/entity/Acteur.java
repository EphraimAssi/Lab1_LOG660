package entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "Acteur")
@PrimaryKeyJoinColumn(name = "idPersonne")
public class Acteur extends Personne{

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
