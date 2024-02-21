package entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class Exemplairefilm {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @javax.persistence.Column(name = "CODEEXEMPLAIRE")
    private BigInteger codeexemplaire;

    public BigInteger getCodeexemplaire() {
        return codeexemplaire;
    }

    public void setCodeexemplaire(BigInteger codeexemplaire) {
        this.codeexemplaire = codeexemplaire;
    }

    @Basic
    @Column(name = "IDFILM")
    private BigInteger idfilm;

    public BigInteger getIdfilm() {
        return idfilm;
    }

    public void setIdfilm(BigInteger idfilm) {
        this.idfilm = idfilm;
    }
}
