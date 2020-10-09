package fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums;

/**
 * @author Tristan LE GACQUE
 * Created 18/10/2018
 */
public enum EffectColorType {

    SIMPLE,
    DOUBLE;

    /**
     * Returns the type of color effect for the given string
     * @param str Effect as a string
     * @return The corresponding color effect, 'simple' as default
     */
    public static EffectColorType getType(String str) {
        for(EffectColorType e : values()) {
            if(e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return SIMPLE;
    }
}
