package fr.eseo.dis.tristan.batucadacommander.activity;

import android.os.Bundle;
import android.util.Log;

import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Bloc;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Chore;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.CenterFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.LeftFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.RightFragment;

public class ChoreCreationActivity extends BatucadaActivity implements LeftFragment.OnLeftFragmentInteractionListener,CenterFragment.OnCenterFragmentInteractionListener,RightFragment.OnRightFragmentInteractionListener{


    private LeftFragment leftFragment;
    private CenterFragment centerFragment;
    private RightFragment rightFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chore_creation);
        setToolbarTitle(R.string.crea_chore);
        setModeName("CHOREE");


        //Show fragment
        this.configureAndShowLeftFragment();
        this.configureAndShowCenterFragment();
        this.configureAndShowRightFragment();
    }


    /////////////
    // Fragment
    /////////////
    /**
     * Configure and show the left fragment
     */
    private void configureAndShowLeftFragment() {
        this.leftFragment = (LeftFragment) getSupportFragmentManager().findFragmentById(R.id.chore_creation_activity_left_frame);

        if(this.leftFragment == null) {
            this.leftFragment = LeftFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.chore_creation_activity_left_frame, this.leftFragment)
                    .commit();
        }
    }
    /**
     * Configure and show the center fragment
     */
    private void configureAndShowCenterFragment() {
        this.centerFragment = (CenterFragment) getSupportFragmentManager().findFragmentById(R.id.chore_creation_activity_center_frame);

        if(this.centerFragment == null) {
            this.centerFragment = CenterFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.chore_creation_activity_center_frame, this.centerFragment)
                    .commit();
        }
    }
    /**
     * Configure and show the right fragment
     */
    private void configureAndShowRightFragment() {
        this.rightFragment = (RightFragment) getSupportFragmentManager().findFragmentById(R.id.chore_creation_activity_right_frame);

        if(this.rightFragment == null) {
            this.rightFragment = RightFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.chore_creation_activity_right_frame, this.rightFragment)
                    .commit();
        }
    }

    public void selectNoChore(){
        //Set selected button to nothing
        this.leftFragment.getAdapter().selectButton(null);
        this.leftFragment.populateChores();
    }

    public void selectNoBloc(Chore chore){
        this.centerFragment.getAdapter().selectButton(null);
        this.centerFragment.populateWithBlocs(chore);
    }

    public void selectNoLien(Bloc bloc){
        this.rightFragment.getAdapter().selectButton(null);
        this.rightFragment.populateWithGroupeEtEffet(bloc);
    }

    private void setupFab(){
        //Add button
        SpeedDialView fabMenu = this.rightFragment.getMainView().findViewById(R.id.fab_chore_creation);

        //Add Chore
        fabMenu.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_action_chore_add_chore,R.drawable.ic_add_white_24dp)
                .setLabel(R.string.fab_action_add_chore)
                .create()
        );


        //Add Bloc
        fabMenu.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_action_chore_add_bloc,R.drawable.ic_add_white_24dp)
                .setLabel(R.string.fab_action_add_bloc)
                .create()

        );


        //Gérer les liens groupes - effets
        fabMenu.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_action_chore_change_groupe_effet,R.drawable.ic_add_white_24dp)
                .setLabel(R.string.fab_action_gestion)
                .create()
        );





        //Gère les actions
        fabMenu.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                switch (actionItem.getId()){
                    case R.id.fab_action_chore_add_chore:
                        leftFragment.openAddChoreDialog();
                        return false;

                    case R.id.fab_action_chore_add_bloc:
                        centerFragment.openAddBlocDialog();
                        return false;
                    case R.id.fab_action_chore_change_groupe_effet:
                        rightFragment.openGestionDialog();
                        return false;
                        default:
                            return false;
                }
            }
        });
    }

    //////////////////
    // Listeners
    //////////////////

    @Override
    public void onChoreChange(Chore chore) {
        Log.d("CHORE", "La choree " + chore.getNomChore() + "est selectionné");
        this.centerFragment.populateWithBlocs(chore);
        this.centerFragment.setSelectedChore(chore);
    }

    @Override
    public void onChoreDelete() {
        this.selectNoChore();
    }


    public void onBlocChange(Bloc bloc, Chore selectedChore){
        Log.d("CHORE", "La choree " + bloc.getNomBloc() + "est selectionné");
        this.rightFragment.populateWithGroupeEtEffet(bloc);
        this.rightFragment.setSelectedBloc(bloc,selectedChore);
    }

    @Override
    public void onBlocDelete(Chore chore) {
        this.selectNoBloc(chore);
    }

    @Override
    public void onChoreNameChange() {
        Log.d("CHORE", "Le nom d'une choree a change: ");
        this.leftFragment.populateChores();
    }


    @Override
    public void onFragmentLoaded() {
        //Setup the floating action button
        this.setupFab();
    }

    @Override
    public void onBlocNameChange(Chore selectedChore) {
        Log.d("BLOC", "Le nom d'un Bloc a change: ");
    this.centerFragment.populateWithBlocs(selectedChore);
    }

    @Override
    public void onLienDelete(Bloc bloc) {
        this.selectNoLien(bloc);
    }
}
