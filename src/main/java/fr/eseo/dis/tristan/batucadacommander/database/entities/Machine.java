package fr.eseo.dis.tristan.batucadacommander.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Locale;

import static android.arch.persistence.room.ForeignKey.SET_NULL;

/**
 * @author Tristan LE GACQUE
 * Created 12/10/2018
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Icone.class, parentColumns = "idIcone", childColumns = "refIcone"),
        @ForeignKey(entity = Groupe.class, parentColumns = "idGroupe", childColumns = "refGroupe", onDelete = SET_NULL)},
        indices = {
        @Index(value ="adresse", unique = true)
})
public class Machine {

    @PrimaryKey(autoGenerate = true)
    private int idMachine;

    private int adresse;

    private Integer refIcone;
    private Integer refGroupe;
    private Integer intensity;

    private boolean state;

    /**
     * Constructor
     * @param idMachine UUID of the machine
     * @param adresse The machine address [0 - 255]
     * @param refIcone The ID of the icon
     * @param refGroupe The ID of the group | null if no group
     * @param state The state of the machine
     * @param intensity The intensity
     */
    public Machine(int idMachine, int adresse, Integer refIcone, Integer refGroupe, boolean state, Integer intensity) {
        this.idMachine = idMachine;
        this.adresse = adresse;
        this.refIcone = refIcone;
        this.refGroupe = refGroupe;
        this.state = state;
        this.intensity = intensity;
    }

    /**
     * Get the id of the machine
     * @return The id
     */
    public int getIdMachine() {
        return idMachine;
    }

    /**
     * Set the machine UUID
     * @param idMachine The id of the machine
     */
    public void setIdMachine(int idMachine) {
        this.idMachine = idMachine;
    }

    /**
     * Get the address of the machine
     * @return The address
     */
    public int getAdresse() {
        return adresse;
    }

    /**
     * Set the adress of the machine [0 - 255]
     * @param adresse int beetween 0 - 255
     */
    public void setAdresse(int adresse) {
        this.adresse = adresse;
    }

    /**
     * Get the ref of the associated icon
     * @return The id of icon
     */
    public Integer getRefIcone() {
        return refIcone;
    }

    /**
     * Set ID of associated icon
     * @param refIcone The id
     */
    public void setRefIcone(Integer refIcone) {
        this.refIcone = refIcone;
    }

    /**
     * Get the groupe ID, or null if not assigned
     * @return groupID | null
     */
    public Integer getRefGroupe() {
        return refGroupe;
    }

    /**
     * Set the groupe ID, or null if no group defined
     * @param refGroupe The groupeID | null
     */
    public void setRefGroupe(Integer refGroupe) {
        this.refGroupe = refGroupe;
    }

    /**
     * Get the state of the machine
     * @return true if connected, false otherwise
     */
    public boolean getState() {
        return state;
    }

    /**
     * Set the state of the machine
     * @param state true if connected, false otherwise
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * Get the intensity of the machine
     * @return intensity
     */
    public Integer getIntensity() {
        return intensity;
    }

    /**
     * Set the intensity of a machine
     * @param intensity The intensity
     */
    public void setIntensity(Integer intensity) {
        this.intensity = intensity;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.FRANCE, "Machine n°%d [#%d] | Groupe %d",
                this.getIdMachine(),
                this.getAdresse(),
                this.getRefGroupe());
    }
}
