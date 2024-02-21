package entity;

import javax.persistence.*;

@Entity
public class Genre {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "NOMGENRE")
    private String nomgenre;

    public String getNomgenre() {
        return nomgenre;
    }

    public void setNomgenre(String nomgenre) {
        this.nomgenre = nomgenre;
    }
}
