package entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;

@Entity
@DiscriminatorValue("PersonneFilm")
public class PersonneFilm extends Personne {
    @Basic
    @Column(name = "PHOTO")
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Basic
    @Column(name = "NOMCOMPLET")
    private String nomcomplet;

    public String getNomcomplet() {
        return nomcomplet;
    }

    public void setNomcomplet(String nomcomplet) {
        this.nomcomplet = nomcomplet;
    }

    @Basic
    @Column(name = "LIEUNAISSANCE")
    private String lieunaissance;

    public String getLieunaissance() {
        return lieunaissance;
    }

    public void setLieunaissance(String lieunaissance) {
        this.lieunaissance = lieunaissance;
    }

    @Basic
    @Column(name = "BIO")
    private String bio;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
