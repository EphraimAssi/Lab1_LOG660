package entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Arrays;
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

    public List<String> getBandesannoncesList() {
        if (bandesannonces != null && !bandesannonces.isEmpty()) {
            return Arrays.asList(bandesannonces.split("###"));
        } else {
            return null;
        }
    }

    public void setBandesannoncesList(List<String> bandesannoncesList) {
        if (bandesannoncesList != null) {
            this.bandesannonces = String.join("###", bandesannoncesList);
        } else {
            this.bandesannonces = null;
        }
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

    @ManyToMany(mappedBy="films")
    private List<Realisateur> realisateurs;

    public List<Realisateur> getRealisateurs() {
        return realisateurs;
    }

    public void setRealisateurs(List<Realisateur> realisateurs) {
        this.realisateurs = realisateurs;
    }

    @OneToMany
    @JoinColumn(name = "IDFILM")
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Paysproductionfilm", joinColumns = @JoinColumn(name = "idfilm"))
    @Column(name = "nomPays")
    private Set<String> paysProduction;

    public Set<String> getPaysProduction() {
        return paysProduction;
    }

    public void setPaysProduction(Set<String> paysProduction) {
        this.paysProduction = paysProduction;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ScenaristeFilm", joinColumns = @JoinColumn(name = "idfilm"))
    @Column(name = "nomScenariste")
    private Set<String> scenaristes;

    public Set<String> getScenaristes() {
        return scenaristes;
    }

    public void setScenaristes(Set<String> scenaristes) {
        this.scenaristes = scenaristes;
    }

    @ManyToMany
    @JoinTable(
            name = "GENREFILM",
            joinColumns = { @JoinColumn(name = "IDFILM") },
            inverseJoinColumns = { @JoinColumn(name = "NOMGENRE") }
    )
    private List<Genre> genres;

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}

