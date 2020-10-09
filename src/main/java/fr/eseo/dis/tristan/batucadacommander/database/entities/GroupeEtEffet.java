package fr.eseo.dis.tristan.batucadacommander.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Lien entre un groupe et un effet pour le compte d'un bloc
 * Link between a group and et effect for a block
 * @author Alexandre DONY
 * Created 29/11/2019
 */
@Entity(primaryKeys = {"refBloc","refGroupe"},
        foreignKeys = {
        @ForeignKey(entity = Bloc.class, parentColumns = "idBloc", childColumns = "refBloc"),
        @ForeignKey(entity = Groupe.class, parentColumns = "idGroupe", childColumns = "refGroupe"),
        @ForeignKey(entity = Effet.class, parentColumns = "idEffet", childColumns = "refEffet")
})
public class GroupeEtEffet {

    private int refBloc;
    private int refGroupe;
    private int refEffet;

    /**
     * GroupeEtEffet constructor
     * @param refBloc
     * @param refGroupe
     * @param refEffet
     */
    public GroupeEtEffet(int refBloc, int refGroupe, int refEffet) {
        this.refBloc = refBloc;
        this.refGroupe = refGroupe;
        this.refEffet = refEffet;
    }


    /**
     * get The ref of the bloc
     * @return
     */
    public int getRefBloc() {
        return refBloc;
    }
    /**
     * set The ref of the bloc
     * @return
     */
    public void setRefBloc(int refBloc) {
        this.refBloc = refBloc;
    }
    /**
     * get The ref of the Groupe
     * @return
     */
    public int getRefGroupe() {
        return refGroupe;
    }
    /**
     * set The ref of the Groupe
     * @return
     */
    public void setRefGroupe(int refGroupe) {
        this.refGroupe = refGroupe;
    }
    /**
     * get The ref of the Effet
     * @return
     */
    public int getRefEffet() {
        return refEffet;
    }
    /**
     * set The ref of the Effet
     * @return
     */
    public void setRefEffet(int refEffet) {
        this.refEffet = refEffet;
    }

    @Override
    public String toString() {
        return "GroupeEtEffet{" +
                "refBloc=" + refBloc +
                ", refGroupe=" + refGroupe +
                ", refEffet=" + refEffet +
                '}';
    }
}
