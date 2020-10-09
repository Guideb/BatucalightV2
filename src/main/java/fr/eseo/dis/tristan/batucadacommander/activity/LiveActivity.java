package fr.eseo.dis.tristan.batucadacommander.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.communication.LogListener;
import fr.eseo.dis.tristan.batucadacommander.communication.enums.SerialComResult;
import fr.eseo.dis.tristan.batucadacommander.communication.task.PopulateMachinesTask;
import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.dao.MachineDao;
import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;
import fr.eseo.dis.tristan.batucadacommander.dialog.QuitConfirmDialog;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.CenterFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.LeftFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.RightFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.EffectEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.ParamEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.Effect;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.EffectArcenciel;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.EffectAuto;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.EffectColorDouble;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.EffectColorSimple;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.EffectFade;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.EffectNone;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.EffectStroboscope;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.MachineElement;

/**
 * @author Tristan LE GACQUE
 */
public class LiveActivity extends BatucadaActivity implements RightFragment.OnRightFragmentInteractionListener, LeftFragment.OnLeftFragmentInteractionListener, CenterFragment.OnCenterFragmentInteractionListener, LogListener {


    private RightFragment rightFragment;
    private LeftFragment leftFragment;
    private CenterFragment centerFragment;

    private EffectEnum effectType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbarTitle(R.string.mode_live);
        setModeName("LIVE");
        setWarnBeforeLeave(true);

        // Show fragments
        this.configureAndShowLeftFragment();
        this.configureAndShowCenterFragment();
        this.configureAndShowRightFragment();

        this.effectType = EffectEnum.NONE;
        this.getCommunicationManager().setListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.centerFragment.populateWithGroups();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    // --------------
    // FRAGMENTS
    // --------------


    /**
     * Configure and show the left fragment
     */
    private void configureAndShowLeftFragment() {
        this.leftFragment = (LeftFragment) getSupportFragmentManager().findFragmentById(R.id.main_activity_left_frame);

        if(this.leftFragment == null) {
            this.leftFragment = LeftFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_activity_left_frame, this.leftFragment)
                    .commit();
        }
    }

    /**
     * COnfigure and show the right fragment
     */
    private void configureAndShowRightFragment() {
        this.rightFragment = (RightFragment) getSupportFragmentManager().findFragmentById(R.id.main_activity_right_frame);

        if(this.rightFragment == null) {
            this.rightFragment = RightFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_activity_right_frame, this.rightFragment)
                    .commit();
        }
    }

    /**
     * Configure and show the center fragment
     */
    private void configureAndShowCenterFragment() {
        this.centerFragment = (CenterFragment) getSupportFragmentManager().findFragmentById(R.id.main_activity_center_frame);

        if(this.centerFragment == null) {
            this.centerFragment = CenterFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_activity_center_frame, this.centerFragment)
                    .commit();
        }
    }

    // --------------
    // LISTENERS
    // --------------


    @Override
    public void onEffectChange(EffectEnum effect) {
        Log.d("BATUCADA_COMMANDER", "L'effet selectionné est : " + effect.getName());
        this.rightFragment.changeEffectFragment(effect);
        this.effectType = effect;

        if(EffectEnum.NONE.equals(effect)) {
            this.onEffectParamsChange(null);
        } else {
            this.centerFragment.deselectAllGroups();
        }
    }

    @Override
    public void onEffectParamsChange(Map<ParamEnum, Object> params) {
        Effect effect = new EffectNone();
        BColor color1;
        BColor color2;
        Integer freq;
        Integer inte;

        switch (effectType) {
            case COLOR_SIMPLE:
                color1 = (BColor) params.get(ParamEnum.COLOR_1);
                effect = new EffectColorSimple(color1, 0);
                break;
            case COLOR_DOUBLE:
                color1 = (BColor) params.get(ParamEnum.COLOR_1);
                color2 = (BColor) params.get(ParamEnum.COLOR_2);
                freq = (Integer) params.get(ParamEnum.TIME);
                effect = new EffectColorDouble(color1, color2, freq);
                break;
            case STROBOSCOPE:
                color1 = (BColor) params.get(ParamEnum.COLOR_1);
                freq = (Integer) params.get(ParamEnum.TIME);
                Log.i("Live Activity ", "freq strob : " + freq);
                effect = new EffectStroboscope(color1, freq);
                break;
            case AUTO:
                color1 = (BColor) params.get(ParamEnum.COLOR_1);
                effect = new EffectAuto(color1,0);
                break;
            case FADE:
                color1 = (BColor) params.get(ParamEnum.COLOR_1);
                inte = (Integer) params.get(ParamEnum.INTENSITY);
                freq = (Integer) params.get(ParamEnum.TIME);
                effect = new EffectFade(color1,inte,inte);
                break;
            case ARCENCIEL:
                freq = (Integer) params.get(ParamEnum.TIME);
                effect = new EffectArcenciel(freq);
                break;
            case NONE:
                // Already defined in start of method
                break;
            default:
                break;
                //INFO - ADD all other effects here

        }
        
        this.centerFragment.onEffectChange(effect);
    }

    /**
     * Get the right fragment
     * @return The right fragment
     */
    RightFragment getRightFragment() {
        return rightFragment;
    }

    /**
     * Get the left fragment
     * @return The left fragment
     */
    LeftFragment getLeftFragment() {
        return leftFragment;
    }

    /**
     * Get the middle fragment
     * @return The middle fragment
     */
    CenterFragment getCenterFragment() {
        return centerFragment;
    }

    /**
     * Show all effect running ,debbuging purpose
     */
    public void showAllEffectsRunning() {
        List<MachineElement> machines = this.getCenterFragment().getMachines();

        Log.d("GROUPES", "Liste des groupes : ");
        for(MachineElement machine : machines) {
            Log.d("GROUPES", machine.getEffect().toString());
        }
    }

    @Override
    public void onMachinesEffectChange(List<MachineElement> machines) {
        //Send effect for each machine
        for(MachineElement me : machines) {
            Effect effect = me.getEffect();
            int errorCount = 0;

            for(Machine machine : me.getMachineList()) {
                SerialComResult result;

                /**
                 *  Ouvrir une asynctask pour récupérer toutes les machines associées à un groupe
                 * @author Marc Saint-Antonin
                 **/

                /** On récupère l'id du groupe souhaité **/
                int idGroupe = machine.getRefGroupe();
                ArrayList<Machine> listMachine = new ArrayList<>();
                new PopulateMachinesTask(this, listMachine).execute(idGroupe);

                Log.i("Live Activity", "J'ai récupéré la liste des machines du groupe : " + idGroupe);
                if (listMachine.isEmpty()) {
                    try {
                        // La tache de remplissage de liste étant en parallèle au thread actuel, la liste d' informations n'a pas le temps de charger
                        // On lance donc un thread pour attendre son remplissage
                        Thread.sleep(100);
                    } catch (InterruptedException e){
                        e.getMessage();
                    }
                }

                for(int i = 0 ; i <listMachine.size(); i++){
                    Log.i("Live Activity", "Liste des machines : " + listMachine.get(i).getAdresse());
                }

                /** Fin de de la récupération **/

                result = this.getCommunicationManager().sendEffectToMachine(effect, idGroupe, listMachine,machine.getIntensity());

                if(SerialComResult.ERROR_SERIAL_NOT_CONNECTED.equals(result) || SerialComResult.ERROR_NOT_INITIALIZED.equals(result)) {
                    errorCount++;
                }
            }

            //Check errors
            if(errorCount == me.getMachineList().size()) {
                this.initCommunicationManager();
            }
        }
    }

    @Override
    public void onBackPressed() {
        new QuitConfirmDialog(this).show();
    }

    @Override
    public void log(String tag, String message) {
        //Inutile, plus de console de log
        //this.centerFragment.writeLog(tag, message);
    }

    //TODO - Faire en sorte que fermer / reouvrir la tablette ne perde pas les modifications
    //TODO - Faire en sorte que changer d'intent (lors d'un message d'init) ne perde pas les modifs
}
