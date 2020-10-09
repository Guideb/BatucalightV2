package fr.eseo.dis.tristan.batucadacommander.fragment.machine.task;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Groupe;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.LeftFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.adapter.RecyclerGroupsAdapter;

/**
 * @author Tristan LE GACQUE
 * Created 06/12/2018
 */
public class PopulateGroupsTask extends AsyncTask<Integer, Void, Integer> {
    private final RecyclerGroupsAdapter adapter;
    private final AppDatabase database;

    /**
     * Task to populate the adapter
     * @param adapter The adapter
     * @param fragment The fragment
     */
    public PopulateGroupsTask(RecyclerGroupsAdapter adapter, LeftFragment fragment) {
        this.adapter = adapter;
        this.database =  AppDatabase.getDatabase(fragment.getContext());
    }

    @Override
    protected Integer doInBackground(Integer... sId) {
        List<Groupe> groups = database.groupeDao().getAllGroupe();
        this.adapter.setGroupes(groups);
        return sId.length == 1 ? sId[0] : null;
    }

    @Override
    protected void onPostExecute(Integer sId) {
        this.adapter.notifyDataSetChanged();
    }
}
