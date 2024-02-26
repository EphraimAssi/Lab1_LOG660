package entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Film {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IDFILM")
    private BigInteger idfilm;

    public BigInteger getIdfilm() {
        return idfilm;
    }

    public void setIdfilm(BigInteger idfilm) {
        this.idfilm = idfilm;
    }

    @Basic
    @Column(name = "TITRE")
    private String titre;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Basic
    @Column(name = "ANNEE")
    private String annee;

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    @Basic
    @Column(name = "LANGUE")
    private String langue;

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    @Basic
    @Column(name = "DUREE")
    private BigInteger duree;

    public BigInteger getDuree() {
        return duree;
    }

    public void setDuree(BigInteger duree) {
        this.duree = duree;
    }

    @Basic
    @Column(name = "RESUME")
    private String resume;

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    @Basic
    @Column(name = "POSTER")
    private String poster;

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Basic
    @Column(name = "BANDESANNONCES")
    private String bandesannonces;

    public String getBandesannonces() {
        return bandesannonces;
    }

    public void setBandesannonces(String bandesannonces) {
        this.bandesannonces = bandesannonces;
    }

    @OneToMany
    @JoinColumn(name = "IDFILM")
    private List<Exemplairefilm> exemplairesFilm;

    public List<Exemplairefilm> getExemplairesFilm() {
        return exemplairesFilm;
    }

    public void setExemplairesFilm(List<Exemplairefilm> exemplairesFilm) {
        this.exemplairesFilm = exemplairesFilm;
    }



}


