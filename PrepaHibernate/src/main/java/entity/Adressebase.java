package entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Adressebase {
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
    @Column(name = "RUE")
    private String rue;

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    @Basic
    @Column(name = "VILLE")
    private String ville;

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Basic
    @Column(name = "PROVINCE")
    private String province;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "PAYS")
    private String pays;

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
}
