package fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects;

import android.support.annotation.NonNull;

import fr.eseo.dis.tristan.batucadacommander.communication.MessageContent;
import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.EffectEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.graphics.ColorEffectDoublePreview;

/**
 * @author Tristan LE GACQUE
 * Created 09/11/2018
 */
public class EffectColorDouble extends Effect {

    private BColor color1;
    private BColor color2;
    private Integer frequence;

    /**
     * Create a color simple effect
     *
     * @param color1 color1
     * @param color2 color2
     * @param frequence The frequency
     */
    public EffectColorDouble(BColor color1, BColor color2, Integer frequence) {
        super(new ColorEffectDoublePreview(color1, color2, frequence), EffectEnum.COLOR_DOUBLE);
        this.color1 = color1;
        this.color2 = color2;
        this.frequence = frequence;
    }


    /**
     * Get color 1
     * @return Color 1
     */
    public BColor getColor1() {
        return color1;
    }

    /**
     * Set color 1
     * @param color1 Color 1
     */
    public void setColor1(BColor color1) {
        this.color1 = color1;
        this.updateColors();
    }

    /**
     * Get color 2
     * @return Color 2
     */
    public BColor getColor2() {
        return color2;
    }

    /**
     * Set color 2
     * @param color2 Color 2
     */
    public void setColor2(BColor color2) {
        this.color2 = color2;
        this.updateColors();
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
        ((ColorEffectDoublePreview)this.getEffectPreview()).setFreq(frequence);
    }

    /**
     * Update colors
     */
    private void updateColors() {
        ((ColorEffectDoublePreview)this.getEffectPreview()).setColors(this.color1, this.color2);
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() + " | " + "color1 = " + this.getColor1() + " &color2 = " + this.getColor2() + " &freq = " + this.getFrequence();
    }

    @Override
    public MessageContent getContent() {
        return new MessageContent(this.getEffectType().getId(), this.getColor1().getRed()
                , this.getColor1().getGreen(), this.getColor1().getBlue()
                , MessageContent.computeSpeedOn255(this.getFrequence()), 16, this.getColor2().getRed(),
                this.getColor2().getGreen(), this.getColor2().getBlue(), 254);
    }


}
