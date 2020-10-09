package fr.eseo.dis.tristan.batucadacommander.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author Tristan LE GACQUE
 * Created 12/10/2018
 */
@Entity
public class Icone {

    @PrimaryKey(autoGenerate = true)
    private int idIcone;

    @NonNull
    private String nom;

    private String fichier;

    /**
     * Constructor
     * @param idIcone The id of the icon
     * @param nom The name of the icon
     * @param fichier The file corresponding to the icon
     */
    public Icone(int idIcone, @NonNull String nom, String fichier) {
        this.idIcone = idIcone;
        this.nom = nom;
        this.fichier = fichier;
    }

    /**
     * Get the icon UUID
     * @return The id
     */
    public int getIdIcone() {
        return idIcone;
    }

    /**
     * Set the icon UUID
     * @param idIcone The id
     */
    public void setIdIcone(int idIcone) {
        this.idIcone = idIcone;
    }

    /**
     * Get the name of the icon
     * @return The name
     */
    @NonNull
    public String getNom() {
        return nom;
    }

    /**
     * Set the name of the icon
     * @param nom The name
     */
    public void setNom(@NonNull String nom) {
        this.nom = nom;
    }

    /**
     * Get the file of the icon
     * @return The file name
     */
    public String getFichier() {
        return fichier;
    }

    /**
     * Set the file of the icon
     * @param fichier The file name
     */
    public void setFichier(String fichier) {
        this.fichier = fichier;
    }
}
