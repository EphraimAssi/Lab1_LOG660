package entity;

import javax.persistence.*;

@Entity
public class Paysproduction {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "NOMPAYS")
    private String nompays;

    public String getNompays() {
        return nompays;
    }

    public void setNompays(String nompays) {
        this.nompays = nompays;
    }
}
