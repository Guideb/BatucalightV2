package fr.eseo.dis.tristan.batucadacommander.fragment.live.effets;

import java.util.Map;

import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.ParamEnum;

/**
 * @author Tristan LE GACQUE
 * Created 09/11/2018
 */
public interface OnEffectInteraction {
    /**
     * Triggered when effect params are changed.
     * @param params Complete params
     */
    void onEffectParamsChange(Map<ParamEnum, Object> params);
}
