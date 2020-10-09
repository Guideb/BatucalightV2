
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
import android.widget.TextView;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Bloc;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Chore;

public class AddBlocDialog {

    private final Dialog dialog;
    private final OnBlocAddedListener listener;
    private Activity context;
    private Chore selectedChore;

    /**
     * Crée le dialog pour ajouter un bloc
     * @param listener
     * @param context
     * @param selectedChore
     */

    public AddBlocDialog(OnBlocAddedListener listener, Activity context,Chore selectedChore){
        this.context = context;
        this.selectedChore = selectedChore;
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
     * Crée un dialog
     * @param context Le context
     * @return Le dialog créé
     */
    private Dialog createDialog(Activity context){
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_bloc,null);

        TextView tv = view.findViewById(R.id.choreBloc_nom);
        tv.setText(selectedChore.getNomChore());
        //inflate the layout
        builder.setView(view)
                .setPositiveButton(context.getString(R.string.dialog_add_addbloc), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et = view.findViewById(R.id.bloc_nom);
                        String nom = et.getText().toString();


                        addBloc(nom,selectedChore.getIdChore());
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


    private void addBloc(String nom,int refChore){
        Log.d("ADDBloc", "Ajout d 'un bloc nommé : " + nom);
        //Pour effectuer la tache, on a besoin d'avoir que des Strings d'ou le cast de la ref et de l'ordre en String
        new AddBlocTask(this.context,this.listener).execute(nom,String.valueOf(refChore));
    }

    public interface OnBlocAddedListener {

        /**
         * Appelé quand le bloc est ajouté
         */
        void onAddBloc();
    }

    //////////////////
    // TASK
    //////////////////

    private static class AddBlocTask extends AsyncTask<String,Void,Void>{
        final AppDatabase appDatabase;
        final OnBlocAddedListener listener;

        /**
         * Task / Tâche qui permet d'ajouter un bloc ds la BDD
         * @param context
         * @param listener
         */
        AddBlocTask(Context context, OnBlocAddedListener listener){
            this.appDatabase = AppDatabase.getDatabase(context);
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(String... nom){
            int ordre = appDatabase.blocDao().getNbBloc(Integer.valueOf(nom[1]));
            Bloc bloc = new Bloc(0,nom[0],ordre,Integer.valueOf(nom[1]));
            // nom[1] correspond a la référence de la chorée (re caster en int ) (cf addBloc)
            appDatabase.blocDao().insert(bloc);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            this.listener.onAddBloc();
        }
    }

}
