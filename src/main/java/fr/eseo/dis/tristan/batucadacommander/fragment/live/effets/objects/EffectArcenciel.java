package fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects;

import fr.eseo.dis.tristan.batucadacommander.communication.MessageContent;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.EffectEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.graphics.ArcencielEffectPreview;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.graphics.EffectPreview;

public class EffectArcenciel extends Effect {

    private Integer frequence;

    /**
     * Create an Effect object
     * @param frequence the frequence
     */
    public EffectArcenciel(Integer frequence) {
        super(new ArcencielEffectPreview(frequence), EffectEnum.ARCENCIEL);
        this.frequence = frequence;
    }

    public Integer getFrequence(){
        return this.frequence;
    }

    @Override
    public MessageContent getContent() {
        return new MessageContent(this.getEffectType().getId(), 0, 0, 0,
                MessageContent.computeSpeedOn255(this.getFrequence()), 0, 0, 0, 0, 0);
    }
}
