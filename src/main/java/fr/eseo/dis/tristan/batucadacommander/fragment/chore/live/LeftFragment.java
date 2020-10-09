package fr.eseo.dis.tristan.batucadacommander.fragment.chore.live;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Chore;
import fr.eseo.dis.tristan.batucadacommander.fragment.MainFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.task.PopulateChoreTask;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.ui.adapter.RecyclerChoreAdapter;

public class LeftFragment extends MainFragment<LeftFragment.OnLeftFragmentInteractionListener> implements RecyclerChoreAdapter.ItemClickListener {

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
        View mainView = inflater.inflate(R.layout.fragment_chore_live_left,container,false);

        //RecyclerView
        RecyclerView recyclerChoreView = mainView.findViewById(R.id.recycler_chore_live);
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

    }


    ////////////////////
    // LISTERNERS
    ///////////////////

    public void populateChores() {
        new PopulateChoreTask(this.adapter, this).execute();
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


    }
}
