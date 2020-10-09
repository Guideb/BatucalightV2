package fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.task;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.GroupeEtEffet;
import fr.eseo.dis.tristan.batucadacommander.database.entities.GroupeEtEffetNom;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.RightFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.ui.adapter.RecyclerGroupeEtEffetAdapter;

public class PopulateGroupeEtEffetTask extends AsyncTask<Integer, Integer , Integer> {
    private final RecyclerGroupeEtEffetAdapter adapter;
    private final AppDatabase database;

    /**
     * Tache pour remplir l'adapter des Groupes et d'effet
     * @param adapter L'adapter
     * @param fragment Le fragment
     */
    public PopulateGroupeEtEffetTask(RecyclerGroupeEtEffetAdapter adapter, RightFragment fragment){
        this.adapter = adapter;
        this.database = AppDatabase.getDatabase(fragment.getContext());
    }

    @Override
    protected Integer doInBackground(Integer... blocId) {
        List<GroupeEtEffet> groupeEtEffets;
        List<GroupeEtEffetNom> groupeEtEffetNoms = new ArrayList<>();

        if (!(blocId.length == 0)){
            groupeEtEffets = database.groupeEtEffetDao().getGroupeEffetByBlocId(blocId[0]);
            for (int i =0; i < groupeEtEffets.size(); i++){
                groupeEtEffetNoms.add(new GroupeEtEffetNom(
                        database.blocDao().getNameById(groupeEtEffets.get(i).getRefBloc()),
                        database.effetDao().getNameById(groupeEtEffets.get(i).getRefEffet()),
                        database.groupeDao().getNameById(groupeEtEffets.get(i).getRefGroupe())));
            }

        } else {
            groupeEtEffets = null;
        }
        this.adapter.setGroupeEtEffet(groupeEtEffets);
        this.adapter.setGroupeEtEffetName(groupeEtEffetNoms);
        return null;
    }

    @Override
    protected void onPostExecute(Integer sId){
        this.adapter.notifyDataSetChanged();
    }
}
