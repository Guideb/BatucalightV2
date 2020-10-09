package fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;

/**
 * @author Tristan LE GACQUE
 * Created 06/12/2018
 */
public class AddMachineDialog {

    private final Dialog dialog;
    private final OnMachineAddedListener listener;
    private Activity context;

    /**
     * Create the dialog to add machine
     * @param context The contect
     * @param listener the listener
     */
    public AddMachineDialog(OnMachineAddedListener listener, Activity context) {
        this.context = context;
        this.dialog = this.createDialog(context);
        this.listener = listener;
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
    private Dialog createDialog(Activity context) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_machine, null);

        //Inflate the layout
        builder.setView(view)
                .setPositiveButton(R.string.dialog_add_addmachine, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText et = view.findViewById(R.id.machine_address);
                        String address = et.getText().toString();

                        addMachine(address);
                    }
                })
                .setNegativeButton(R.string.dialog_add_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Nothing special to do
                    }
                }).setCancelable(false);

        return builder.create();
    }

    /**
     * Add the machine to the database
     * @param address The machine address
     */
    private void addMachine(String address) {
        Log.d("ADDMACHINE", "Ajout de la machine avec l'adresse : " + address);
        new AddMachineTask(this.context, this.listener).execute(address);
    }

    public interface OnMachineAddedListener {
        /**
         * Fired when machine is add
         */
        void onAddMachine();
    }


    ////////////////
    // INTERFACE
    ////////////////

    ////////////////
    // TASKS
    ////////////////
    private static class AddMachineTask extends AsyncTask<String, Void, Void> {
        final AppDatabase database;
        final OnMachineAddedListener listener;

        /**
         * Task to add a machine to the database
         * @param context The context
         * @param listener The listener
         */
        AddMachineTask(Context context, OnMachineAddedListener listener) {
            this.database =  AppDatabase.getDatabase(context);
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(String... address) {
            int iAddress;

            try{
                iAddress = Integer.parseInt(address[0]);
                Machine machine = new Machine(0, iAddress, null, null, false, 100);
                database.machineDao().insert(machine);
            }catch (NumberFormatException ignored) {
                //Do nothing if not correct address
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            this.listener.onAddMachine();
        }
    }
}
