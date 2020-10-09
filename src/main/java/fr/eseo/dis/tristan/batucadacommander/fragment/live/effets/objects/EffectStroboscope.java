package fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects;

import android.support.annotation.NonNull;

import fr.eseo.dis.tristan.batucadacommander.communication.MessageContent;
import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.EffectEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.graphics.StroboscopeEffectPreview;

/**
 * @author Tristan LE GACQUE
 * Created 09/11/2018
 */
public class EffectStroboscope extends Effect {

    private BColor color;
    private Integer frequence;

    /**
     * Create a color simple effect
     *
     * @param color The color to use
     * @param frequence The frequence
     */
    public EffectStroboscope(BColor color, Integer frequence) {
        super(new StroboscopeEffectPreview(color, frequence), EffectEnum.STROBOSCOPE);
        this.color = color;
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
        ((StroboscopeEffectPreview)this.getEffectPreview()).setColor(color.getColor());

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
        ((StroboscopeEffectPreview)this.getEffectPreview()).setFreq(frequence);
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() + " | " + "color = " + this.getColor() + " &freq = " + this.getFrequence();
    }

    @Override
    public MessageContent getContent() {
        return new MessageContent(this.getEffectType().getId(),
                this.getColor().getRed(), this.getColor().getGreen(), this.getColor().getBlue(),
                MessageContent.computeSpeedOn255(this.getFrequence()), 1);
    }
}
