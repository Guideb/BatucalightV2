package fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;

/**
 * @author Tristan LE GACQUE
 * Created 06/12/2018
 */
public class RemoveMachineDialog {

    private final Context context;
    private final Dialog dialog;
    private final OnMachineRemovedListener listener;
    private Machine machine;

    /**
     * Create the dialog to add machine
     * @param context The contect
     * @param listener the listener
     * @param machine The machine to remove
     */
    public RemoveMachineDialog(OnMachineRemovedListener listener, Context context, Machine machine) {
        this.context = context;
        this.dialog = this.createDialog(context);
        this.listener = listener;
        this.machine = machine;
    }


    /**
     * Show the dialog
     */
    public void show(){
        this.dialog.show();
    }

    /**
     * Create dialog
     * @param context The context
     * @return The dialog created
     */
    private Dialog createDialog(Context context) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.dialog_rem_remmachine);
        builder.setPositiveButton(R.string.dialog_rem_remmachineb, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        remMachine();
                    }
                })
                .setNegativeButton(R.string.dialog_add_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Nothing special to do
                    }
                });

        return builder.create();
    }

    /**
     * Add the machine to the database
     */
    private void remMachine() {
        Log.d("REMMACHINE", "Retrait de la machine : " + this.machine);
        new RemMachineTask(this.context, this.listener, this.machine).execute();
    }

    public interface OnMachineRemovedListener {
        /**
         * Fired when machine is removed
         */
        void onRemoveMachine();
    }


    ////////////////
    // INTERFACE
    ////////////////

    ////////////////
    // TASKS
    ////////////////
    private static class RemMachineTask extends AsyncTask<Void, Void, Void> {
        final AppDatabase database;
        final OnMachineRemovedListener listener;
        final Machine machine;

        /**
         * Task to add a machine to the database
         * @param context The context
         * @param listener The listener
         * @param machine THe machine to remove
         */
        RemMachineTask(Context context, OnMachineRemovedListener listener, Machine machine) {
            this.database =  AppDatabase.getDatabase(context);
            this.listener = listener;
            this.machine = machine;
        }

        @Override
        protected Void doInBackground(Void... nothing) {
            database.machineDao().delete(machine);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            this.listener.onRemoveMachine();
        }
    }
}
