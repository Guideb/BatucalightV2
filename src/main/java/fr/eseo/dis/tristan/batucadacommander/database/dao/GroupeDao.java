package fr.eseo.dis.tristan.batucadacommander.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.entities.Groupe;
import fr.eseo.dis.tristan.batucadacommander.database.entities.GroupeAndMachines;

/**
 * @author Tristan LE GACQUE
 * Created 12/10/2018
 */
@Dao
public interface GroupeDao {

    /**
     * Get all groupes
     * @return The groupes
     */
    @Query("SELECT * FROM Groupe")
    List<Groupe> getAllGroupe();

    /**
     * Get a groupe by ID
     * @param idGroupe groupe id
     * @return The grouê - null
     */
        @Query("SELECT * FROM Groupe where idGroupe = :idGroupe")
    Groupe getById(int idGroupe);

    /**
     * Insert new groupe in database
     * @param groupe The groupe to insert
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Groupe groupe);

    /**
     * Update an existing group
     * @param groupe The group
     */
    @Update
    void update(Groupe groupe);

    /**
     * Delete a group
     * @param groupe The groupe to delete
     */
    @Delete
    void delete(Groupe groupe);

    /**
     * Clear groupe table
     */
    @Query("DELETE FROM Groupe")
    void deleteAll();

    /**
     * Get all groupe with associated machines for each groupe
     * @return The groupes with machines
     */
    @Query("SELECT * FROM Groupe")
    List<GroupeAndMachines> getAllGroupeWithMachine();

    /**
     * Obtenir le nom du groupe grâce à son ID
     * @param idGroupe L'ID du groupe
     * @return Le nom du groupe
     */
    @Query("SELECT nom FROM Groupe WHERE idGroupe = :idGroupe")
    String getNameById(int idGroupe);

    /**
     * Obtenir le groupe grâce à son nom
     * @param nomGroupe
     * @return
     */
    @Query("SELECT * FROM GROUPE WHERE nom = :nomGroupe")
    Groupe getByName(String nomGroupe);

}
