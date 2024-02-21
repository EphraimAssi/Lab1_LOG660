package entity;

import javax.persistence.Entity;
import java.math.BigInteger;

@Entity
public class Forfait {
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Id
    @javax.persistence.Column(name = "CODEFORFAIT")
    private String codeforfait;

    public String getCodeforfait() {
        return codeforfait;
    }

    public void setCodeforfait(String codeforfait) {
        this.codeforfait = codeforfait;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "NOM")
    private String nom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "PRIX")
    private short prix;

    public short getPrix() {
        return prix;
    }

    public void setPrix(short prix) {
        this.prix = prix;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "MAXLOCATION")
    private BigInteger maxlocation;

    public BigInteger getMaxlocation() {
        return maxlocation;
    }

    public void setMaxlocation(BigInteger maxlocation) {
        this.maxlocation = maxlocation;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "MAXDUREE")
    private Object maxduree;

    public Object getMaxduree() {
        return maxduree;
    }

    public void setMaxduree(Object maxduree) {
        this.maxduree = maxduree;
    }
}
