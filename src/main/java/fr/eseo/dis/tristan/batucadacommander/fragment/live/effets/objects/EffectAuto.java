package fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects;

import android.support.annotation.NonNull;

import fr.eseo.dis.tristan.batucadacommander.communication.MessageContent;
import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.EffectEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.graphics.AutoEffectPreview;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.graphics.ColorEffectDoublePreview;

/**
 * @author Tristan LE GACQUE
 * Created 09/11/2018
 */
public class EffectAuto extends Effect {

    private BColor color;
    private int frequence;

    /**
     * Create an automatic color effect (based on hardware)
     *
     * @param color The color to use
     */
    public EffectAuto(BColor color, int frequence) {
        super(new AutoEffectPreview(color), EffectEnum.AUTO);
        this.color = color;
        this.frequence = frequence;
    }

    /**
     * Get the frequence
     * @return The frequence
     */
    public Integer getFrequence() {
        return frequence;
    }

    /**
     * Set the frequence
     * @param frequence the frequence
     */
    public void setFrequence(Integer frequence) {
        this.frequence = frequence;
    }

    /**
     * Get the color
     * @return the color
     */
    public BColor getColor() {
        return color;
    }

    /**
     * Set the color of the effect
     * @param color The color
     */
    public void setColor(BColor color) {
        this.color = color;
        ((AutoEffectPreview)this.getEffectPreview()).setColor(color.getColor());
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() + " | " + "color = " + this.getColor();
    }

    @Override
    public MessageContent getContent() {
        return new MessageContent(this.getEffectType().getId(), this.getColor().getRed()
                , this.getColor().getGreen(), this.getColor().getBlue()
                , MessageContent.computeSpeedOn255(this.getFrequence()), 0, this.getColor().getRed(),
                this.getColor().getGreen(), this.getColor().getBlue(), 254);
    }
}
