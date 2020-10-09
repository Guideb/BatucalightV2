package fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.task;

import android.os.AsyncTask;

import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Chore;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.LeftFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.ui.adapter.RecyclerChoreAdapter;

/**
 * @author Alexandre DONY
 *
 */
public class PopulateChoreTask  extends AsyncTask<Integer, Void, Integer> {
    private  final RecyclerChoreAdapter adapter;
    private final AppDatabase database;

    /**
     *  Task to populate the adapter
     * @param adapter The adapter
     * @param fragment The fragment
     */
    public PopulateChoreTask(RecyclerChoreAdapter adapter, LeftFragment fragment){
        this.adapter = adapter;
        this.database = AppDatabase.getDatabase(fragment.getContext());
    }

    @Override
    protected Integer doInBackground(Integer... sId){
        List<Chore> chores = database.choreDao().getAllChore();
        this.adapter.setChores(chores);
        return sId.length == 1 ? sId[0] : null;
    }

    @Override
    protected void onPostExecute(Integer sId){
        this.adapter.notifyDataSetChanged();
    }

}
