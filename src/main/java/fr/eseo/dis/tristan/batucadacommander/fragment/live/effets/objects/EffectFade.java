package fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects;


import android.support.annotation.NonNull;
import android.util.Log;

import fr.eseo.dis.tristan.batucadacommander.communication.MessageContent;
import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.EffectEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.graphics.FadeEffectPreview;

/**
 * Test pour l'effet fade
 * @Auteur Dony
 * Créé le 4/10/2019
 */
public class EffectFade extends Effect{

    private BColor color;
    private Integer intensity;
    private int frequence;
    /**
     * Create an fade effect

     * @param color The color to use
     * @param intensity The intensity to display
     */
    public EffectFade(BColor color, Integer intensity, int frequence) {
        super(new FadeEffectPreview(color, intensity), EffectEnum.FADE);
        this.color = color;
        this.intensity = intensity;
        this.frequence = frequence;
    }

    /**
     * get The color
     * @return the color
     */
    public BColor getColor(){
        return color;
    }

    /**
     * set the color of the effect
     * @param color the color
     */
    public void setColor(BColor color){
        this.color = color;
        ((FadeEffectPreview) this.getEffectPreview()).setColor(color.getColor());
    }

    /**
     * get the intensity
     * @return
     */
    public int getIntensity(){
        return intensity;
    }

    /**
     * set the intensity
     * @param intensity the intensity
     */
    public void setIntensity(int intensity){
        this.intensity = intensity;
        ((FadeEffectPreview) this.getEffectPreview()).setIntensity(intensity);
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


    @NonNull
    @Override
    public String toString() {
        return super.toString() + " | " + "color = " + this.getColor() + " &inten = " + this.getIntensity();
    }

    @Override
    public MessageContent getContent() {
        return new MessageContent(this.getEffectType().getId(), this.getColor().getRed()
                , this.getColor().getGreen(), this.getColor().getBlue()
                , MessageContent.computeSpeedOn255(this.getFrequence()), 0, this.getColor().getRed(),
                this.getColor().getGreen(), this.getColor().getBlue(), 254);
    }
}
