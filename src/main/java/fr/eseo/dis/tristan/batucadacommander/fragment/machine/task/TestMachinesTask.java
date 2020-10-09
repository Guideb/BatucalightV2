package fr.eseo.dis.tristan.batucadacommander.fragment.machine.task;

import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.activity.BatucadaActivity;
import fr.eseo.dis.tristan.batucadacommander.communication.CommunicationManager;
import fr.eseo.dis.tristan.batucadacommander.communication.enums.SerialComResult;
import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dialog.ErrorDialog;

/**
 * @author Tristan LE GACQUE
 * Created 06/12/2018
 */
public class TestMachinesTask extends AsyncTask<Void, Void, List<Machine>> {
    private static final String TEST_TAG = "TEST";

    private final WeakReference<BatucadaActivity> contextWR;
    private final AppDatabase database;
    private final CommunicationManager communicationManager;
    private final UpdateMachinesTask task;
    private final boolean uiResponse;

    /**
     * Task to get all the registered machines
     * @param context The context
     * @param task The task to execute at the end
     * @param uiResponse If show visual response of not connected machines
     */
    public TestMachinesTask(BatucadaActivity context, UpdateMachinesTask task, boolean uiResponse) {
        this.contextWR = new WeakReference<>(context);
        this.database =  AppDatabase.getDatabase(context);
        this.communicationManager = context.getCommunicationManager();
        this.task = task;
        this.uiResponse = uiResponse;
    }


    @Override
    protected List<Machine> doInBackground(Void... voids) {
        //Get ahh the machines
        List<Machine> machines = database.machineDao().getAllMachine();

        //Execute test for machines
        int testIndex = 1;
        for(Machine machine : machines) {
            Log.d(TEST_TAG, String.format("Test de la machine %d / %d", testIndex++, machines.size()));

            SerialComResult result = communicationManager.testMachine(machine);
            boolean connected = !SerialComResult.ERROR_MESSAGE_TIMEOUT.equals(result);

            //Set the state
            machine.setState(connected);
        }

        return machines;
    }

    @Override
    protected void onPostExecute(List<Machine> machines) {
        //Store machine not connected
        List<Machine> notConnectedMachines = new ArrayList<>();

        for(Machine machine : machines) {
            if(!machine.getState()) {
                notConnectedMachines.add(machine);
            }
        }

        //Update the machines
        task.execute(machines);

        //Set ui response
        if(this.uiResponse) {
            BatucadaActivity context = contextWR.get();

            List<String> messages = new ArrayList<>();

            if(notConnectedMachines.isEmpty()) {
                messages.add(context.getString(R.string.com_error_all_success));
            } else {
                for (Machine machine : notConnectedMachines) {
                    messages.add(context.getString(R.string.com_error_timeout_msg, machine.getAdresse()));
                }
            }

            new ErrorDialog(context, context.getString(R.string.com_error_test_title), messages).show();
        }

    }

}
