package fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Bloc;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Chore;
import fr.eseo.dis.tristan.batucadacommander.database.entities.GroupeEtEffet;
import fr.eseo.dis.tristan.batucadacommander.fragment.MainFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.task.ChangeBlocNameTask;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.task.InformationGroupeEtEffetTask;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.task.PopulateGroupeEtEffetTask;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.ui.adapter.RecyclerGroupeEtEffetAdapter;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.ui.dialog.GestionLienDialog;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.ui.dialog.RemoveLienDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RightFragment.OnRightFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RightFragment extends MainFragment<RightFragment.OnRightFragmentInteractionListener> implements RecyclerGroupeEtEffetAdapter.ItemClickListener,GestionLienDialog.OnLienAddedListener, RemoveLienDialog.OnLienRemovedListener{
    private RecyclerGroupeEtEffetAdapter adapter;
    private Bloc selectedBloc;
    private ViewGroup mainView;
    private EditText tvBlocName;
    private Chore selectedChore;


    public RightFragment() {
        super();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RightFragment.
     */
    public static RightFragment newInstance() {
        return new RightFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.mainView = (ViewGroup) inflater.inflate(R.layout.fragment_chore_creation_right,container,false);

        //Text View
        this.tvBlocName = mainView.findViewById(R.id.nom_blocRight);

        this.selectedBloc = null;

        //Recycler view
        RecyclerView recyclerGroupeEtEffetView = mainView.findViewById(R.id.recycler_groupe_effect);
        this.adapter = new RecyclerGroupeEtEffetAdapter(this.getContext(), this);
        recyclerGroupeEtEffetView.setAdapter(adapter);
        recyclerGroupeEtEffetView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        this.getListener().onFragmentLoaded();

        this.tvBlocName.setText(getString(R.string.mc_bloc_noselect));
        this.tvBlocName.setEnabled(false);
        this.tvBlocName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    changeBlocName(v.getText().toString());
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                    return true;
                }
                return false;
            }
        });
        return mainView;
    }

    private void changeBlocName(String name){
        this.selectedBloc.setNomBloc(name);
        new ChangeBlocNameTask(this).execute(this.selectedBloc);

        this.getListener().onBlocNameChange(selectedChore);
    }

    public void setSelectedBloc(Bloc bloc,Chore chore){
        this.selectedBloc = bloc;
        this.selectedChore = chore;
    }
    /**
     * Get the main view
     * @return The main view
     */
    public ViewGroup getMainView() {
        return mainView;
    }


    //////////////////////
    // BUTTON EVENTS
    //////////////////////

    /**
     * Affiche le dialog de la gestion des liens groupes / effets
     */
    public void openGestionDialog(){
        Log.d("DEBUG", "Ouverture du dialog de gestion des liens groupes/effets");
        if (selectedBloc == null){
            CharSequence text = "Veuillez selectionner un bloc !";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(this.getContext(),text,duration);
            toast.show();
        }else {
            new GestionLienDialog(this,this.getActivity(),selectedBloc).show();
        }
    }
    //////////////////////
    // LISTERNERS
    //////////////////////

    /**
     * Populate GroupeEtEffet list
     */
    public void populateWithGroupeEtEffet(Bloc bloc){
        int blocId = bloc == null ? -1 : bloc.getIdBloc();
        new PopulateGroupeEtEffetTask(this.adapter,this).execute(blocId);

        //Update the bloc name
        this.selectedBloc = bloc;
        this.tvBlocName.setText(bloc == null ? getString(R.string.mc_bloc_noselect) : bloc.getNomBloc());
        this.tvBlocName.setEnabled(bloc != null);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onGroupeEtEffetSelected(GroupeEtEffet groupeEtEffet) {
        ArrayList<String> informations = new ArrayList<>();
        new InformationGroupeEtEffetTask(informations,this).execute(groupeEtEffet.getRefEffet());
        if (informations.isEmpty()) {
            try {
                // La tache de remplissage de liste étant en parallèle au thread actuel, la liste d' informations n'a pas le temps de charger
                // On lance donc un thread pour attendre son remplissage
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.getMessage();
            }
        }
        for (int i = 0; i < informations.size(); i++){
            Toast.makeText(getContext(),informations.get(i),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGroupeEtEffetLongClick(GroupeEtEffet groupeEtEffet) {
        new RemoveLienDialog(this,this.getContext(),groupeEtEffet).show();
    }

    @Override
    public void onAddLien() {
        Log.d("ADDLIEN", "Rafraîchissement de la bdd  ");
        this.refreshLien();
    }

    public void refreshLien(){
        new PopulateGroupeEtEffetTask(this.adapter,this).execute(selectedBloc.getIdBloc());
    }

    @Override
    public void onRemoveLien() {
        //Rafraichissement de la BDD post suppression
        this.populateWithGroupeEtEffet(selectedBloc);
        this.getListener().onLienDelete(selectedBloc);
    }

    /**
     * Get the adapter
     * @return
     */
    public RecyclerGroupeEtEffetAdapter getAdapter(){return adapter;}

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRightFragmentInteractionListener {
        /**
         * Fired when fragment is loaded
         */
        void onFragmentLoaded();

        /**
         * Appelé quand le bloc change de nom
         */
        void onBlocNameChange(Chore selectedChore);

        /**
         * Appeler quand le lien est supprimé
         */
        void onLienDelete(Bloc bloc);

    }
}
