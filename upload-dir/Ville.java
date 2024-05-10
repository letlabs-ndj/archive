package org.travel.packmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "villes")
public class Ville{
    @SequenceGenerator(name="Ville_Gen", sequenceName="Ville_Seq")
    @Id @GeneratedValue(generator="Ville_Gen")
    private long id;
    private String nom;
    private String pays;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ville ville = (Ville) o;
        return Objects.equals(nom, ville.nom) && Objects.equals(pays, ville.pays);
    }
    @Override
    public int hashCode() {
        return Objects.hash(nom, pays);
    }
}
