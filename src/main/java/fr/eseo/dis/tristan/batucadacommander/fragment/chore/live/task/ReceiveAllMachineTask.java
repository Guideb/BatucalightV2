package fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.task;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Groupe;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.Objet.MachinesEtEffect;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.RightFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.EffectNone;

/**
 * Tache qui permet de récupérer tout les machines de tout les groupes
 */
public class ReceiveAllMachineTask extends AsyncTask<Integer, Void , Void> {
    private final AppDatabase database;
    private final ArrayList<MachinesEtEffect> machinesEtEffects;

    public ReceiveAllMachineTask(RightFragment fragment, ArrayList<MachinesEtEffect> machinesEtEffects) {
        this.database = AppDatabase.getDatabase(fragment.getContext());
        this.machinesEtEffects = machinesEtEffects;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        List<Groupe> allgroupes = database.groupeDao().getAllGroupe();
        for (int i = 0; i < allgroupes.size(); i++) {
            List<Machine> machines = database.machineDao().getAllMachineInGroup(allgroupes.get(i).getIdGroupe());
            EffectNone effectNone = new EffectNone();
            machinesEtEffects.add(new MachinesEtEffect(machines,effectNone));
        }
        return null;
    }
}











