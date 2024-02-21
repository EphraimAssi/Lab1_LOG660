package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Scenariste {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @javax.persistence.Column(name = "NOMSCENARISTE")
    private String nomscenariste;

    public String getNomscenariste() {
        return nomscenariste;
    }

    public void setNomscenariste(String nomscenariste) {
        this.nomscenariste = nomscenariste;
    }
}
