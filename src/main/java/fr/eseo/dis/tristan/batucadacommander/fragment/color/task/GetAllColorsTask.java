package fr.eseo.dis.tristan.batucadacommander.fragment.color.task;

import android.os.AsyncTask;

import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.activity.BatucadaActivity;
import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;

/**
 * @author Tristan LE GACQUE
 * Created 06/12/2018
 */
public class GetAllColorsTask extends AsyncTask<Void, Void, List<BColor>> {

    private final AppDatabase database;

    /**
     * Task to get all the registered machines
     * @param context The context
     */
    public GetAllColorsTask(BatucadaActivity context) {
        this.database =  AppDatabase.getDatabase(context);
    }

    @Override
    protected List<BColor> doInBackground(Void... voids) {
        return database.colorDao().getAllColors();
    }


}
