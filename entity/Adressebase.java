package entity;

import javax.persistence.Entity;
import java.math.BigInteger;

@Entity
public class Adressebase {
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
    @javax.persistence.Column(name = "RUE")
    private String rue;

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "VILLE")
    private String ville;

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "PROVINCE")
    private String province;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "PAYS")
    private String pays;

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
}
