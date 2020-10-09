package fr.eseo.dis.tristan.batucadacommander.fragment.machine;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Groupe;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;
import fr.eseo.dis.tristan.batucadacommander.fragment.MainFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.task.ChangeGroupIntensityTask;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.task.ChangeGroupNameTask;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.task.PopulateMachinesTask;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.adapter.RecyclerMachinesAdapter;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dragdrop.DropFragmentEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dragdrop.MachineDragDropListener;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dragdrop.MachineEventListener;

/**
 * @author Tristan LE GACQUE
 * A simple {@link Fragment} subclass.
 * Use the {@link CenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CenterFragment extends MainFragment<CenterFragment.OnCenterFragmentInteractionListener> implements MachineEventListener {

    private RecyclerMachinesAdapter adapter;
    private EditText tvGroupName;
    private SeekBar bar;
    private Groupe currentGroup;

    /**
     * The center fragment
     */
    public CenterFragment() {
        super();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment
     *
     * @return A new instance of fragment CenterFragment.
     */
    public static CenterFragment newInstance() {
        return new CenterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LinearLayout mainView = (LinearLayout) inflater.inflate(R.layout.fragment_machine_center, container, false);
        mainView.setOnDragListener(new MachineDragDropListener(DropFragmentEnum.GROUP, this));

        //Text views
        this.tvGroupName = mainView.findViewById(R.id.tv_mc_groupe_name);
        this.currentGroup = null;

        //RecyclerView
        RecyclerView recyclerMachineView = mainView.findViewById(R.id.recycler_machines);
        this.adapter = new RecyclerMachinesAdapter(this.getContext(), this, DropFragmentEnum.GROUP);
        recyclerMachineView.setAdapter(this.adapter);
        recyclerMachineView.setLayoutManager(new GridLayoutManager(this.getContext(), 3));

        this.tvGroupName.setText(getString(R.string.mc_group_noselect));
        this.tvGroupName.setEnabled(false);
        this.tvGroupName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    changeGroupName(v.getText().toString());
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        //Intensity
        this.bar = mainView.findViewById(R.id.param_lumin);

        this.bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Update input value
                if(currentGroup != null) {
                    setGroupIntensity(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        return mainView;
    }


    /**
     * Change the group name to the one passed
     * @param name The new name
     */
    private void changeGroupName(String name) {
        this.currentGroup.setNom(name);
        new ChangeGroupNameTask(this).execute(this.currentGroup);

        this.getListener().onGroupNameChange();
    }

    /**
     * Get the adapter
     * @return The adapter
     */
    public RecyclerMachinesAdapter getAdapter() {
        return adapter;
    }


    //////////////////////////
    // LISTENERS
    ////////////////////////

    /**
     * Populate the center view with machines of the group
     * @param groupe The group associated
     */
    public void populateWithMachines(@Nullable Groupe groupe) {
        int groupId = groupe == null ? -1 : groupe.getIdGroupe();
        new PopulateMachinesTask(adapter, this.getContext()).execute(groupId);

        //Update groupe name
        this.currentGroup = groupe;
        this.tvGroupName.setText(groupe == null ? getString(R.string.mc_group_noselect) : groupe.getNom());
        this.tvGroupName.setEnabled(groupe != null);

        //Set bar for intensity
        if(this.currentGroup != null) {
            ((View) bar.getParent()).setVisibility(View.VISIBLE);
            Integer intensity = this.currentGroup.getIntensity();

            if(intensity == null) {
                intensity = 100;
            }

            bar.setProgress(intensity);
            this.setGroupIntensity(intensity);
        }
    }

    /**
     * Persist group intensity to database
     * @param intensity The intensity to set
     */
    private void setGroupIntensity(int intensity) {
        //Set label
        View view = (View) this.bar.getParent();
        TextView label = view.findViewById(R.id.param_lumin_label);
        label.setText(getResources().getString(R.string.param_lumin, intensity, '%'));

        //Set group intensity if change
        if(this.currentGroup.getIntensity() != intensity) {
            this.currentGroup.setIntensity(intensity);

            //Update in database
            new ChangeGroupIntensityTask(this).execute(this.currentGroup);
        }
    }

    @Override
    public void onMachineMove(Machine machine, DropFragmentEnum from) {
        this.getListener().onMachineMoveToGroup(machine);
    }

    @Override
    public void onMachineDrop(Machine machine, DragEvent event) {
        //Do nothing
    }

    @Override
    public void onMachineClicked(Machine machine, int position) {
        this.getListener().onMachineClicked(machine);
    }

    @Override
    public void onMachineEnterDropzone(Machine machine) {
        //Do nothing
    }

    @Override
    public void onMachineExitDropzone(Machine machine) {
        //Do nothing
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnCenterFragmentInteractionListener{
        /**
         * Fired when group name change
         */
        void onGroupNameChange();

        /**
         * Fired when machine is selected
         * @param machine The machine
         */
        void onMachineMoveToGroup(Machine machine);

        /**
         * Fired when machine is clicked
         * @param machine The machine
         */
        void onMachineClicked(Machine machine);
    }

}
