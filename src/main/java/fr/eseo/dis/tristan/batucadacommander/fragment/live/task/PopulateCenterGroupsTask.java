package fr.eseo.dis.tristan.batucadacommander.fragment.live.task;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.GroupeAndMachines;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.CenterFragment;

/**
 * @author Tristan LE GACQUE
 * Created 06/12/2018
 */
public class PopulateCenterGroupsTask extends AsyncTask<Void, Void, List<GroupeAndMachines>> {
    private final AppDatabase database;
    private final WeakReference<CenterFragment> fragment;

    /**
     * Task to populate the fragment
     * @param fragment The fragment
     */
    public PopulateCenterGroupsTask(CenterFragment fragment) {
        this.database =  AppDatabase.getDatabase(fragment.getContext());
        this.fragment = new WeakReference<>(fragment);
    }

    @Override
    protected List<GroupeAndMachines> doInBackground(Void... nothing) {
        return database.groupeDao().getAllGroupeWithMachine();
    }

    @Override
    protected void onPostExecute(List<GroupeAndMachines> groups) {
        //Populate
        fragment.get().populateWithGroups(groups);
    }
}
