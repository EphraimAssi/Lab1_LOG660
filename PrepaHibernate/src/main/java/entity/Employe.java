package entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class Employe {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IDPERSONNE")
    private BigInteger idpersonne;

    @OneToOne
    @JoinColumn(name = "IDPERSONNE")
    private PersonneDossier personne;

    public PersonneDossier getPersonne() {
        return personne;
    }

    public void setPersonne(PersonneDossier personne) {
        this.personne = personne;
    }

    @Basic
    @Column(name = "MATRICULE")
    private int matricule;

    public int getMatricule() {
        return matricule;
    }

    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }
}
