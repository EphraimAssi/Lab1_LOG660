package entity.RoleActeur.model;

import javax.persistence.*;

@Entity
public class Roleacteur {

    @EmbeddedId
    private roleActeurId role = new roleActeurId();

    @Column(name = "PERSONNAGE")
    private String personnage;

    public roleActeurId getRole() {
        return role;
    }

    public String getPersonnage() {
        return personnage;
    }

    public void setPersonnage(String personnage) {
        this.personnage = personnage;
    }


}
