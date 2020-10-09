package fr.eseo.dis.tristan.batucadacommander.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;

/**
 * @author Tristan LE GACQUE
 * Created 12/10/2018
 */
@Dao
public interface MachineDao {

    /**
     * Get all machines
     * @return The machines
     */
    @Query("SELECT * FROM Machine")
    List<Machine> getAllMachine();

    /**
     * Get all machines not associated to a group
     * @return The machines
     */
    @Query("SELECT * FROM Machine WHERE refGroupe IS NULL")
    List<Machine> getAllMachineAvailable();

    /**
     * Get all machine in the desired group
     * @param groupId The group
     * @return The machines in the group
     */
    @Query("SELECT * FROM Machine WHERE refGroupe = :groupId")
    List<Machine> getAllMachineInGroup(int groupId);

    /**
     * Get a machine by its id
     * @param id The machine id
     * @return The machine | null
     */
    @Query("SELECT * FROM Machine where idMachine = :id")
    Machine getById(int id);

    /**
     * Get a machine by its address
     * @param adresse The address of the machone
     * @return The machine | null
     */
    @Query("SELECT * FROM Machine where adresse = :adresse")
    Machine getByAdresse(int adresse);

    /**
     * Insert a machine
     * @param machine The machine
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Machine machine);

    /**
     * Update an existing machine
     * @param machine The machine
     */
    @Update
    void update(Machine machine);

    /**
     * Delete a machine
     * @param machine The machine
     */
    @Delete
    void delete(Machine machine);

    /**
     * Clear machine database
     */
    @Query("DELETE FROM Machine")
    void deleteAll();
}
