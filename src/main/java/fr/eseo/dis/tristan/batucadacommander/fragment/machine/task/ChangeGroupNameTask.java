package fr.eseo.dis.tristan.batucadacommander.fragment.machine.task;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Groupe;

/**
 * @author Tristan LE GACQUE
 * Created 06/12/2018
 */
public class ChangeGroupNameTask extends AsyncTask<Groupe, Void, Void> {
    private final AppDatabase database;

    /**
     * Task to change group name
     * @param fragment The fragment
     */
    public ChangeGroupNameTask(Fragment fragment) {
        this.database =  AppDatabase.getDatabase(fragment.getContext());
    }

    @Override
    protected Void doInBackground(Groupe... groups) {
        if(groups.length == 1) {
            Groupe group = groups[0];

            database.groupeDao().update(group);
        }

        return null;
    }
}
