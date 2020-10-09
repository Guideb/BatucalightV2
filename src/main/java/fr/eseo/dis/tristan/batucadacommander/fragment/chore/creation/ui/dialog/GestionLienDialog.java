package fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Bloc;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Effet;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Groupe;
import fr.eseo.dis.tristan.batucadacommander.database.entities.GroupeEtEffet;

public class GestionLienDialog implements AdapterView.OnItemSelectedListener {

    private final Dialog dialog;
    private final OnLienAddedListener listener;
    private Activity context;
    private Bloc selectedBloc;



    //Liste des groupes libres et couleurs
    private final ArrayList<String> groupes = new ArrayList<>();
    private final ArrayList<String> couleurs = new ArrayList<>();
    private final ArrayList<BColor> couleurCouleur = new ArrayList<>();
    //Effet
    final Effet effet = new Effet();
    //ID des spinners
    private  int spinnerIDCouleur1;
    private  int spinnerIDCouleur2;
    /**
     * Crée le dialog pour gérer les liens
     * @param listener
     * @param context
     */
    public GestionLienDialog(OnLienAddedListener listener, Activity context, Bloc selectedBloc){
        this.context = context;
        this.selectedBloc = selectedBloc;
        this.dialog = this.createDialog(context);
        this.listener = listener;
    }

    /**
     * Affiche le dialog
     */
    public void show(){this.dialog.show();}

    /**
     * Crée le dialog
     * @param context Le context de l'activité
     * @return Le dialog créé
     */
    public Dialog createDialog(final Activity context){
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_gestion,null);

        TextView tv = view.findViewById(R.id.blocNom);
        tv.setText(selectedBloc.getNomBloc());

        //Remplir les spinners
        final Spinner spinnerGroupe = view.findViewById(R.id.spinner_groupe);

        new PopulateSpinnerTask(this.groupes,this.couleurs,this.couleurCouleur,this.context).execute(selectedBloc.getIdBloc());
        if (groupes.isEmpty()) {
            try {
                // La tache de remplissage de liste étant en parallèle au thread actuel, la liste de groupe n'a pas le temps de chargé
                //On lance donc un thread pour attendre son remplissage
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.getMessage();
            }
        }
        final ArrayAdapter<String> adapterGroupes =  new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item,groupes);
        adapterGroupes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGroupe.setAdapter(adapterGroupes);


        final Spinner spinnerEffet = view.findViewById(R.id.spinner_effet);

        final ArrayAdapter<CharSequence> adapterEffets = ArrayAdapter.createFromResource(context,R.array.effet,android.R.layout.simple_spinner_item);
        adapterEffets.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEffet.setAdapter(adapterEffets);

        final Spinner spinnerCouleur1 = view.findViewById(R.id.spinner_couleur1);

        final ArrayAdapter<String> adapterCouleur =  new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item,couleurs);
        adapterCouleur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCouleur1.setAdapter(adapterCouleur);
        spinnerIDCouleur1 = spinnerCouleur1.getId();

        final Spinner spinnerCouleur2 = view.findViewById(R.id.spinner_couleur2);
        spinnerCouleur2.setAdapter(adapterCouleur);
        spinnerIDCouleur2 = spinnerCouleur2.getId();



        final SeekBar seekBarFreq = view.findViewById(R.id.barFreq);
        final TextView textFreq =  view.findViewById(R.id.textFreq);
        final SeekBar seekBarDuree = view.findViewById(R.id.barDuree);
        final TextView textDuree =  view.findViewById(R.id.textDuree);

        seekBarFreq.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textFreq.setText("" + (float)progress/100f + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarDuree.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textDuree.setText("" + (float)progress/10f + "s");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        spinnerCouleur1.setOnItemSelectedListener(this);
        spinnerCouleur2.setOnItemSelectedListener(this);

        //inflate the layout
        builder.setView(view)
                .setPositiveButton(context.getString(R.string.dialog_gestion_fin), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (spinnerGroupe.getSelectedItem() == null){
                            CharSequence text = "Aucun groupe selectionné !";
                            CharSequence text2 = "Tout les groupes sont surement assignés !";
                            Toast toast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
                            Toast toast2 = Toast.makeText(context,text2,Toast.LENGTH_SHORT);
                            toast.show();
                            toast2.show();
                        }else {
                            String nomEffet = spinnerEffet.getSelectedItem().toString();
                            String nomGroupe = spinnerGroupe.getSelectedItem().toString();
                            String nomCouleur1 = spinnerCouleur1.getSelectedItem().toString();
                            String nomCouleur2 = spinnerCouleur2.getSelectedItem().toString();
                            int freq = seekBarFreq.getProgress();
                            int duree = seekBarDuree.getProgress();

                            addLien(nomGroupe, nomEffet,nomCouleur1,nomCouleur2,freq,duree);
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_add_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        return builder.create();
    }


    /**
     * Ajout du lien dans la BDD
     */
    private void addLien(String nomGroupe, String nomEffet,String nomCouleur1,String nomCouleur2,int freq,int duree){
        Log.d("ADDLien", "Ajout d'un lien pour le bloc: " + selectedBloc.getNomBloc());
        new AddLienTask(this.context,this.listener,selectedBloc.getIdBloc()).execute(nomGroupe,nomEffet,nomCouleur1,nomCouleur2,String.valueOf(freq),String.valueOf(duree));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView text = new TextView((context));
        text.setText(parent.getItemAtPosition(position).toString());
        int color = Color.rgb(couleurCouleur.get(position).getRed(), couleurCouleur.get(position).getGreen(), couleurCouleur.get(position).getBlue());
        text.setBackgroundColor(color);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(text);
        toast.show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public interface OnLienAddedListener{

        /**
         * Appelé quand un lien est ajouté
         */
        void onAddLien();
    }


    ///////////////
    // TASK
    ///////////////


    private static class AddLienTask extends AsyncTask<String,Void,Void>{
        final AppDatabase database;
        final OnLienAddedListener listener;
        final int idBloc;
        /**
         * Tache qui permet d'ajouter un lien ds la BDD
         * @param context
         * @param listener
         */
        AddLienTask(Context context,OnLienAddedListener listener, int idBloc){
            this.database = AppDatabase.getDatabase(context);
            this.listener = listener;
            this.idBloc = idBloc;
        }


        @Override
        protected Void doInBackground(String... noms) {
            //noms[0] correspond au nom du groupe et noms[1] correspond au nom de l'effet
            int idGroupe = database.groupeDao().getByName(noms[0]).getIdGroupe();
            String nomEffet  = noms[1];
            List<BColor> couleurCouleurList = database.colorDao().getAllColors();
            int idCouleur1 = Integer.parseInt((String) noms[2].subSequence(noms[2].length() - 1, noms[2].length()));
            int idCouleur2 = Integer.parseInt((String) noms[3].subSequence(noms[2].length() - 1, noms[2].length()));
            int freq = Integer.parseInt(noms[4]);
            int duree = Integer.parseInt(noms[5]);

            switch ( noms[1]){
                case "Simple":
                    Log.d("ADD", "Ajout d'un effet simple");
                    Effet effet = new Effet(0,nomEffet,idCouleur1,9,0,0);
                    database.effetDao().insert(effet);
                    //On recupère l'id de l'effet qui vient d'être crée (AutoGénéré)
                    GroupeEtEffet groupeEtEffet = new GroupeEtEffet(idBloc,idGroupe,database.effetDao().getAllEffet().get(database.effetDao().getAllEffet().size()-1).getIdEffet());
                    database.groupeEtEffetDao().insert(groupeEtEffet);
                    break;

                case "Double":
                    Log.d("ADD", "Ajout d'un effet double");
                    Effet effet2 = new Effet(0,nomEffet,idCouleur1,idCouleur2,freq,0);
                    database.effetDao().insert(effet2);
                    //On recupère l'id de l'effet qui vient d'être crée (AutoGénéré)
                    GroupeEtEffet groupeEtEffet2 = new GroupeEtEffet(idBloc,idGroupe,database.effetDao().getAllEffet().get(database.effetDao().getAllEffet().size()-1).getIdEffet());
                    database.groupeEtEffetDao().insert(groupeEtEffet2);
                    break;

                case "Stroboscope":
                    Log.d("ADD", "Ajout d'un effet stroboscope");
                    Effet effet3 = new Effet(0,nomEffet,idCouleur1,9,freq,0);
                    database.effetDao().insert(effet3);
                    //On recupère l'id de l'effet qui vient d'être crée (AutoGénéré)
                    GroupeEtEffet groupeEtEffet3 = new GroupeEtEffet(idBloc,idGroupe,database.effetDao().getAllEffet().get(database.effetDao().getAllEffet().size()-1).getIdEffet());
                    database.groupeEtEffetDao().insert(groupeEtEffet3);
                    break;

                case "Arc en ciel":
                    Log.d("ADD", "Ajout d'un effet arc en ciel");
                    Effet effet4 = new Effet(0,nomEffet,9,9,freq,0);
                    database.effetDao().insert(effet4);
                    //On recupère l'id de l'effet qui vient d'être crée (AutoGénéré)
                    GroupeEtEffet groupeEtEffet4 = new GroupeEtEffet(idBloc,idGroupe,database.effetDao().getAllEffet().get(database.effetDao().getAllEffet().size()-1).getIdEffet());
                    database.groupeEtEffetDao().insert(groupeEtEffet4);
                    break;

                case "Fade":
                    Log.d("ADD", "Ajout d'un effet Fade");
                    Effet effet5 = new Effet(0,nomEffet,idCouleur1,9,0,duree);
                    database.effetDao().insert(effet5);
                    //On recupère l'id de l'effet qui vient d'être crée (AutoGénéré)
                    GroupeEtEffet groupeEtEffet5 = new GroupeEtEffet(idBloc,idGroupe,database.effetDao().getAllEffet().get(database.effetDao().getAllEffet().size()-1).getIdEffet());
                    database.groupeEtEffetDao().insert(groupeEtEffet5);
                    break;

                case "Aucun":
                    Log.d("ADD", "Ajout d'un effet Aucun");
                    Effet effet6 = new Effet(0,nomEffet,9,9,0,0);
                    database.effetDao().insert(effet6);
                    //On recupère l'id de l'effet qui vient d'être crée (AutoGénéré)
                    GroupeEtEffet groupeEtEffet6 = new GroupeEtEffet(idBloc,idGroupe,database.effetDao().getAllEffet().get(database.effetDao().getAllEffet().size()-1).getIdEffet());
                    database.groupeEtEffetDao().insert(groupeEtEffet6);
                    break;
                default:
                    Log.d("ADD", "défault ? Une erreur");
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid){
            this.listener.onAddLien();
        }
    }

    public static class PopulateSpinnerTask extends AsyncTask<Integer,String,Void> {
        private ArrayList<String> groupes;
        private ArrayList<String> couleurs;
        private ArrayList<BColor> couleurCouleur;
        private final AppDatabase database;

        /**
         * Tache pour remplir l'adapter du spinner des groupes
         * @param groupes
         * @param context
         */
        public PopulateSpinnerTask(ArrayList<String> groupes,ArrayList<String> couleurs,ArrayList<BColor> couleurCouleur, Context context){
            this.groupes = groupes;
            this.database = AppDatabase.getDatabase(context);
            this.couleurs = couleurs;
            this.couleurCouleur = couleurCouleur;
        }

        @Override
        protected Void doInBackground(Integer... blocId) {
            List<GroupeEtEffet> groupesEtEffet;
            List<Integer> idGroupes = new ArrayList<>();
            List<Groupe> groupesRestant;
            List<Integer> idGroupesrestant = new ArrayList<>();
            boolean estAttribue;

            if (!(blocId.length == 0)){
                groupesEtEffet = database.groupeEtEffetDao().getGroupeEffetByBlocId(blocId[0]);
            } else {
                groupesEtEffet = null;
            }
            //On veut remplir notre spinner avec les groupes qui ne sont pas déjà attribués
            //Remplir la liste idGroupes avec les id des groupes dèjà utilisés
            for (int i =0; i < groupesEtEffet.size(); i ++){
                idGroupes.add(groupesEtEffet.get(i).getRefGroupe());
            }


            //Remplir la liste groupesRestant avec tout les groupes possibles
            // Et remplir la liste idGroupesRestant avec leur ID
            groupesRestant = database.groupeDao().getAllGroupe();

            for (int i =0; i < groupesRestant.size(); i ++){
                idGroupesrestant.add(groupesRestant.get(i).getIdGroupe());
            }

            //Retire les groupes déjà attribué en croisant les listes
            for (int i = 0; i < idGroupes.size(); i ++){
                estAttribue = idGroupesrestant.contains(idGroupes.get(i));
                if (estAttribue)
                    idGroupesrestant.remove(idGroupes.get(i));
            }

            //Remplir la liste des groupes Restants
            if (groupesRestant != null) {
                groupesRestant.clear();
            }

            for (int i = 0; i <idGroupesrestant.size(); i++){
                groupesRestant.add(database.groupeDao().getById(idGroupesrestant.get(i)));
            }

            String[] groupesRestantString = new String[groupesRestant.size()];
            //remplir l'adapter avec les strings
            for (int i =0; i < groupesRestant.size(); i ++){
                groupesRestantString[i] = groupesRestant.get(i).getNom();
                this.groupes.add(groupesRestant.get(i).getNom());
            }

            //Remplir les spinners des couleurs
            List<BColor> couleurCouleurList = database.colorDao().getAllColors();
            for (int i = 0; i < couleurCouleurList.size(); i++){
                couleurs.add( "couleur n° " + couleurCouleurList.get(i).getIdColor());
                couleurCouleur.add((BColor) couleurCouleurList.get(i));
            }
            return null;
        }
    }

}
