package fr.eseo.dis.tristan.batucadacommander.activity;

import android.os.Bundle;
import android.util.Log;

import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.communication.enums.SerialComResult;
import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Groupe;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.CenterFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.LeftFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.RightFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.task.TestMachinesTask;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.task.UpdateMachineTask;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.task.UpdateMachinesTask;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dialog.ErrorDialog;

public class MachineActivity extends BatucadaActivity implements RightFragment.OnMachineInteractionListener, LeftFragment.OnLeftFragmentInteractionListener, CenterFragment.OnCenterFragmentInteractionListener {
    private static final String MACHINE_DEBUG = "MACHINE";

    private LeftFragment leftFragment;
    private CenterFragment centerFragment;
    private RightFragment rightFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine);
        setToolbarTitle(R.string.param_machines);
        setModeName("CONFIGURATION");

        // Show fragments
        this.configureAndShowLeftFragment();
        this.configureAndShowCenterFragment();
        this.configureAndShowRightFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.testMachineFull(false);
    }


    // --------------
    // FRAGMENTS
    // --------------


    /**
     * Configure and show the left fragment
     */
    private void configureAndShowLeftFragment() {
        this.leftFragment = (LeftFragment) getSupportFragmentManager().findFragmentById(R.id.machine_activity_left_frame);

        if(this.leftFragment == null) {
            this.leftFragment = LeftFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.machine_activity_left_frame, this.leftFragment)
                    .commit();
        }
    }

    /**
     * Configure and show the center fragment
     */
    private void configureAndShowCenterFragment() {
        this.centerFragment = (CenterFragment) getSupportFragmentManager().findFragmentById(R.id.machine_activity_center_frame);

        if(this.centerFragment == null) {
            this.centerFragment = CenterFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.machine_activity_center_frame, this.centerFragment)
                    .commit();
        }
    }

    /**
     * Configure and show the right fragment
     */
    private void configureAndShowRightFragment() {
        this.rightFragment = (RightFragment) getSupportFragmentManager().findFragmentById(R.id.machine_activity_right_frame);

        if(this.rightFragment == null) {
            this.rightFragment = RightFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.machine_activity_right_frame, this.rightFragment)
                    .commit();
        }
    }


    /**
     * Set up the Floating action button
     */
    private void setupFab() {
        //Add button
        SpeedDialView fabMenu = this.rightFragment.getMainView().findViewById(R.id.fab_add_machine);

        //Test all machines
        fabMenu.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_action_test_all, R.drawable.ic_settings_input_antenna_white_24dp)
                        .setFabBackgroundColor(BColor.ORANGE)
                        .setLabel(R.string.fab_action_test_label)
                        .create()
        );

        //Add Groupe
        fabMenu.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_action_add_groupe, R.drawable.ic_add_white_24dp)
                        .setFabBackgroundColor(BColor.BLUE)
                        .setLabel(R.string.fab_action_add_groupe_label)
                        .create()
        );

        //Add Machine
        fabMenu.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_action_add_machine, R.drawable.ic_add_white_24dp)
                        .setFabBackgroundColor(BColor.GREEN)
                        .setLabel(R.string.fab_action_add_machine_label)
                        .create()
        );



        //Handle the actions
        fabMenu.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                switch (actionItem.getId()) {
                    case R.id.fab_action_add_machine:
                        rightFragment.openAddMachineDialog();
                        return false;
                    case R.id.fab_action_add_groupe:
                        leftFragment.openAddGroupeDialog();
                        return false;
                    case R.id.fab_action_test_all:
                        testMachineFull(true);
                        return false;
                    default:
                        return false;
                }
            }
        });
    }


    /**
     * Select no group
     */
    private void selectNoGroup() {
        //Set selected button to nothing
        this.leftFragment.getAdapter().selectButton(null);
        this.leftFragment.populateGroups();

        //Set no group selected
        this.centerFragment.populateWithMachines(null);
        this.rightFragment.refreshMachines();
    }


    ////////////////////////
    // LISTENERS
    ////////////////////////


    @Override
    public void onGroupeChange(Groupe groupe) {
        Log.d("GROUPE", "Le groupe '" + groupe.getNom() + "' est selectionné");
        this.centerFragment.populateWithMachines(groupe);
    }

    @Override
    public void onGroupeDelete() {
        //Refresh machines on the right
        this.selectNoGroup();
    }

    @Override
    public void onGroupNameChange() {
        Log.d("GROUPE", "Le nom d'un groupe à changé !");
        this.leftFragment.populateGroups();
    }

    @Override
    public void onMachineMoveToPool(Machine machine) {
        Groupe group = this.leftFragment.getSelectedGroup();

        if(group != null) {
            Log.d(MACHINE_DEBUG, "Retrait de la machine au groupe " + group);

            machine.setRefGroupe(null);

            new UpdateMachineTask(
                    this.getApplicationContext(),
                    this.centerFragment,
                    this.rightFragment,
                    group.getIdGroupe())
                    .execute(machine);
        }
    }


    @Override
    public void onMachineMoveToGroup(Machine machine) {
        Groupe group = this.leftFragment.getSelectedGroup();

        if(group != null) {
            Log.d(MACHINE_DEBUG, "Ajout de la machine au groupe " + group);
            machine.setRefGroupe(group.getIdGroupe());
            machine.setIntensity(group.getIntensity());

            new UpdateMachineTask(
                    this.getApplicationContext(),
                    this.centerFragment,
                    this.rightFragment,
                    group.getIdGroupe())
                    .execute(machine);
        }
    }

    @Override
    public void onMachineClicked(Machine machine) {
        this.testMachine(machine, true);
    }

    /**
     * Test a machine and update the icon
     * Can also send a ui response in case of error
     * @param machine The machine to test
     * @param uiResponse True if you want to show dialog on error, false otherwise
     */
    public void testMachine(Machine machine, boolean uiResponse) {
        Log.d(MACHINE_DEBUG, "Lancement du test machine : " + machine);
        SerialComResult result = this.getCommunicationManager().testMachine(machine);

        boolean connected = !SerialComResult.ERROR_MESSAGE_TIMEOUT.equals(result);

        if(!connected && uiResponse) {
            new ErrorDialog(
                    this,
                    this.getString(R.string.com_error_init_title),
                    this.getString(R.string.com_error_timeout_msg, machine.getAdresse())
            ).show();
        }

        //Récupération de l'état précédent
        boolean oldState = machine.getState();
        Groupe group = this.leftFragment.getSelectedGroup();

        if(oldState != connected) {
            //Update les machines
            machine.setState(connected);
            new UpdateMachineTask(
                    this.getApplicationContext(),
                    this.centerFragment,
                    this.rightFragment,
                    (group == null ? null : group.getIdGroupe()))
                    .execute(machine);
        }
    }

    /**
     * Test all the machines registered in the database
     * Can also send a ui response in case of error
     * @param uiResponse True if you want to show dialog on error, false otherwise
     */
    public void testMachineFull(boolean uiResponse) {
        Log.d(MACHINE_DEBUG, "Lancement du test sur toutes les machines");

        //Prepare the update task
        Groupe group = this.leftFragment.getSelectedGroup();
        UpdateMachinesTask updateMachinesTask = new UpdateMachinesTask(
                this.getApplicationContext(),
                this.centerFragment,
                this.rightFragment,
                (group == null ? null : group.getIdGroupe()));

        //Execute the test
        new TestMachinesTask(this, updateMachinesTask, uiResponse).execute();
    }


    @Override
    public void onMachineRemoved() {
        Log.d(MACHINE_DEBUG, "Retrait d'une machine -> rafraichissement");
        Groupe group = this.leftFragment.getSelectedGroup();

        new UpdateMachineTask(
                this.getApplicationContext(),
                this.centerFragment,
                this.rightFragment,
                (group == null ? null : group.getIdGroupe()))
                .execute();
    }

    @Override
    public void onFragmentLoaded() {
        //Setup the floating action button
        this.setupFab();
    }

}
