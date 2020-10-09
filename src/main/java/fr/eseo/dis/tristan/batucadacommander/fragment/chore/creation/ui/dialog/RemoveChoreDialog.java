package fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.Locale;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Bloc;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Chore;

public class RemoveChoreDialog {

    private final Context context;
    private final Dialog dialog;
    private final OnChoreRemovedListener listener;
    private Chore chore;

    /**
     *  Create the dialop to remove Chore
     * @param listener The listener
     * @param context The context
     * @param chore The chore to remove
     */
    public RemoveChoreDialog(OnChoreRemovedListener listener, Context context, Chore chore){
        this.context = context;
        this.chore = chore;
        this.dialog = this.createDialog(context);
        this.listener = listener;
    }

    /**
     * show the dialog
     */
    public void show(){
        this.dialog.show();
    }

    /**
     * Create a dialog
     * @param context The context
     * @return The dialog created
     */
    private Dialog createDialog(Context context) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle((String.format(Locale.FRANCE, "Supprimer '%s' ? ", this.chore.getNomChore())));
        builder.setPositiveButton(R.string.dialog_rem_remgroupb, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                remChore();
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

    /**
     * Remove the chore to the database
     */
    private void remChore() {
        Log.d("REMCHORE", "retrait de la choree : " + this.chore);
        new RemChoreTask(this.context,this.listener,this.chore).execute();
    }


    public interface OnChoreRemovedListener{

        /**
         * Fired when chore is removed
         */
        void onRemoveChore();
    }

    ////////////////
    // TASK
    ////////////////

    private static class RemChoreTask extends AsyncTask<Void, Void, Void> {
        final AppDatabase appDatabase;
        final OnChoreRemovedListener listener;
        final Chore chore;

        /**
         * Task to remove a chore to the database
         * @param context
         * @param listener
         * @param chore
         */
        RemChoreTask(Context context, OnChoreRemovedListener listener, Chore chore){
            this.appDatabase = AppDatabase.getDatabase(context);
            this.listener = listener;
            this.chore = chore;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("DELETE", "chore : " + chore.toString());
            //Quand on supprime une chore il faut supprimer tout les blocs liés à la choree
            //Il faut aussi supprimer tout les liens groupetEtEffet lié au bloc que l'on veut suppirmer
            for (int i = 0 ; i < appDatabase.blocDao().getByChoreId(chore.getIdChore()).size(); i++ ){
                appDatabase.groupeEtEffetDao().deleteLienByIdBloc(
                        appDatabase.blocDao().getByChoreId(chore.getIdChore()).get(0).getIdBloc());
            }
            List<Bloc> blocs = appDatabase.blocDao().getByChoreId(chore.getIdChore());
            if (!(blocs.isEmpty())){
                for (int i = 0; i < blocs.size(); i++){
                    appDatabase.blocDao().delete(blocs.get(i));
                }
            }
            appDatabase.choreDao().delete(chore);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            this.listener.onRemoveChore();
        }
    }
}
