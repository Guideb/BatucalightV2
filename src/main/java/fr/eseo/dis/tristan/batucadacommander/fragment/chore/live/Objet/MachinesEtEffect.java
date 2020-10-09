package fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.Objet;

import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.Effect;

/**
 * Cet objet sert à pouvoir envoyer une liste de machines et l'effet associé (effet sous forme d'effect)
 */
public class MachinesEtEffect {

    private List<Machine> machines;
    private Effect effect;

    /**
     * Constructeur
     * @param machines La liste des machines qui vont recevoir le meme message
     * @param effect L'effet associé à tout le groupe
     */
    public MachinesEtEffect(List<Machine> machines,Effect effect){
        this.machines = machines;
        this.effect = effect;
    }

    @Override
    public String toString() {
        return "MachinesEtEffect{" +
                "machines=" + machines +
                ", effect=" + effect +
                '}';
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }
}
