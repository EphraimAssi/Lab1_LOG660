package entity;

import javax.persistence.*;
import java.math.BigInteger;

@Embeddable
public class CarteCredit {
    @Basic
    @Column(name = "NUMEROCARTECREDIT")
    private String numero;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Basic
    @Column(name = "TYPECARTECREDIT")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "CVV")
    private String cvv;

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Basic
    @Column(name = "EXPMOIS")
    private BigInteger expmois;

    public BigInteger getExpmois() {
        return expmois;
    }

    public void setExpmois(BigInteger expmois) {
        this.expmois = expmois;
    }

    @Basic
    @Column(name = "EXPANNEE")
    private BigInteger expannee;

    public BigInteger getExpannee() {
        return expannee;
    }

    public void setExpannee(BigInteger expannee) {
        this.expannee = expannee;
    }
}
