package fr.eseo.dis.tristan.batucadacommander.database.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author DONY
 * created 08/11/2019
 */
@Entity
public class Chore {
    @PrimaryKey(autoGenerate = true)
    private int idChore;

    @NonNull
    private String nomChore;

    /**
     * Chore constructor
     * @param idChore
     * @param nomChore
     */
    public Chore(int idChore, @NonNull String nomChore) {
        this.idChore = idChore;
        this.nomChore = nomChore;
    }

    /**
     * get the id of the chore
     * @return the id
     */
    public int getIdChore() {
        return idChore;
    }

    /**
     * Set the id of the chore
     * @param idChore
     */
    public void setIdChore(int idChore) {
        this.idChore = idChore;
    }

    /**
     * Get the name of the chore
     * @return The name of the chore
     */
    public String getNomChore() {
        return nomChore;
    }

    /**
     * Set the name of the chore
     * @param nomChore The name
     */
    public void setNomChore(@NonNull String nomChore) {
        this.nomChore = nomChore;
    }

    @Override
    public String toString() {
        return "Chore{" +
                "idChore=" + this.idChore +
                ", nomChore='" + this.nomChore + '\'' +
                '}';
    }
}
