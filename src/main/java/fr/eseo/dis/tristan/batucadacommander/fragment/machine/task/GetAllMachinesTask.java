package fr.eseo.dis.tristan.batucadacommander.fragment.machine.task;

import android.os.AsyncTask;

import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.activity.BatucadaActivity;
import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;

/**
 * @author Tristan LE GACQUE
 * Created 06/12/2018
 */
public class GetAllMachinesTask extends AsyncTask<Void, Void, List<Machine>> {

    private final AppDatabase database;

    /**
     * Task to get all the registered machines
     * @param context The context
     */
    public GetAllMachinesTask(BatucadaActivity context) {
        this.database =  AppDatabase.getDatabase(context);
    }

    @Override
    protected List<Machine> doInBackground(Void... voids) {
        return database.machineDao().getAllMachine();
    }


}
