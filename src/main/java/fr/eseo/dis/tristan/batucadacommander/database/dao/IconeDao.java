package fr.eseo.dis.tristan.batucadacommander.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.entities.Icone;

/**
 * @author Tristan LE GACQUE
 * Created 12/10/2018
 */
@Dao
public interface IconeDao {

    /**
     * Get all icons
     * @return The icons
     */
    @Query("SELECT * FROM Icone")
    List<Icone> getAllIcone();

    /**
     * Get an icon by id
     * @param idIcone The id of the icon
     * @return The icon | null
     */
    @Query("SELECT * FROM Icone where idIcone = :idIcone")
    Icone getById(int idIcone);

    /**
     * Insert an icon in database
     * @param icone The icon to insert
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Icone icone);

    /**
     * Update an icon
     * @param icone The icon to update
     */
    @Update
    void update(Icone icone);

    /**
     * Delete an icon
     * @param icone The icon
     */
    @Delete
    void delete(Icone icone);

    /**
     * Clear icon table
     */
    @Query("DELETE FROM Icone")
    void deleteAll();
}
