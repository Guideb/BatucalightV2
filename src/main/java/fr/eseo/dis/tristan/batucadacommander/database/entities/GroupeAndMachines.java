package fr.eseo.dis.tristan.batucadacommander.database.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

/**
 * @author Tristan LE GACQUE
 * Created 12/10/2018
 */
public class GroupeAndMachines {

    @Embedded
    public Groupe groupe;

    @Relation(parentColumn = "idGroupe", entityColumn = "refGroupe")
    public List<Machine> machines;

    /**
     * Get the groupe
     * @return The groupe
     */
    public Groupe getGroupe() {
        return groupe;
    }

    /**
     * Get the machines in the groupe
     * @return The machines
     */
    public List<Machine> getMachines() {
        return machines;
    }
}
