package fr.eseo.dis.tristan.batucadacommander.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author Tristan LE GACQUE
 * Created 12/10/2018
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Icone.class, parentColumns = "idIcone", childColumns = "refIcone")
},
        indices = {
        @Index(value = "nom", unique = true)
})
public class Groupe {

    @PrimaryKey(autoGenerate = true)
    private int idGroupe;

    @NonNull

    private String nom;

    private Integer refIcone;
    private Integer intensity;

    /**
     * Groupe constructor
     * @param idGroupe unique-Id of the groupe
     * @param nom The name of the groupê
     * @param refIcone The ref of the icon
     * @param intensity The intensity
     */
    public Groupe(int idGroupe, @NonNull String nom, Integer refIcone, Integer intensity) {
        this.idGroupe = idGroupe;
        this.nom = nom;
        this.refIcone = refIcone;
        this.intensity = intensity;
    }

    /**
     * Get the ID
     * @return the ID
     */
    public int getIdGroupe() {
        return idGroupe;
    }

    /**
     * set the id
     * @param idGroupe The id
     */
    public void setIdGroupe(int idGroupe) {
        this.idGroupe = idGroupe;
    }

    /**
     * Get the name of the groupe
     * @return The name
     */
    @NonNull
    public String getNom() {
        return nom;
    }

    /**
     * Set the name of the groupe
     * @param nom The name
     */
    public void setNom(@NonNull String nom) {
        this.nom = nom;
    }

    /**
     * Get the ref of the icon
     * @return The ref
     */
    public Integer getRefIcone() {
        return refIcone;
    }

    /**
     * Set the ref of the icon
     * @param refIcone the ref
     */
    public void setRefIcone(Integer refIcone) {
        this.refIcone = refIcone;
    }

    /**
     * Get the intensity
     * @return The intensity
     */
    public Integer getIntensity() {
        return intensity;
    }

    /**
     * Set the intensity of the group
     * @param intensity^The intensity
     */
    public void setIntensity(Integer intensity) {
        this.intensity = intensity;
    }

    @NonNull
    @Override
    public String toString() {
        return "Groupe " + this.idGroupe + " : " + this.getNom() + "[" + this.intensity + "%]";
    }
}
