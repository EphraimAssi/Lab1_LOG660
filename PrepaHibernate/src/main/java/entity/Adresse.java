package entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "Adresse")
@PrimaryKeyJoinColumn(name = "idadresse")
public class Adresse extends Adressebase{
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
