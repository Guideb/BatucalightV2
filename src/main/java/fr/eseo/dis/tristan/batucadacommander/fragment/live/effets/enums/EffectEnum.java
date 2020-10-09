package fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums;

/**
 *
 * @author Tristan LE GACQUE
 * Created 19/10/2018
 */
public enum EffectEnum {

    COLOR_SIMPLE(16, "Couleur simple"),
    COLOR_DOUBLE(11, "Couleur double"),
    STROBOSCOPE(3, "Stroboscope"),
    FADE(14,"Fade"),
    ARCENCIEL(15, "Arc-en-ciel"),


    NONE(99, "Aucun effet"),
    AUTO(0, "MODE Automatique");






    private int id;
    private String name;

    /**
     * Effect
     * @param id the UNIQUE ID of the effect (correspond to ID in physical card. DO NOT CHANGE IT !)
     * @param name The name to be displayed on the screen
     */
    EffectEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Get the name of the effect
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the UUID
     * @return The uuid
     */
    public int getId() {
        return id;
    }

}
