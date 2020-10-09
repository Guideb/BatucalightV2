package fr.eseo.dis.tristan.batucadacommander.fragment.machine.task;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Groupe;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;

/**
 * @author Tristan LE GACQUE
 * Created 06/12/2018
 */
public class ChangeGroupIntensityTask extends AsyncTask<Groupe, Void, Void> {
    private final AppDatabase database;

    /**
     * Task to change group name
     * @param fragment The fragment
     */
    public ChangeGroupIntensityTask(Fragment fragment) {
        this.database =  AppDatabase.getDatabase(fragment.getContext());
    }

    @Override
    protected Void doInBackground(Groupe... groups) {
        if(groups.length == 1) {
            Groupe group = groups[0];

            if(group != null) {
                database.groupeDao().update(group);

                //Update all associated machines
                List<Machine> machines = database.machineDao().getAllMachineInGroup(group.getIdGroupe());
                for(Machine machine : machines) {
                    machine.setIntensity(group.getIntensity());
                    database.machineDao().update(machine);
                }
            }
        }

        return null;
    }
}
