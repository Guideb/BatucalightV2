package fr.eseo.dis.tristan.batucadacommander.communication.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.RightFragment;

/**
 * @author Marc Saint-Antonin
 * Asynctask permettant de récupérer les machines associées à un groupe.
 */
public class PopulateMachinesTask extends AsyncTask<Integer, Void, Void>  {

    private final AppDatabase database;
    private ArrayList<Machine> listMachine;

    /**
     * Tache pour avoir les informations sur des Groupes et d'effet
     * @param context Le context
     */
    public PopulateMachinesTask(Context context, ArrayList<Machine> machines){
        this.database = AppDatabase.getDatabase(context);
        this.listMachine = machines;
    }

    @Override
    protected Void doInBackground(Integer... idGroupe) {
        Log.i("PopulateMachinesTask", "id group" + idGroupe[0]);
        List<Machine> machine = database.machineDao().getAllMachineInGroup(idGroupe[0]);
        Log.i("Populate Machines Task", "List des machines : " + machine);

        for(int i = 0; i < machine.size(); i++){
            listMachine.add(machine.get(i));
        }

        return null;
    }
}
