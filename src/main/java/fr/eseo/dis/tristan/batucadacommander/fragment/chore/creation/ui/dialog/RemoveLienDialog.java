package fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Locale;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.GroupeEtEffet;

public class RemoveLienDialog {

    private final Context context;
    private Dialog dialog;
    private final OnLienRemovedListener listener;
    private GroupeEtEffet groupeEtEffet;

    /**
     * Crée le dialog de suppression d'un lien entre groupe et effet
     * @param listener
     * @param context
     * @param groupeEtEffet Le lien a supprimer
     */
    public RemoveLienDialog(OnLienRemovedListener listener, Context context, GroupeEtEffet groupeEtEffet){
        this.context = context;
        this.groupeEtEffet = groupeEtEffet;
        this.dialog = this.createDialog(context);
        this.listener = listener;
    }

    /**
     * Afffiche le dialog
     */
    public void show(){
        this.dialog.show();
    }

    /**
     * Crée le dialog
     * @param context
     * @return Le dialog crée
     */
    private Dialog createDialog(Context context){
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle((String.format(Locale.FRANCE, "Supprimer le lien ? ")));
        builder.setPositiveButton(R.string.dialog_rem_groupeEtEffet, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                remGroupeEtEffet();
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
     * Supprime le lien de la BDD
     */
    private void remGroupeEtEffet(){
        Log.d("REMGROUPEETEFFET", "retrait du lien " + this.groupeEtEffet);
        new RemLienTask(this.context,this.listener,this.groupeEtEffet).execute();
    }


    public interface OnLienRemovedListener{
        /**
         * Appelé quand un lien est supprimé
         */
        void onRemoveLien();
    }

    //////////////////
    // TASK
    //////////////////

    private static class RemLienTask extends AsyncTask<Void, Void, Void> {
        final AppDatabase appDatabase;
        final OnLienRemovedListener listener;
        final GroupeEtEffet groupeEtEffet;

        /**
         * Tache pour supprimer le lien de la BDD
         * @param context
         * @param listener
         * @param groupeEtEffet
         */
        RemLienTask(Context context, OnLienRemovedListener listener, GroupeEtEffet groupeEtEffet){
            this.appDatabase = AppDatabase.getDatabase(context);
            this.listener = listener;
            this.groupeEtEffet = groupeEtEffet;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            appDatabase.groupeEtEffetDao().delete(groupeEtEffet);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            this.listener.onRemoveLien();
        }
    }
}
