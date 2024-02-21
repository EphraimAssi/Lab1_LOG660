package entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class Employe {
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

    @Basic
    @Column(name = "MATRICULE")
    private int matricule;

    public int getMatricule() {
        return matricule;
    }

    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }
}
