package fr.eseo.dis.tristan.batucadacommander.fragment.chore.live;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.fragment.MainFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.Objet.MachinesEtEffect;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.task.ReceiveAllMachineTask;

public class RightFragment extends MainFragment<RightFragment.OnRightFragmentInteractionListener> {

    private Button button;
    /**
     * Construtor of the left chore config fragment
     */
    public RightFragment() {
        super();    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment LeftFragment.
     */
    public static RightFragment newInstance() {
        return new RightFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_chore_live_right,container,false);

        this.button = mainView.findViewById(R.id.buttonStopLive);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"ON ARRETE TOUT",Toast.LENGTH_LONG).show();
                onBoutonPress();
            }
        });
        return mainView;
    }

    public void onBoutonPress(){
        ArrayList<MachinesEtEffect> machinesEtEffects = new ArrayList<>();
        new ReceiveAllMachineTask(this,machinesEtEffects).execute();
        if (machinesEtEffects.isEmpty()) {
            try {
                // La tache de remplissage de liste étant en parallèle au thread actuel, la liste machinesEtEffects n'a pas le temps de chargé
                //On lance donc un thread pour attendre son remplissage
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.getMessage();
            }
        }
        //Envoi de message pour tout les machines
        this.getListener().onBtnPressed(machinesEtEffects);


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnRightFragmentInteractionListener {

        /**
         * Envoie le message pour stopper tout les machines et deselectionne le bouton
         * @param machinesEtEffects
         */
        void onBtnPressed(ArrayList<MachinesEtEffect> machinesEtEffects);


    }
}
