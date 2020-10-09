package fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Chore;


public class AddChoreDialog {

    private final Dialog dialog;
    private final OnChoreAddedListener listener;
    private Activity context;

    /**
     * Create the dialog to add machine
     * @param listener
     * @param context
     */
    public AddChoreDialog(OnChoreAddedListener listener, Activity context){
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
     * Create a dialog
     * @param context The context
     * @return The dialog created
     */
    private Dialog createDialog(Activity context) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_chore,null);

        //inflate the layout
        builder.setView(view)
                .setPositiveButton(context.getString(R.string.dialog_add_addchore), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText et = view.findViewById(R.id.chore_nom);
                String nom = et.getText().toString();

                addChore(nom);
            }
        })
                .setNegativeButton(R.string.dialog_add_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Rien à faire
                    }
                });
        return builder.create();
    }

    private void addChore(String nom) {
        Log.d("ADDCHORE", "Ajout de la chorée nommée : " + nom);
        new AddChoreTask(this.context, this.listener).execute(nom);
    }


    public interface OnChoreAddedListener{

        /**
         * Fired when chore is removed
         */
        void onAddChore();
    }

    ////////////////
    // TASK
    ////////////////

    private static class AddChoreTask extends AsyncTask<String, Void, Void> {
        final AppDatabase appDatabase;
        final OnChoreAddedListener listener;

        /**
         * Task / Tâche qui permet d'ajouter une chore ds la BDD
         * @param context
         * @param listener
         */
        AddChoreTask(Context context, OnChoreAddedListener listener){
            this.appDatabase = AppDatabase.getDatabase(context);
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(String... nom) {
            Chore chore = new Chore(0,nom[0]);
            appDatabase.choreDao().insert(chore);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            this.listener.onAddChore();
        }
    }



}
