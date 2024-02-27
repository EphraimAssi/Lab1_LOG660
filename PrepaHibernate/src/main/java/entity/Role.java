package entity;

import javax.persistence.*;

@Entity(name = "Roleacteur")
public class Role {
    @EmbeddedId
    private RoleActeurId role = new RoleActeurId();

    public RoleActeurId getRole() {
        return role;
    }

    @Basic
    @Column(name = "PERSONNAGE")
    private String personnage;

    public String getPersonnage() {
        return personnage;
    }

    public void setPersonnage(String personnage) {
        this.personnage = personnage;
    }
}
