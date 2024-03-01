package entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

@Embeddable
public class RoleActeurId implements Serializable {
    @Column(name = "IDPERSONNE")
    private BigInteger idpersonne;

    @Column(name = "IDFILM")
    private BigInteger idfilm;

    public RoleActeurId() {

    }

    public RoleActeurId(BigInteger idpersonne, BigInteger idfilm) {
        this.idpersonne = idpersonne;
        this.idfilm = idfilm;
    }

    public BigInteger getIdpersonne() {
        return idpersonne;
    }

    public void setIdpersonne(BigInteger idpersonne) {
        this.idpersonne = idpersonne;
    }

    public BigInteger getIdfilm() {
        return idfilm;
    }

    public void setIdfilm(BigInteger idfilm) {
        this.idfilm = idfilm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleActeurId)) return false;
        RoleActeurId that = (RoleActeurId) o;
        return Objects.equals(getIdpersonne(), that.getIdpersonne()) &&
                Objects.equals(getIdfilm(), that.getIdfilm());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdpersonne(), getIdfilm());
    }
}