package entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class Adresse extends Adressebase{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IDADRESSE")
    private BigInteger idadresse;

    public BigInteger getIdadresse() {
        return idadresse;
    }

    public void setIdadresse(BigInteger idadresse) {
        this.idadresse = idadresse;
    }

    @Basic
    @Column(name = "NUMEROCIVIQUE")
    private String numerocivique;

    public String getNumerocivique() {
        return numerocivique;
    }

    public void setNumerocivique(String numerocivique) {
        this.numerocivique = numerocivique;
    }
    @Basic
    @Column(name = "CODEPOSTAL")
    private String codepostal;

    public String getCodepostal() {
        return codepostal;
    }

    public void setCodepostal(String codepostal) {
        this.codepostal = codepostal;
    }
}
