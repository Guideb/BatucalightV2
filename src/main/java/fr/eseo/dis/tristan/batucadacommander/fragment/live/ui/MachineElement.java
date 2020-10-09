package fr.eseo.dis.tristan.batucadacommander.fragment.live.ui;

import android.util.Log;
import android.widget.Button;

import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.Effect;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.EffectNone;

/**
 * @author Tristan LE GACQUE
 * Created 12/10/2018
 */
public class MachineElement {

    private Button button;
    private boolean selected;
    private Effect effect;
    private List<Machine> machineList;

    /**
     * UI Element that store the effect for a machine
     * @param button The button to enable to select the machine
     * @param machines The machines
     */
    public MachineElement(Button button, List<Machine> machines) {
        this.button = button;
        this.selected = false;
        this.effect = new EffectNone();
        this.machineList = machines;

        this.updateColor();
    }

    /**
     * Update the displayed color of the ui machine object
     */
    private void updateColor() {
        if(this.getEffect() != null) {
            Log.d("DEBUG", "UPDATE COLOR => " +
                    this.getEffect().getEffectType().getName() + "(" + this.isSelected() + ")");
            this.button.setBackground(effect.getDrawable());
        }
    }

    /**
     * Is the machine selected ?
     * @return Ture if selected, false otherwise
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Set the status of selection of the machine
     * @param selected true if selected, false otherwise
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
        this.getEffect().getEffectPreview().setSelected(selected);
        this.getEffect().getEffectPreview().invalidateSelf();
        this.updateColor();
    }

    /***
     * Get the effect that run on machine
     * @return The effect
     */
    public Effect getEffect() {
        return effect;
    }

    /**
     * Set the effect for machine
     * And change the preview
     * @param effect The effect
     */
    public void setEffect(Effect effect) {
        this.effect = effect;

        //Update selected state for Preview items
        this.setSelected(this.isSelected());
    }

    /**
     * Get the machines in the groupe
     * @return The machines
     */
    public List<Machine> getMachineList() {
        return machineList;
    }

    /**
     * Toggle selection
     */
    public void toggleSelected() {
        this.setSelected(!this.isSelected());
    }
}
