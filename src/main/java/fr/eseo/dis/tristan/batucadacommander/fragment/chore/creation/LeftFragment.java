package fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Chore;
import fr.eseo.dis.tristan.batucadacommander.fragment.MainFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.task.PopulateChoreTask;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.ui.adapter.RecyclerChoreAdapter;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.ui.dialog.AddChoreDialog;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.ui.dialog.RemoveChoreDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LeftFragment.OnLeftFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LeftFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeftFragment extends MainFragment<LeftFragment.OnLeftFragmentInteractionListener> implements RecyclerChoreAdapter.ItemClickListener, RemoveChoreDialog.OnChoreRemovedListener,AddChoreDialog.OnChoreAddedListener {


    private RecyclerChoreAdapter adapter;
    private Chore selectedChore;


    /**
     * Construtor of the left chore config fragment
     */
    public LeftFragment() {
        super();    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment LeftFragment.
     */
    public static LeftFragment newInstance() {
        return new LeftFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_chore_creation_left,container,false);

        //RecyclerView
        RecyclerView recyclerChoreView = mainView.findViewById(R.id.recycler_chore);
        this.adapter = new RecyclerChoreAdapter(this.getContext(), this);
        recyclerChoreView.setAdapter(adapter);
        recyclerChoreView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //Populate the adapter
        this.populateChores();
        return mainView;
    }

    @Override
    public void onChoreSelected(Chore chore) {
        this.selectedChore = chore;

        //Tells Activity
        this.getListener().onChoreChange(chore);
    }

    @Override
    public void onChoreLongClick(Chore chore) {
        new RemoveChoreDialog(this, this.getContext(),chore).show();
    }


    @Override
    public void onRemoveChore() {
        //Repopulate on delete
        this.selectedChore = null;
        this.populateChores();
        this.getListener().onChoreDelete();
    }


    /**
     * Get the selected chore
     * @return The chore selected
     */
    public Chore getSelectedChore(){
        return selectedChore;
    }

    public void openAddChoreDialog(){
        Log.d("DEBUG", "Ouverture du dialog d'ajout de choree ");
        new AddChoreDialog(this, this.getActivity()).show();
    }

    ////////////
    // LISTENERS
    ////////////

    /**
     * Populate groups list
     */
    public void populateChores() {
        new PopulateChoreTask(this.adapter, this).execute();
    }

    /**
     * Rafraichir la page pour afficher la nouvelle choree
     */
    public void refreshChore(){
        new PopulateChoreTask(this.adapter,this).execute();
    }

    @Override
    public void onAddChore() {
        Log.d("ADDCHORE", "Rafraichissement de la bdd ");
        this.refreshChore();
    }


    /**
     *  Get the adapter.
     * @return The adapter
     */
    public RecyclerChoreAdapter getAdapter(){return adapter;}


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
        public interface OnLeftFragmentInteractionListener {

        /**
         * Event fired when chore change
         * @param chore The chore
         */
        void onChoreChange(Chore chore);

        /**
         * Event fired when chore deleted
         */
        void onChoreDelete();
    }
}
