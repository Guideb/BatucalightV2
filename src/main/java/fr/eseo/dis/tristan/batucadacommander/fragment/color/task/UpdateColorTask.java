package fr.eseo.dis.tristan.batucadacommander.fragment.color.task;

import android.content.Context;
import android.os.AsyncTask;

import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;

/**
 * @author Tristan LE GACQUE
 * Created 06/12/2018
 */
public class UpdateColorTask extends AsyncTask<BColor, Void, Void> {

    private final AppDatabase database;

    /**
     * Task to get all the registered machines
     * @param context The context
     */
    public UpdateColorTask(Context context) {
        this.database =  AppDatabase.getDatabase(context);
    }

    @Override
    protected Void doInBackground(BColor... colors) {

        for(BColor color : colors) {
            database.colorDao().update(color);
        }

        return null;
    }

}
