package fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import fr.eseo.dis.tristan.batucadacommander.communication.ISerialSendable;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.EffectEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.graphics.EffectPreview;

/**
 * @author Tristan LE GACQUE
 * Created 09/11/2018
 */
public abstract class Effect implements ISerialSendable {

    private EffectPreview effectPreview;
    private EffectEnum effectType;

    /**
     * Create an Effect object
     * Effect contains the type of effect and the params of the effect
     *
     * @param effectPreview The preview of the effect
     * @param effectType The type of the effect
     */
    public Effect(EffectPreview effectPreview, EffectEnum effectType) {
        this.effectPreview = effectPreview;
        this.effectType = effectType;
    }

    /**
     * Get the effect preview
     * @return The effect preview
     */
    public EffectPreview getEffectPreview() {
        return effectPreview;
    }

    /**
     * Get the effect type
     * @return The effect type
     */
    public EffectEnum getEffectType() {
        return effectType;
    }

    /**
     * Get the drawable
     * @return The drawable
     */
    public Drawable getDrawable() {
        return this.getEffectPreview();
    }

    @NonNull
    @Override
    public String toString() {
        return this.getEffectType().getName();
    }
}
