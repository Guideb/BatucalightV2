package fr.eseo.dis.tristan.batucadacommander.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.entities.Chore;

@Dao
public interface ChoreDao {

    /**
     * Get of all the chore on the DB
     * @return the chore
     */
    @Query("SELECT * FROM Chore")
    List<Chore> getAllChore();

    /**
     * Get the chore by ID
     * @param idChore The id of the chore
     * @return the chore
     */
    @Query("SELECT * FROM Chore WHERE idChore = :idChore")
    Chore getById(int idChore);

    /**
     * Insert new Chore in database
     * @param chore The chore inserted
     */
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Chore chore);

    /**
     * Update the chore
     * @param chore The chore updated
     */
    @Update
    void update(Chore chore);

    /**
     * Clear the chore table
     */
    @Query("DELETE FROM Chore")
    void deleteAll();

    /**
     * Delete a chore
     * @param chore
     */
    @Delete
    void delete(Chore chore);
}
