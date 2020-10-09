package fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.task;

import android.os.AsyncTask;

import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Bloc;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.CenterFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.ui.adapter.RecyclerBlocAdapter;

/**
 * @author Alexandre DONY
 */
public class PopulateBlocTask extends AsyncTask<Integer, Void , Integer> {
    private final RecyclerBlocAdapter adapter;
    private final AppDatabase database;

    /**
     * Tache pour remplir l'adapter des blocs
     * @param adapter L'adapter
     * @param fragment Le fragment
     */
    public PopulateBlocTask(RecyclerBlocAdapter adapter, CenterFragment fragment){
        this.adapter = adapter;
        this.database = AppDatabase.getDatabase(fragment.getContext());
    }

    @Override
    protected Integer doInBackground(Integer... choreId) {
        List<Bloc> blocs;

        if (!(choreId.length == 0)){
            blocs = database.blocDao().getByChoreId(choreId[0]);
        } else {
            blocs = null;
        }
        this.adapter.setBlocs(blocs);
        return null;
    }

    @Override
    protected void onPostExecute(Integer sId){
        this.adapter.notifyDataSetChanged();
    }
}
