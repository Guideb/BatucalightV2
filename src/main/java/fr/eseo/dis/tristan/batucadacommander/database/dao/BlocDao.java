package fr.eseo.dis.tristan.batucadacommander.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.entities.Bloc;

@Dao
public interface BlocDao {

    /**
     * Get of all the bloc on the DB
     * @return
     */
    @Query("SELECT * FROM Bloc")
    List<Bloc> getAllBloc();

    /**
     * Get the bloc by ID
     * @param idBloc The id of the bloc
     * @return the bloc
     */
    @Query("SELECT * FROM Bloc WHERE idBloc = :idBloc")
    Bloc getById(int idBloc);

    /**
     * Get the list of the bloc link to the chore by the choreId order by the order of the bloc in the chore
     * @param idChore The id of the chore
     * @return The list of bloc
     */
    @Query("SELECT * FROM Bloc WHERE refChore= :idChore ORDER BY ordre")
    List<Bloc> getByChoreId(int idChore);

    /**
     * Insert new Bloc in database
     * @param bloc
     */
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Bloc bloc);

    /**
     * Update the bloc
     * @param bloc
     */
    @Update
    void update(Bloc bloc);

    /**
     * Clear the bloc table
     */
    @Query("DELETE FROM Bloc")
    void deleteAll();

    /**
     * Delete a bloc
     * @param bloc
     */
    @Delete
    void delete(Bloc bloc);

    /**
     * Obtenir le nom du bloc grâce son ID
     * @param idBloc
     * @return
     */
    @Query("SELECT nomBloc FROM Bloc WHERE idBloc = :idBloc")
    String getNameById(int idBloc);

    /**
     * Obtenir le nombre de bloc associé à une chore
     * @param idChore
     * @return le nombre de bloc associé à la chore
     */
    @Query("SELECT count(*) FROM Bloc WHERE refChore= :idChore")
    int getNbBloc(int idChore);

    /**
     * Supprimer tout les blocs liés à une chorée
     * @param idChore L'id de la chorée à supprimer
     */
    @Query("DELETE FROM Bloc WHERE refChore = :idChore")
    void deleteBlocByIdChore(int idChore);

}
