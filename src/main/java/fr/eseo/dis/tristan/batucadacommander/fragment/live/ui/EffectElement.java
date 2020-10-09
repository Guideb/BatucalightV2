package fr.eseo.dis.tristan.batucadacommander.fragment.live.ui;

import android.graphics.drawable.GradientDrawable;
import android.widget.Button;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.EffectEnum;

/**
 * @author Tristan LE GACQUE
 * Created 12/10/2018
 */
public class EffectElement {

    private EffectEnum type;
    private Button button;
    private boolean selected;

    /**
     * Define the button that select effect
     * Example : COULEUR SIMPLE, COULEUR DOUBLE, AUCUN EFFET
     *
     * @param type The effect to select when you press that button
     * @param button The button object
     */
    EffectElement(EffectEnum type, Button button) {
        this.button = button;
        this.selected = false;
        this.type = type;

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        this.button.setBackground(drawable);

        this.updatePreview();
    }

    /**
     * Update the preview
     * Add color on border if selected
     * Add graphics depending on the effect
     */
    public void updatePreview() {
        GradientDrawable drawable = (GradientDrawable) this.getButton().getBackground();

        if(this.isSelected()) {
            drawable.setColor(button.getResources().getColor(R.color.colorPrimary));
        } else {
            drawable.setColor(button.getResources().getColor(R.color.colorGray));
        }
    }

    /**
     * Get the button that reporesent the effect
     * @return The button
     */
    public Button getButton() {
        return button;
    }

    /**
     * Check if element is selected
     * @return True if selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Set selected status
     * @param selected True is selected, false otherwise
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Get type of effect
     * @return TYPE
     */
    public EffectEnum getType() {
        return type;
    }

}
