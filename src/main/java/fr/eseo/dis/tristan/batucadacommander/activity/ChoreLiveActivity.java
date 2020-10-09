package fr.eseo.dis.tristan.batucadacommander.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.R;

import fr.eseo.dis.tristan.batucadacommander.communication.enums.SerialComResult;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Bloc;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Chore;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.CenterFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.LeftFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.Objet.MachinesEtEffect;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.RightFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.Effect;

public class ChoreLiveActivity extends BatucadaActivity implements LeftFragment.OnLeftFragmentInteractionListener,CenterFragment.OnCenterFragmentInteractionListener,RightFragment.OnRightFragmentInteractionListener{

    private LeftFragment leftFragment;
    private CenterFragment centerFragment;
    private RightFragment rightFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chore_live);
        setToolbarTitle(R.string.live_chore);
        setModeName("LIVE-CHOREE");

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
        this.leftFragment = (LeftFragment) getSupportFragmentManager().findFragmentById(R.id.chore_live_activity_left_frame);

        if(this.leftFragment == null) {
            this.leftFragment = LeftFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.chore_live_activity_left_frame, this.leftFragment)
                    .commit();
        }
    }
    /**
     * Configure and show the center fragment
     */
    private void configureAndShowCenterFragment() {
        this.centerFragment = (CenterFragment) getSupportFragmentManager().findFragmentById(R.id.chore_live_activity_center_frame);

        if(this.centerFragment == null) {
            this.centerFragment = CenterFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.chore_live_activity_center_frame, this.centerFragment)
                    .commit();
        }
    }
    /**
     * Configure and show the right fragment
     */
    private void configureAndShowRightFragment() {
        this.rightFragment = (RightFragment) getSupportFragmentManager().findFragmentById(R.id.chore_live_activity_right_frame);

        if(this.rightFragment == null) {
            this.rightFragment = RightFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.chore_live_activity_right_frame, this.rightFragment)
                    .commit();
        }
    }

    @Override
    public void onChoreChange(Chore chore) {
        Log.d("CHORE", "La choree " + chore.getNomChore() + "est selectionné");
        this.centerFragment.populateWithBlocs(chore);
        this.centerFragment.setSelectedChore(chore);
    }

    @Override
    public void onBlocChange(Bloc bloc, Chore selectedChore,ArrayList<MachinesEtEffect> machinesEtEffect) {
        Log.d("BLOC", "Le bloc " + bloc.getNomBloc() + "est selectionné");
        SerialComResult result;
        int errorCount = 0;
        for (int i = 0; i<machinesEtEffect.size();i++){
            Effect effect = machinesEtEffect.get(i).getEffect();
            List<Machine> machines = machinesEtEffect.get(i).getMachines();
            if ((!machines.isEmpty()) && (!(effect == null))){
                ArrayList<Machine> arrayMachines = new ArrayList<>();
                for (int y = 0; y < machines.size(); y++) {
                    arrayMachines.add(machines.get(y));
                }
                result = this.getCommunicationManager().sendEffectToMachine(effect, machines.get(0).getRefGroupe(), arrayMachines, machines.get(0).getIntensity());
                if (SerialComResult.ERROR_SERIAL_NOT_CONNECTED.equals(result) || SerialComResult.ERROR_NOT_INITIALIZED.equals(result)) {
                    errorCount++;
                }
                //Check errors
                if (errorCount == machines.size()) {
                    this.initCommunicationManager();
                }
            } else {
                Toast.makeText(this.getApplicationContext(),"Aucune Machine n'est présente dans les groupes ou Aucun effet associé",Toast.LENGTH_LONG).show();
            }
       }


    }

    @Override
    public void onBtnPressed(ArrayList<MachinesEtEffect> machinesEtEffects) {
        Log.d("BTN", "Le bouton stop a été invoqué");
        //On envoie un message vide pour tout eles machines
        SerialComResult result;
        int errorCount = 0;
        for (int i = 0; i<machinesEtEffects.size();i++){
            Effect effect = machinesEtEffects.get(i).getEffect();
            List<Machine> machines = machinesEtEffects.get(i).getMachines();
            if ((!machines.isEmpty()) && (!(effect == null))){
                ArrayList<Machine> arrayMachines = new ArrayList<>();
                for (int y = 0; y < machines.size(); y++) {
                    arrayMachines.add(machines.get(y));
                }
                result = this.getCommunicationManager().sendEffectToMachine(effect, machines.get(0).getRefGroupe(), arrayMachines, machines.get(0).getIntensity());
                if (SerialComResult.ERROR_SERIAL_NOT_CONNECTED.equals(result) || SerialComResult.ERROR_NOT_INITIALIZED.equals(result)) {
                    errorCount++;
                }
                //Check errors
                if (errorCount == machines.size()) {
                    this.initCommunicationManager();
                }
            } else {
                Toast.makeText(this.getApplicationContext(),"Aucune Machine n'est présente dans les groupes ou Aucun effet associé",Toast.LENGTH_LONG).show();
            }
        }

        //On deselectionne le bloc
    }
}
