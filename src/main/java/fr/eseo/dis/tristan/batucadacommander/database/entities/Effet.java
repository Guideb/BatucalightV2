package fr.eseo.dis.tristan.batucadacommander.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Locale;
@Entity(foreignKeys = {
        @ForeignKey(entity = BColor.class, parentColumns = "idColor", childColumns = "refCouleurUne"),
        @ForeignKey(entity = BColor.class, parentColumns = "idColor", childColumns = "refCouleurDeux")
})
public class Effet {


    @PrimaryKey(autoGenerate = true)
    private int idEffet;

    private String nomEffet;
    private int refCouleurUne;
    private int refCouleurDeux;
    private int frequence;
    private int duree;

    /**
     * Constructeur pour effet
     * @param nomEffet Le nom de l'effet
     * @param refCouleurUne L'id de la première couleur
     * @param refCouleurDeux L'id de la deuxième couleur
     * @param frequence La fréquence de l'effet (Strob)
     * @param duree La durée de l'effet (Fade)
     *
     */
    public Effet(int idEffet, String nomEffet,int refCouleurUne,int refCouleurDeux, int frequence, int duree){
        this.idEffet = idEffet;
        this.nomEffet = nomEffet;
        this.refCouleurUne = refCouleurUne;
        this.refCouleurDeux = refCouleurDeux;
        this.frequence = frequence;
        this.duree = duree;
    }

    /**
     * Constructeur Vide
     */
    public Effet(){

    }

    /**
     *  Get the Id of the effet
     * @return The id
     */
    public int getIdEffet() {
        return idEffet;
    }

    /**
     * Set the Id of the effet
     * @param idEffet The id
     */
    public void setIdEffet(int idEffet) {
        this.idEffet = idEffet;
    }

    /**
     * Get the name of the effets
     * @return The name
     */
    public String getNomEffet() {
        return nomEffet;
    }

    /**
     * Set the name of the effet
     * @param nomEffet
     */
    public void setNomEffet(String nomEffet) {
        this.nomEffet = nomEffet;
    }


    /**
     * "Get" la fréquence de l'effet
     * @return la fréquence de l'effet
     */
    public int getFrequence() {
        return frequence;
    }

    /**
     * "Set" la fréquence de l'effet
     * @param frequence la fréquence de l'effet
     */
    public void setFrequence(int frequence) {
        this.frequence = frequence;
    }

    /**
     * "Get" la durée de l'effet
     * @return la durée de l'effet
     */
    public int getDuree() {
        return duree;
    }

    /**
     * "Set" la durée de l'effet
     * @param duree la durée de l'effet
     */
    public void setDuree(int duree) {
        this.duree = duree;
    }

    /**
     * "get" l'id de la  première couleur
     * @return l'id de la première couleur
     */
    public int getRefCouleurUne() {
        return refCouleurUne;
    }

    /**
     * "Set" l'id de la  première couleur
     * @param refCouleurUne l'id de la première couleur
     */
    public void setRefCouleurUne(int refCouleurUne) {
        this.refCouleurUne = refCouleurUne;
    }

    /**
     * "get" l'id de la  deuxième couleur
     * @return l'id de la deuxième couleur
     */
    public int getRefCouleurDeux() {
        return refCouleurDeux;
    }

    /**
     * "Set" l'id de la  deuxième couleur
     * @param refCouleurDeux l'id de la deuxième couleur
     */
    public void setRefCouleurDeux(int refCouleurDeux) {
        this.refCouleurDeux = refCouleurDeux;
    }

    @Override
    public String toString() {
        return "Effet{" +
                "idEffet=" + idEffet +
                ", nomEffet='" + nomEffet + '\'' +
                ", refCouleurUne=" + refCouleurUne +
                ", refCouleurDeux=" + refCouleurDeux +
                ", frequence=" + frequence +
                ", duree=" + duree +
                '}';
    }
}
