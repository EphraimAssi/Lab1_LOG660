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

    @ManyToOne
    @JoinColumn(name = "CODEEXEMPLAIRE")
    private Exemplairefilm exemplaireFilm;

    public Exemplairefilm getExemplairefilm() {
        return exemplaireFilm;
    }

    public void setCodeexemplaire(Exemplairefilm exemplFilm) {
        this.exemplaireFilm = exemplFilm;
    }

    @ManyToOne
    @JoinColumn (name = "IDABONNEMENT")
    private Abonnement abonnement;

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }
}
