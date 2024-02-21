package entity;

import javax.persistence.Entity;
import java.math.BigInteger;

@Entity
public class Scenaristefilm {
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Id
    @javax.persistence.Column(name = "NOMSCENARISTE")
    private String nomscenariste;

    public String getNomscenariste() {
        return nomscenariste;
    }

    public void setNomscenariste(String nomscenariste) {
        this.nomscenariste = nomscenariste;
    }

    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Id
    @javax.persistence.Column(name = "IDFILM")
    private BigInteger idfilm;

    public BigInteger getIdfilm() {
        return idfilm;
    }

    public void setIdfilm(BigInteger idfilm) {
        this.idfilm = idfilm;
    }
}
