package sn.dev.ramadanapps.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "departement")
@NamedQuery(name = "listeDepartement",query = "SELECT m FROM Departement m ORDER BY m.nomD desc ")
public class Departement {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int idD;
    private String nomD;
    private Double superficie;
    private int population;
    @ManyToOne
    @JoinColumn(name = "idRegion",referencedColumnName = "id", nullable = false)
    private Region regionId;


}
