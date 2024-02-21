package entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class Genrefilm {
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

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IDFILM")
    private BigInteger idfilm;

    public BigInteger getIdfilm() {
        return idfilm;
    }

    public void setIdfilm(BigInteger idfilm) {
        this.idfilm = idfilm;
    }
}
