package fr.eseo.dis.tristan.batucadacommander.fragment.machine.task;

import android.content.Context;
import android.os.AsyncTask;

import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.CenterFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.RightFragment;

/**
 * @author Tristan LE GACQUE
 * Created 06/12/2018
 */
public class UpdateMachineTask extends AsyncTask<Machine, Void, Void> {
    private final AppDatabase database;
    private final PopulateMachinesTask populateCenter;
    private final PopulateMachinesTask populateRight;
    private final Integer groupId;

    /**
     * Task to populate the adapter
     * @param context The context
     * @param centerFragment The left fragment
     * @param rightFragment The right fragment
     * @param groupId The group selected
     */
    public UpdateMachineTask(Context context, CenterFragment centerFragment, RightFragment rightFragment, Integer groupId) {
        this.database =  AppDatabase.getDatabase(context);
        this.groupId = groupId;

        this.populateCenter = new PopulateMachinesTask(centerFragment.getAdapter(), context);
        this.populateRight = new PopulateMachinesTask(rightFragment.getAdapter(), context);
    }

    @Override
    protected Void doInBackground(Machine... machines) {

        for(Machine machine : machines) {
            database.machineDao().update(machine);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //Refresh machine list
        if(groupId != null) {
            this.populateCenter.execute(groupId);
        }

        this.populateRight.execute();
    }
}
