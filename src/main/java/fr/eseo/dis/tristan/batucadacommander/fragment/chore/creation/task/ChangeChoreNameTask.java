package fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.task;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Chore;

public class ChangeChoreNameTask extends AsyncTask<Chore,Void,Void> {

    private final AppDatabase database;

    public ChangeChoreNameTask(Fragment fragment){
        this.database = AppDatabase.getDatabase(fragment.getContext());
    }


    @Override
    protected Void doInBackground(Chore... chores) {
        if(chores.length == 1){
            Chore chore = chores[0];

            database.choreDao().update(chore);
        }
        return null;
    }

}
