package sn.dev.ramadanapps.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Region {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String nomR;

    public int getIdR() {
        return id;
    }

    public void setIdR(int id) {
        this.id = id;
    }

    public String getNomR() {
        return nomR;
    }

    public void setNomR(String nomR) {
        this.nomR = nomR;
    }
}
