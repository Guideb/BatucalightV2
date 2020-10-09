package fr.eseo.dis.tristan.batucadacommander.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import fr.eseo.dis.tristan.batucadacommander.database.dao.BColorDao;
import fr.eseo.dis.tristan.batucadacommander.database.dao.BlocDao;
import fr.eseo.dis.tristan.batucadacommander.database.dao.ChoreDao;
import fr.eseo.dis.tristan.batucadacommander.database.dao.EffetDao;
import fr.eseo.dis.tristan.batucadacommander.database.dao.GroupeDao;
import fr.eseo.dis.tristan.batucadacommander.database.dao.GroupeEtEffetDao;
import fr.eseo.dis.tristan.batucadacommander.database.dao.IconeDao;
import fr.eseo.dis.tristan.batucadacommander.database.dao.MachineDao;
import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Bloc;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Chore;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Effet;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Groupe;
import fr.eseo.dis.tristan.batucadacommander.database.entities.GroupeEtEffet;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Icone;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;

/**
 * @author Tristan LE GACQUE
 * Created 12/10/2018
 */
@Database(entities = {
        BColor.class,
        Groupe.class,
        Icone.class,
        Machine.class,
        Effet.class,
        Bloc.class,
        Chore.class,
        GroupeEtEffet.class

}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onCreate (@NonNull SupportSQLiteDatabase db){
                    super.onCreate(db);
                    new PopulateDbAsync(instance).execute();
                }
            };

    /**
     * Get the database
     * @param context Context
     * @return The database
     */
    public static AppDatabase getDatabase(final Context context) {
        synchronized (AppDatabase.class) {
            if(instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "app_database")
                        //.fallbackToDestructiveMigration()
                        .addCallback(sRoomDatabaseCallback)
                        .build();
            }
        }
        return instance;
    }

    /**
     * Get the color DAO
     * @return The color DAO
     */
    public abstract BColorDao colorDao();

    /**
     * Get the groupe DAO
     * @return The groupe DAO
     */
    public abstract GroupeDao groupeDao();

    /**
     * Get the icon DAO
     * @return the icon DAO
     */
    public abstract IconeDao iconeDao();

    /**
     * Get the machine DAO
     * @return The machine DAO
     */
    public abstract MachineDao machineDao();

    /**
     * Get the chore DAO
     * @return The chore DAO
     */
    public abstract ChoreDao choreDao();

    /**
     * Get the block DAO
     * @return The block DAO
     */
    public abstract BlocDao blocDao();

    /**
     * Get the effect DAO
     * @return The effect DAO
     */
    public abstract EffetDao effetDao();

    /**
     * Get the GroupeEtEffet Dao
     * @return the GroupeEtEffet Dao
     */
    public abstract GroupeEtEffetDao groupeEtEffetDao();

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase db;

        /**
         * Populate the database
         * @param db database
         */
        PopulateDbAsync(AppDatabase db) {
            this.db = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            this.db.colorDao().deleteAll();
            this.db.machineDao().deleteAll();
            this.db.groupeDao().deleteAll();
            this.db.choreDao().deleteAll();
            this.db.blocDao().deleteAll();
            this.db.effetDao().deleteAll();
            this.db.groupeEtEffetDao().deleteAll();

            insertColors();
            insertGroupes();
            insertMachines();
            insertChores();
            insertBlocs();
            insertEffets();
            insertGroupeEtEffets();
            return null;
        }



        /**
         * Insert default colors
         */
        private void insertColors() {
            BColor cRouge = new BColor(0, 1, 255, 0, 0);
            BColor cVert = new BColor(1, 2, 0, 255, 0);
            BColor cBleu = new BColor(2, 3, 0, 0, 255);

            BColor cCyan = new BColor(3, 4, 0, 255, 255);
            BColor cMagenta = new BColor(4, 5, 255, 0, 255);
            BColor cJaune = new BColor(5, 6, 255, 255, 0);

            BColor cNoir = new BColor(6, 7, 255, 255, 255);
            BColor cBlanc = new BColor(7, 8, 0, 0, 0);
            BColor cOrange = new BColor(8, 9, 255, 102, 0);
            BColor cRien = new BColor(9,10,255,255,255);

            this.db.colorDao().insert(cRouge);
            this.db.colorDao().insert(cVert);
            this.db.colorDao().insert(cBleu);
            this.db.colorDao().insert(cCyan);
            this.db.colorDao().insert(cMagenta);
            this.db.colorDao().insert(cJaune);
            this.db.colorDao().insert(cNoir);
            this.db.colorDao().insert(cBlanc);
            this.db.colorDao().insert(cOrange);
            this.db.colorDao().insert(cRien);
        }

        /**
         * Insert default machines groups
         */
        private void insertGroupes() {
            this.db.groupeDao().insert(new Groupe(1, "All machines", null, 100));
            this.db.groupeDao().insert(new Groupe(2, "Groupe 1", null, 100));
            this.db.groupeDao().insert(new Groupe(3, "Groupe 2", null, 100));

            /*
            this.db.groupeDao().insert(new Groupe(3, "Groupe 3", null, 100));
            this.db.groupeDao().insert(new Groupe(4, "Groupe 4", null, 100));
            this.db.groupeDao().insert(new Groupe(5, "Groupe 5", null, 100));
            this.db.groupeDao().insert(new Groupe(6, "Groupe 6", null, 100));
            this.db.groupeDao().insert(new Groupe(7, "Groupe 7", null, 100));

             */
        }

        /**
         * Insert default machines
         */
        private void insertMachines() {
            this.db.machineDao().insert(new Machine(0, 0, null, 1, true, 100));
            this.db.machineDao().insert(new Machine(0, 1, null, null, true, 100));
            this.db.machineDao().insert(new Machine(0, 2, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 3, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 4, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 5, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 6, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 7, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 8, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 9, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 10, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 11, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 12, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 13, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 14, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 15, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 16, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 17, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 18, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 19, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 20, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 21, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 22, null, null, true, 100));
            this.db.machineDao().insert(new Machine(0, 23, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 24, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 25, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 26, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 27, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 28, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 29, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 30, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 31, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 32, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 33, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 34, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 35, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 36, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 37, null, null, true,100));
            this.db.machineDao().insert(new Machine(0, 38, null, null, true,100));
        }

        /**
         * Insert the default chore
         */
        private void insertChores(){
            this.db.choreDao().insert(new Chore(1,"Africa Toto"));
        }

        /**
         * Insert the default Bloc
         */
        private void insertBlocs(){
            this.db.blocDao().insert(new Bloc(1,"Intro",1,1));
            this.db.blocDao().insert(new Bloc(2,"Couplet 1",2,1));
            this.db.blocDao().insert(new Bloc(3,"Refrain 1",3,1));
            this.db.blocDao().insert(new Bloc(4,"Couplet 2",4,1));
            this.db.blocDao().insert(new Bloc(5,"Refrain 2",5,1));
        }

        /**
         * Insert the default GroupeEtEffet
         */
        private void insertGroupeEtEffets(){
            this.db.groupeEtEffetDao().insert(new GroupeEtEffet(1,1,1));
            this.db.groupeEtEffetDao().insert(new GroupeEtEffet(1,2,2));
        }


        /**
         * insert the default effects
         */
        private void insertEffets() {

            this.db.effetDao().insert((new Effet(1,"Simple",1,9,0,0)));
            this.db.effetDao().insert((new Effet(2,"Simple",2,9,0,0)));

        }
    }
}
