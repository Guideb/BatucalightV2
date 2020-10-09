package fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.Locale;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Groupe;

/**
 * @author Tristan LE GACQUE
 * Created 06/12/2018
 */
public class RemoveGroupDialog {

    private final Context context;
    private final Dialog dialog;
    private final OnGroupeRemovedListener listener;
    private Groupe groupe;

    /**
     * Create the dialog to add machine
     * @param context The contect
     * @param listener the listener
     * @param groupe The group to remove
     */
    public RemoveGroupDialog(OnGroupeRemovedListener listener, Context context, Groupe groupe) {
        this.context = context;
        this.groupe = groupe;
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
    private Dialog createDialog(Context context) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(String.format(Locale.FRENCH, "Supprimer '%s' ?", this.groupe.getNom()));
        builder.setPositiveButton(R.string.dialog_rem_remgroupb, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        remGroupe();
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
    private void remGroupe() {
        Log.d("REMGROUPE", "Retrait du groupe : " + this.groupe);
        new RemGroupeTask(this.context, this.listener, this.groupe).execute();
    }

    public interface OnGroupeRemovedListener {
        /**
         * Fired when machine is removed
         */
        void onRemoveGroupe();
    }


    ////////////////
    // INTERFACE
    ////////////////

    ////////////////
    // TASKS
    ////////////////
    private static class RemGroupeTask extends AsyncTask<Void, Void, Void> {
        final AppDatabase database;
        final OnGroupeRemovedListener listener;
        final Groupe groupe;

        /**
         * Task to add a machine to the database
         * @param context The context
         * @param listener The listener
         * @param groupe The group to remove
         */
        RemGroupeTask(Context context, OnGroupeRemovedListener listener, Groupe groupe) {
            this.database =  AppDatabase.getDatabase(context);
            this.listener = listener;
            this.groupe = groupe;
        }

        @Override
        protected Void doInBackground(Void... nothing) {
            database.groupeEtEffetDao().deleteLienByIdGroupe(groupe.getIdGroupe());
            database.groupeDao().delete(groupe);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            this.listener.onRemoveGroupe();
        }
    }
}
