package entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class Forfait {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CODEFORFAIT")
    private String codeforfait;

    public String getCodeforfait() {
        return codeforfait;
    }

    public void setCodeforfait(String codeforfait) {
        this.codeforfait = codeforfait;
    }

    @Basic
    @Column(name = "NOM")
    private String nom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "PRIX")
    private short prix;

    public short getPrix() {
        return prix;
    }

    public void setPrix(short prix) {
        this.prix = prix;
    }

    @Basic
    @Column(name = "MAXLOCATION")
    private BigInteger maxlocation;

    public BigInteger getMaxlocation() {
        return maxlocation;
    }

    public void setMaxlocation(BigInteger maxlocation) {
        this.maxlocation = maxlocation;
    }

    @Basic
    @Column(name = "MAXDUREE")
    private Double maxduree;

    public Double getMaxduree() {
        return maxduree;
    }

    public void setMaxduree(Double maxduree) {
        this.maxduree = maxduree;
    }
}
