package entity;

import javax.persistence.Entity;
import java.math.BigInteger;

@Entity
public class Adresse {
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Id
    @javax.persistence.Column(name = "IDADRESSE")
    private BigInteger idadresse;

    public BigInteger getIdadresse() {
        return idadresse;
    }

    public void setIdadresse(BigInteger idadresse) {
        this.idadresse = idadresse;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "NUMEROCIVIQUE")
    private String numerocivique;

    public String getNumerocivique() {
        return numerocivique;
    }

    public void setNumerocivique(String numerocivique) {
        this.numerocivique = numerocivique;
    }
    @javax.persistence.Basic
    @javax.persistence.Column(name = "CODEPOSTAL")
    private String codepostal;

    public String getCodepostal() {
        return codepostal;
    }

    public void setCodepostal(String codepostal) {
        this.codepostal = codepostal;
    }
}
