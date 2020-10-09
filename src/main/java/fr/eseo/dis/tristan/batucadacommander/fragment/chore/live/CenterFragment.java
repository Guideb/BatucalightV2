package fr.eseo.dis.tristan.batucadacommander.fragment.chore.live;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Bloc;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Chore;
import fr.eseo.dis.tristan.batucadacommander.fragment.MainFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.Objet.MachinesEtEffect;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.task.PopulateBlocTask;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.task.ReceiveMachineTask;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.ui.adapter.RecyclerBlocAdapter;

public class CenterFragment extends MainFragment<CenterFragment.OnCenterFragmentInteractionListener> implements RecyclerBlocAdapter.ItemClickListener {

    private RecyclerBlocAdapter adapter;
    private Chore selectedChore;
    private Bloc selectedBloc;
    /**
     * Construtor of the left chore config fragment
     */
    public CenterFragment() {
        super();    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment LeftFragment.
     */
    public static CenterFragment newInstance() {
        return new CenterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_chore_live_center,container,false);

        this.selectedChore = null;

        //Recycler view
        RecyclerView recyclerBlocView = mainView.findViewById(R.id.recycler_bloc_live);
        this.adapter = new RecyclerBlocAdapter(this.getContext(), this);
        recyclerBlocView.setAdapter(adapter);
        recyclerBlocView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return mainView;
    }

    public void setSelectedChore(Chore chore){
        this.selectedChore = chore;
    }

    ////////////////////
    // LISTENERS
    ////////////////////

    /**
     * Populate bloc list
     */
    public void populateWithBlocs(Chore chore) {
        int choreId = chore == null ? -1 : chore.getIdChore();
        new PopulateBlocTask(this.adapter,this).execute(choreId);

        //Update the bloc
        this.selectedChore = chore;
    }

    @Override
    public void onBlocSelected(Bloc bloc) {
        this.selectedBloc = bloc;

        ArrayList<MachinesEtEffect> machinesEtEffects = new ArrayList<>();
        //Envoi de message pour chaque machine concernée
        new ReceiveMachineTask(this,machinesEtEffects).execute(bloc.getIdBloc());
        if (machinesEtEffects.isEmpty()) {
            try {
                // La tache de remplissage de liste étant en parallèle au thread actuel, la liste machinesEtEffects n'a pas le temps de chargé
                //On lance donc un thread pour attendre son remplissage
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.getMessage();
            }
        }
       this.getListener().onBlocChange(bloc,selectedChore,machinesEtEffects);



    }

    @Override
    public void onBlocLongClick(Bloc bloc) {

    }

    /**
    * This interface must be implemented by activities that contain this
    * fragment to allow an interaction in this fragment to be communicated
    * to the activity and potentially other fragments contained in that
    * activity.
    */
    public interface OnCenterFragmentInteractionListener {

        /**
         * Appeler quand un bloc change
         */
        void onBlocChange(Bloc bloc, Chore selectedChore,ArrayList<MachinesEtEffect> machinesEtEffects);
    }
}
