package fr.eseo.dis.tristan.batucadacommander.fragment.machine;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;
import fr.eseo.dis.tristan.batucadacommander.fragment.MainFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.task.PopulateMachinesTask;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.adapter.RecyclerMachinesAdapter;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dialog.AddMachineDialog;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dialog.RemoveMachineDialog;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dragdrop.DropFragmentEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dragdrop.MachineDragDropListener;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dragdrop.MachineEventListener;

/**
 * @author Tristan LE GACQUE
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnMachineInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RightFragment extends MainFragment<RightFragment.OnMachineInteractionListener> implements MachineEventListener, AddMachineDialog.OnMachineAddedListener, RemoveMachineDialog.OnMachineRemovedListener {

    private RecyclerMachinesAdapter adapter;

    private View deleteView;
    private ViewGroup mainView;
    /**
     * The right fragment
     */
    public RightFragment() {
        super();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment
     *
     * @return A new instance of fragment RightFragment.
     */
    public static RightFragment newInstance() {
        return new RightFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        this.mainView = (ViewGroup) inflater.inflate(R.layout.fragment_machine_right, container, false);
        this.mainView.setOnDragListener(new MachineDragDropListener(DropFragmentEnum.POOL, this));

        //RecyclerView
        RecyclerView recyclerMachineView = mainView.findViewById(R.id.recycler_machines);
        this.adapter = new RecyclerMachinesAdapter(this.getContext(), this, DropFragmentEnum.POOL);
        recyclerMachineView.setAdapter(this.adapter);
        recyclerMachineView.setLayoutManager(new GridLayoutManager(this.getContext(), 3));

        this.deleteView = mainView.findViewById(R.id.delete_machine_tv);

        //Populate the adapter
        new PopulateMachinesTask(adapter, this.getContext()).execute();

        //Notify
        this.getListener().onFragmentLoaded();
        return mainView;
    }

    /**
     * Get the adapter
     * @return The adapter
     */
    public RecyclerMachinesAdapter getAdapter() {
        return adapter;
    }

    /**
     * Get the main view
     * @return The main view
     */
    public ViewGroup getMainView() {
        return mainView;
    }


    ////////////////////////
    /// BUTTON EVENT
    ////////////////////////

    /**
     * Open machine add dialog
     */
    public void openAddMachineDialog() {
        Log.d("DEBUG", "Ouverture du dialog d'ajout de machine");
        new AddMachineDialog(this, this.getActivity()).show();
    }


    //////////////////////////
    // LISTENERS
    ////////////////////////

    /**
     * Refresh machines on right fragment
     */
    public void refreshMachines() {
        new PopulateMachinesTask(adapter, this.getContext()).execute();
    }



    @Override
    public void onAddMachine() {
        Log.d("ADDMACHINE", "Rafraichissement de la base de données");
        this.refreshMachines();
    }

    @Override
    public void onMachineMove(Machine machine, DropFragmentEnum from) {
        this.getListener().onMachineMoveToPool(machine);
    }

    @Override
    public void onMachineDrop(Machine machine, DragEvent event) {
        Rect offsetViewBounds = new Rect();
        //returns the visible bounds
        this.deleteView.getDrawingRect(offsetViewBounds);
        // calculates the relative coordinates to the parent
        this.mainView.offsetDescendantRectToMyCoords(this.deleteView, offsetViewBounds);

        //Check if dropped inside the DELETE ZONE
        if(offsetViewBounds.contains((int) event.getX(), (int) event.getY())) {
            new RemoveMachineDialog(this, this.getContext(), machine).show();
        }
    }

    @Override
    public void onMachineClicked(Machine machine, int position) {
        this.getListener().onMachineClicked(machine);
    }

    @Override
    public void onMachineEnterDropzone(Machine machine) {
        this.deleteView.setVisibility(View.VISIBLE);
        this.deleteView.bringToFront();
    }

    @Override
    public void onMachineExitDropzone(Machine machine) {
        this.deleteView.setVisibility(View.GONE);
    }

    @Override
    public void onRemoveMachine() {
        this.getListener().onMachineRemoved();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnMachineInteractionListener {
        /**
         * Fired when machine is selected
         * @param machine The machine
         */
        void onMachineMoveToPool(Machine machine);

        /**
         * Fired when machione is clicked
         * @param machine The machine
         */
        void onMachineClicked(Machine machine);

        /**
         * Fired when a machine is removed
         */
        void onMachineRemoved();

        /**
         * Fired when fragment is loaded
         */
        void onFragmentLoaded();
    }


}
