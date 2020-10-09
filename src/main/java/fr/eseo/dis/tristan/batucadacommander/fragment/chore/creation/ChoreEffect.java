package fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation;

import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.Effect;

public class ChoreEffect {

    private Effect effect;
    private int durationMs;
    private List<Machine> machineList;

    /**
     * Create a chore effect.
     * @param effect The effect to show
     * @param durationMs How many time before next, in milliseconds
     * @param machineList List of machines
     */
    public ChoreEffect(Effect effect, int durationMs, List<Machine> machineList) {
        this.effect = effect;
        this.durationMs = durationMs;
        this.machineList = machineList;
    }

    /**
     * Get the effect
     * @return The effect
     */
    public Effect getEffect() {
        return effect;
    }

    /**
     * Get the duration of the effect
     * @return Duration in ms
     */
    public int getDurationMs() {
        return durationMs;
    }

    /**
     * Get the machines
     * @return The machines
     */
    public List<Machine> getMachineList() {
        return machineList;
    }
}
