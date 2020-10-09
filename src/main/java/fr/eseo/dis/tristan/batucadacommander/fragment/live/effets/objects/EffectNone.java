package fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects;

import fr.eseo.dis.tristan.batucadacommander.communication.MessageContent;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.EffectEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.graphics.NoneEffectPreview;

/**
 * @author Tristan LE GACQUE
 * Created 09/11/2018
 */
public class EffectNone extends Effect {

    /**
     * Create a none effect
     */
    public EffectNone() {
        super(new NoneEffectPreview(), EffectEnum.NONE);
    }

    @Override
    public MessageContent getContent() {
        return new MessageContent(this.getEffectType().getId());
    }
}
