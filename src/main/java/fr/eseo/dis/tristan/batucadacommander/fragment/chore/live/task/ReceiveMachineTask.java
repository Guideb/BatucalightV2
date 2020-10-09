package fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.task;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Effet;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Groupe;
import fr.eseo.dis.tristan.batucadacommander.database.entities.GroupeEtEffet;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.CenterFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.Objet.MachinesEtEffect;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.EffectArcenciel;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.EffectAuto;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.EffectColorDouble;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.EffectColorSimple;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.EffectFade;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.EffectNone;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.EffectStroboscope;

/**
 * Tache qui permet de récupérer les machines et leurs effets d'un bloc de chorée
 */
public class ReceiveMachineTask extends AsyncTask<Integer, Void , Integer> {
    private final AppDatabase database;
    private final ArrayList<MachinesEtEffect> machinesEtEffectst;


    public ReceiveMachineTask(CenterFragment fragment, ArrayList<MachinesEtEffect> machinesEtEffectst){
        this.database = AppDatabase.getDatabase(fragment.getContext());
        this.machinesEtEffectst = machinesEtEffectst;
    }

    @Override
    protected Integer doInBackground(Integer... blocId) {
            List<GroupeEtEffet> effets = database.groupeEtEffetDao().getGroupeEffetByBlocId(blocId[0]);


            for (int i = 0; i < effets.size(); i++) {
                Effet effet = database.effetDao().getById(effets.get(i).getRefEffet());
                Groupe groupe = database.groupeDao().getById(effets.get(i).getRefGroupe());

                List<Machine> machines = database.machineDao().getAllMachineInGroup(groupe.getIdGroupe());

            switch (effet.getNomEffet()){
                case "Simple":
                    BColor color = database.colorDao().getById(effet.getRefCouleurUne());
                    EffectColorSimple colorSimple = new EffectColorSimple(color, effet.getFrequence());
                    machinesEtEffectst.add(new MachinesEtEffect(machines,colorSimple));
                    break;
                case "Double":
                    BColor color1 = database.colorDao().getById(effet.getRefCouleurUne());
                    BColor color2 = database.colorDao().getById(effet.getRefCouleurDeux());

                        EffectColorDouble colorDouble = new EffectColorDouble(color1, color2, effet.getFrequence());
                        machinesEtEffectst.add(new MachinesEtEffect(machines, colorDouble));
                        break;
                    case "Stroboscope":
                        BColor colorStrob = database.colorDao().getById(effet.getRefCouleurUne());
                        EffectStroboscope colorStrobEffect = new EffectStroboscope(colorStrob, effet.getFrequence());
                        machinesEtEffectst.add(new MachinesEtEffect(machines, colorStrobEffect));
                        break;

                    case "Fade":
                        BColor colorFade = database.colorDao().getById(effet.getRefCouleurUne());
                        EffectFade colorFadeEffect = new EffectFade(colorFade, effet.getDuree(),effet.getFrequence());
                        machinesEtEffectst.add(new MachinesEtEffect(machines, colorFadeEffect));
                        break;
                    case "Arc en ciel":
                        EffectArcenciel colorAutoEffect = new EffectArcenciel(effet.getFrequence());
                        machinesEtEffectst.add(new MachinesEtEffect(machines, colorAutoEffect));
                        break;
                case "Aucun":
                    EffectNone effectNone = new EffectNone();
                    machinesEtEffectst.add(new MachinesEtEffect(machines, effectNone));
                    break;
                }


        }



        return null;
    }
}
