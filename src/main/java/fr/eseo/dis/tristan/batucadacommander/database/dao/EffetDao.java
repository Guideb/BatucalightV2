package fr.eseo.dis.tristan.batucadacommander.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.entities.Effet;

@Dao
public interface EffetDao {

    /**
     * Get all effets
     * @return The effet
     */
    @Query("SELECT * FROM Effet")
    List<Effet> getAllEffet();

    /**
     *  Get a effet by Name
     * @param nomEffet
     * @return
     */
    @Query("SELECT * FROM Effet where nomEffet= :nomEffet")
    Effet getByName(String nomEffet);


    /**
     * Obtenir le nom de l'effet grâce à son ID
     * @param idEffet L'ID de l'effet
     * @return Le nom de l'effet
     */
    @Query("SELECT nomEffet FROM Effet WHERE idEffet = :idEffet")
    String getNameById(int idEffet);

    /**
     * Obtenirl'effet grâce à son ID
     * @param idEffet L'ID de l'effet
     * @return Le nom de l'effet
     */
    @Query("SELECT * FROM Effet WHERE idEffet = :idEffet")
    Effet getById(int idEffet);

    /**
     * Insert new effet in database
     * @param effet
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Effet effet);

    /**
     * Clear the effet table
     */
    @Query("DELETE FROM Effet")
    void deleteAll();

    /**
     * Delete a effet
     * @param effet
     */
    @Delete
    void delete(Effet effet);
}
