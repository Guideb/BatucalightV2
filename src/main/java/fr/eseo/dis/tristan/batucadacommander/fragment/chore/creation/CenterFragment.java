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

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Bloc;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Chore;
import fr.eseo.dis.tristan.batucadacommander.fragment.MainFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.task.ChangeChoreNameTask;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.task.PopulateBlocTask;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.ui.adapter.RecyclerBlocAdapter;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.ui.dialog.AddBlocDialog;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.ui.dialog.RemoveBlocDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CenterFragment.OnCenterFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CenterFragment extends MainFragment<CenterFragment.OnCenterFragmentInteractionListener> implements RecyclerBlocAdapter.ItemClickListener,AddBlocDialog.OnBlocAddedListener, RemoveBlocDialog.OnBlocRemovedListener {
     private RecyclerBlocAdapter adapter;
     private Chore selectedChore;
     private Bloc selectedBloc;
     private EditText tvChoreName;

    public CenterFragment() {
        super();    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CenterFragment.
     */
    public static CenterFragment newInstance() {
        return new CenterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_chore_creation_center,container,false);

        //Text Views
        this.tvChoreName = mainView.findViewById(R.id.nom_choree);
        this.selectedChore = null;

        //Recycler view
        RecyclerView recyclerBlocView = mainView.findViewById(R.id.recycler_bloc);
        this.adapter = new RecyclerBlocAdapter(this.getContext(), this);
        recyclerBlocView.setAdapter(adapter);
        recyclerBlocView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        this.tvChoreName.setText((R.string.mc_chore_noselect));
        this.tvChoreName.setEnabled(false);
        this.tvChoreName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    changeChoreName(v.getText().toString());
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                    return true;
                }
                return false;
            }
        });
        return mainView;
    }


    private void changeChoreName(String name){
        this.selectedChore.setNomChore(name);
        new ChangeChoreNameTask(this).execute(this.selectedChore);

        this.getListener().onChoreNameChange();
    }

    public void setSelectedChore(Chore chore){
        this.selectedChore = chore;
    }

    public void openAddBlocDialog(){
        Log.d("DEBUG", "Ouverture du dialog d'ajout d'un bloc ");
        if (selectedChore == null){
            CharSequence text = "Veuillez selectionner une chorée !";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(this.getContext(),text,duration);
            toast.show();
        }else {
            new AddBlocDialog(this, this.getActivity(), selectedChore).show();
        }
    }



    ////////////////////
    // LISTENERS
    ////////////////////

    /**
     * Populate bloc list
     */
    public void populateWithBlocs(Chore chore) {
        int choreId = chore == null ? -1 : chore.getIdChore();
        new PopulateBlocTask(this.adapter,this).execute(choreId);

        //Update the bloc
        this.selectedChore = chore;
        this.tvChoreName.setText(chore == null ? getString(R.string.mc_chore_noselect) : chore.getNomChore());
        this.tvChoreName.setEnabled(chore != null);
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onBlocSelected(Bloc bloc) {
        this.selectedBloc = bloc;

        //Tells Activity
        this.getListener().onBlocChange(bloc,selectedChore);
    }



    @Override
    public void onBlocLongClick(Bloc bloc) {
        new RemoveBlocDialog(this,this.getContext(),bloc).show();
    }

    /**
     * Rafraichir la page pour afficher le nouveau Bloc
     */
    public void refreshBloc(){
        new PopulateBlocTask(this.adapter,this).execute(selectedChore.getIdChore());
    }

    @Override
    public void onAddBloc() {
        Log.d("ADDBLOC", "Rafraîchissement de la bdd ");
        this.refreshBloc();
    }

    @Override
    public void onRemoveBloc() {
        //Rafraichissement de la BDD post suppression
        this.selectedBloc = null;
        this.populateWithBlocs(selectedChore);
        this.getListener().onBlocDelete(selectedChore);
    }

    /**
     * Get the adapter
     * @return
     */
    public RecyclerBlocAdapter getAdapter(){return adapter;}


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnCenterFragmentInteractionListener {

        /**
         * Appeler quand la choree change de nom
         */
        void onChoreNameChange();

        /**
         * Appeler quand un bloc change
         */
        void onBlocChange(Bloc bloc, Chore selectedChore);

        /**
         * Appeler quand le bloc est supprimé
         */
        void onBlocDelete(Chore chore);
    }
}
