package entity;

import javax.persistence.*;
import java.math.BigInteger;

@Embeddable
public class CarteCredit {

    private String numero;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String cvv;

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    private BigInteger expmois;

    public BigInteger getExpmois() {
        return expmois;
    }

    public void setExpmois(BigInteger expmois) {
        this.expmois = expmois;
    }

    private BigInteger expannee;

    public BigInteger getExpannee() {
        return expannee;
    }

    public void setExpannee(BigInteger expannee) {
        this.expannee = expannee;
    }
}
