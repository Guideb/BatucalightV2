package fr.eseo.dis.tristan.batucadacommander.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author Alexandre DONY
 * Created 08/11/2019
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Chore.class, parentColumns = "idChore", childColumns = "refChore")
})
public class Bloc {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int idBloc;
    private String nomBloc;
    private int ordre;
    private int refChore;


    /**
     * bloc constructor
     * @param idBloc
     * @param nomBloc
     * @param ordre
     * @param refChore
     */
    public Bloc(int idBloc, String nomBloc,int ordre, int refChore){
        this.idBloc = idBloc;
        this.nomBloc = nomBloc;
        this.ordre = ordre;
        this.refChore = refChore;
    }

    /**
     *  Get the id
     * @return The id
     */
    public int getIdBloc() {
        return idBloc;
    }

    /**
     *  Set the id
     * @param idBloc The id of the bloc
     */
    public void setIdBloc(int idBloc) {
        this.idBloc = idBloc;
    }

    /**
     * Get the name of the bloc
     * @return The name of the bloc
     */
    public String getNomBloc() {
        return nomBloc;
    }

    /**
     * Set the name
     * @param nomBloc The name
     */
    public void setNomBloc(String nomBloc) {
        this.nomBloc = nomBloc;
    }

    /**
     *  Get the ordre number in the "chorée"
     * @return the ordre
     */
    public int getOrdre() {
        return ordre;
    }

    /**
     * Set the ordre of the bloc
     * @param ordre
     */
    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    /**
     *  Get the id of the "chorée" attach with this bloc
     * @return the ordre
     */
    public int getRefChore() {
        return refChore;
    }

    /**
     * set the ref of the chore
     * @param refChore
     */
    public void setRefChore(int refChore) {
        this.refChore = refChore;
    }

    @Override
    public String toString() {
        return "Bloc{" +
                "idBloc=" + idBloc +
                ", nomBloc='" + nomBloc + '\'' +
                ", ordre=" + ordre +
                ", refChore=" + refChore +
                '}';
    }

}
