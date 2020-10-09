package fr.eseo.dis.tristan.batucadacommander.fragment.machine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Groupe;
import fr.eseo.dis.tristan.batucadacommander.fragment.MainFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.task.PopulateGroupsTask;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.adapter.RecyclerGroupsAdapter;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dialog.AddGroupeDialog;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dialog.RemoveGroupDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LeftFragment.OnLeftFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LeftFragment#newInstance} factory method to
 * create an instance of this fragment.
 * @author Tristan LE GACQUE
 */
public class LeftFragment extends MainFragment<LeftFragment.OnLeftFragmentInteractionListener> implements RecyclerGroupsAdapter.ItemClickListener, RemoveGroupDialog.OnGroupeRemovedListener, AddGroupeDialog.OnGroupeAddedListener {

    private RecyclerGroupsAdapter adapter;
    private Groupe selectedGroup;

    /**
     * Constructor of left machine config fragment
     */
    public LeftFragment() {
        super();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LeftFragment.
     */
    public static LeftFragment newInstance() {
        return new LeftFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_machine_left, container, false);

        //RecyclerView
        RecyclerView recyclerGroupView = mainView.findViewById(R.id.recycler_groups);
        this.adapter = new RecyclerGroupsAdapter(this.getContext(), this);
        recyclerGroupView.setAdapter(this.adapter);
        recyclerGroupView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //Populate the adapter
        this.populateGroups();
        return mainView;
    }

    @Override
    public void onGroupSelected(Groupe groupe) {
        this.selectedGroup = groupe;

        //Tells Activity
        this.getListener().onGroupeChange(groupe);
    }

    @Override
    public void onGroupLongClick(Groupe groupe) {
        new RemoveGroupDialog(this, this.getContext(), groupe).show();
    }

    @Override
    public void onRemoveGroupe() {
        //Repopulate on delete
        this.selectedGroup = null;
        this.populateGroups();
        this.getListener().onGroupeDelete();
    }

    /**
     * Get the selected Group
     * @return the selected group
     */
    public Groupe getSelectedGroup() {
        return selectedGroup;
    }

    /**
     * Open machine add dialog
     */
    public void openAddGroupeDialog() {
        Log.d("DEBUG", "Ouverture du dialog d'ajout de groupe");
        new AddGroupeDialog(this, this.getActivity()).show();
    }

    /////////////////
    // LISTENERS
    /////////////////

    /**
     * Populate groups list
     */
    public void populateGroups() {
        new PopulateGroupsTask(this.adapter, this).execute();
    }

    /**
     * Get the adapter
     * @return The adapter
     */
    public RecyclerGroupsAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onAddGroupe() {
        this.populateGroups();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnLeftFragmentInteractionListener {
        /**
         * Event fired when groupe change
         * @param groupe The groupe
         */
        void onGroupeChange(Groupe groupe);

        /**
         * Event fired when group deleted
         */
        void onGroupeDelete();
    }
}
