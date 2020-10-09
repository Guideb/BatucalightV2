package fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.task;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Bloc;

public class ChangeBlocNameTask extends AsyncTask<Bloc,Void,Void> {

    private final AppDatabase database;


    public ChangeBlocNameTask(Fragment fragment){
        this.database = AppDatabase.getDatabase(fragment.getContext());
    }

    @Override
    protected Void doInBackground(Bloc... blocs) {
        if(blocs.length == 1){
            Bloc bloc = blocs[0];

            database.blocDao().update(bloc);
        }
        return null;
    }
}
