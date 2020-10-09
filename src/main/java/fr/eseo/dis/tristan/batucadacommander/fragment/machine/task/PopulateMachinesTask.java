package fr.eseo.dis.tristan.batucadacommander.fragment.machine.task;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.adapter.RecyclerMachinesAdapter;

/**
 * @author Tristan LE GACQUE
 * Created 06/12/2018
 */
public class PopulateMachinesTask extends AsyncTask<Integer, Void, Void> {
    private final RecyclerMachinesAdapter adapter;
    private final AppDatabase database;

    /**
     * Task to populate the adapter
     * @param adapter The adapter
     * @param context The context
     */
    public PopulateMachinesTask(RecyclerMachinesAdapter adapter, Context context) {
        this.adapter = adapter;
        this.database =  AppDatabase.getDatabase(context);
    }

    @Override
    protected Void doInBackground(Integer... groupId) {
        List<Machine> machines;

        if(groupId.length == 0) {
            machines = database.machineDao().getAllMachineAvailable();
        } else {
            machines = database.machineDao().getAllMachineInGroup(groupId[0]);
        }

        this.adapter.setMachines(machines);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        this.adapter.notifyDataSetChanged();
    }
}
