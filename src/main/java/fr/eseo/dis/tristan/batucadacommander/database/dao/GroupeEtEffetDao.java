package fr.eseo.dis.tristan.batucadacommander.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.entities.GroupeEtEffet;

@Dao
public interface GroupeEtEffetDao {

    /**
     * Get of all the groupeEtEffet on the DB
     * @return The list of all groupeEtEffet
     */
    @Query("SELECT * FROM GroupeEtEffet")
    List<GroupeEtEffet>  getAllGroupeEtEffet();

    /**
     * Get the bloc ID, groupe ID and effet ID (In that order) from the bloc ID
     * @param idBloc The id of the bloc
     * @return The list of int
     */
    @Query("SELECT * FROM GroupeEtEffet WHERE refBloc = :idBloc")
    List<GroupeEtEffet> getGroupeEffetByBlocId(int idBloc);

    /**
     * Insert new GroupeEtEffet in database
     * @param groupeEtEffet
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(GroupeEtEffet groupeEtEffet);

    /**
     * Update the groupeEtEffet
     * @param groupeEtEffet
     */
    @Update
    void update(GroupeEtEffet groupeEtEffet);

    /**
     * Clear the groupeEtEffet table
     */
    @Query("DELETE FROM GroupeEtEffet")
    void deleteAll();

    /**
     * Delete a groupeEtEffet
     * @param groupeEtEffet
     */
    @Delete
    void delete(GroupeEtEffet groupeEtEffet);

    /**
     * Delete a groupeEtEffet from the BlocId
     */
    @Query("DELETE FROM GroupeEtEffet WHERE refBloc =:idBloc")
    void deleteLienByIdBloc(int idBloc);

    /**
     * Delete a groupeEtEffet from the GroupeId
     */
    @Query("DELETE FROM GroupeEtEffet WHERE refGroupe =:idGroupe")
    void deleteLienByIdGroupe(int idGroupe);
}
