package fr.eseo.dis.tristan.batucadacommander.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;

/**
 * @author Tristan LE GACQUE
 * Created 12/10/2018
 */
@Dao
public interface BColorDao {

    /**
     * Get all the colors available
     * @return The colors
     */
    @Query("SELECT * FROM BColor ORDER BY position, idColor")
    List<BColor> getAllColors();

    /**
     * Get a color by ID
     * @param idColor The id of the color
     * @return The color | null
     */
    @Query("SELECT * FROM BColor where idColor = :idColor")
    BColor getById(int idColor);

    /**
     * Insert a new color
     * @param couleur The color to insert
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(BColor couleur);

    /**
     * Update a color
     * @param couleur The color to update
     */
    @Update
    void update(BColor couleur);

    /**
     * Delete a color
     * @param couleur The color to delete
     */
    @Delete
    void delete(BColor couleur);

    /**
     * Clear the color table
     */
    @Query("DELETE FROM BColor")
    void deleteAll();


}
