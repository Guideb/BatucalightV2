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
import fr.eseo.dis.tristan.batucadacommander.database.entities.Bloc;

public class RemoveBlocDialog {

    private final Context context;
    private Dialog dialog;
    private final OnBlocRemovedListener listener;
    private Bloc bloc;

    /**
     *  Crée le dialog de suppression d'un bloc
     * @param listener The listener
     * @param context The context
     * @param bloc Le bloc à supprimer
     */
    public RemoveBlocDialog(OnBlocRemovedListener listener, Context context, Bloc bloc){
        this.context = context;
        this.bloc = bloc;
        this.dialog = this.createDialog(context);
        this.listener = listener;
    }

    /**
     * Affiche le dialog
     */
    public void show(){
        this.dialog.show();
    }

    /**
     * Crée le dialog
     * @param context
     * @return Le dialog créé
     */
    private Dialog createDialog(Context context){
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle((String.format(Locale.FRANCE, "Supprimer '%s' ? ", this.bloc.getNomBloc())));
        builder.setPositiveButton(R.string.dialog_rem_remgroupb, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                remBloc();
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
     * Supprime le bloc de la BDD
     */
    private void remBloc(){
        Log.d("REMBLOC", "retrait du bloc : " + this.bloc);
        new RemBlocTask(this.context,this.listener,this.bloc).execute();
    }

    public interface OnBlocRemovedListener{
        /**
         * Appelé quand un bloc est supprimé
         */
        void onRemoveBloc();
    }

    //////////////////
    // TASK
    //////////////////

    private static class RemBlocTask extends AsyncTask<Void, Void, Void> {
        final AppDatabase appDatabase;
        final OnBlocRemovedListener listener;
        final Bloc bloc;

        /**
         * Tache pour supprimer le bloc de la BDD
         * @param context
         * @param listener
         * @param bloc
         */
        RemBlocTask(Context context, OnBlocRemovedListener listener, Bloc bloc){
            this.appDatabase = AppDatabase.getDatabase(context);
            this.listener = listener;
            this.bloc = bloc;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("DELETE", "bloc: " + bloc.toString());
            appDatabase.groupeEtEffetDao().deleteLienByIdBloc(bloc.getIdBloc());
            appDatabase.blocDao().delete(bloc);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            this.listener.onRemoveBloc();
        }
    }

}
