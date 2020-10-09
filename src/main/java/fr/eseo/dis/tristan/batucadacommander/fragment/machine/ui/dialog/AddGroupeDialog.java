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
import fr.eseo.dis.tristan.batucadacommander.database.entities.Groupe;

/**
 * @author Tristan LE GACQUE
 * Created 06/12/2018
 */
public class AddGroupeDialog {

    private final Dialog dialog;
    private final OnGroupeAddedListener listener;
    private Activity context;

    /**
     * Create the dialog to add machine
     * @param context The contect
     * @param listener the listener
     */
    public AddGroupeDialog(OnGroupeAddedListener listener, Activity context) {
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
        final View view = inflater.inflate(R.layout.dialog_add_groupe, null);

        //Inflate the layout
        builder.setView(view)
                .setPositiveButton(R.string.dialog_add_addgroupe, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText et = view.findViewById(R.id.groupe_nom);
                        String nom = et.getText().toString();

                        addGroupe(nom);
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
     * Add the groupe to the database
     * @param nom The mgroupe name
     */
    private void addGroupe(String nom) {
        Log.d("ADDGROUPE", "Ajout du groupe avec le nom : " + nom);
        new AddGroupeTask(this.context, this.listener).execute(nom);
    }

    public interface OnGroupeAddedListener {
        /**
         * Fired when groupe is added
         */
        void onAddGroupe();
    }


    ////////////////
    // INTERFACE
    ////////////////

    ////////////////
    // TASKS
    ////////////////
    private static class AddGroupeTask extends AsyncTask<String, Void, Void> {
        final AppDatabase database;
        final OnGroupeAddedListener listener;

        /**
         * Task to add a machine to the database
         * @param context The context
         * @param listener The listener
         */
        AddGroupeTask(Context context, OnGroupeAddedListener listener) {
            this.database =  AppDatabase.getDatabase(context);
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(String... nom) {

            Groupe groupe = new Groupe(0, nom[0], null, 100);
            database.groupeDao().insert(groupe);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            this.listener.onAddGroupe();
        }
    }
}
