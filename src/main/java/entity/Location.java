package entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;

@Entity
public class Location {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @javax.persistence.Column(name = "IDLOCATION")
    private BigInteger idlocation;

    public BigInteger getIdlocation() {
        return idlocation;
    }

    public void setIdlocation(BigInteger idlocation) {
        this.idlocation = idlocation;
    }

    @Basic
    @Column(name = "STATUT")
    private String statut;

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Basic
    @Column(name = "DATEDEBUT")
    private Date datedebut;

    public Date getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }

    @Basic
    @Column(name = "DATEMAX")
    private Date datemax;

    public Date getDatemax() {
        return datemax;
    }

    public void setDatemax(Date datemax) {
        this.datemax = datemax;
    }

    @Basic
    @Column(name = "CODEEXEMPLAIRE")
    private BigInteger codeexemplaire;

    public BigInteger getCodeexemplaire() {
        return codeexemplaire;
    }

    public void setCodeexemplaire(BigInteger codeexemplaire) {
        this.codeexemplaire = codeexemplaire;
    }

    @Basic
    @Column(name = "IDABONNEMENT")
    private BigInteger idabonnement;

    public BigInteger getIdabonnement() {
        return idabonnement;
    }

    public void setIdabonnement(BigInteger idabonnement) {
        this.idabonnement = idabonnement;
    }
}
