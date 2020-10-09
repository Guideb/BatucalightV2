package fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums;

import android.support.annotation.NonNull;

import static fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.ParamEnum.Type.COLOR;
import static fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.ParamEnum.Type.INTEGER;
import static fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.ParamEnum.Type.NOTYPE;

/**
 * @author Tristan LE GACQUE
 * Created 09/11/2018
 */
public enum ParamEnum {

    COLOR_1("COLOR_1", COLOR),
    COLOR_2("COLOR_2", COLOR),
    TIME("TIME", INTEGER),
    INTENSITY("INTENSITY",INTEGER),

    NONE("NONE", NOTYPE);

    private String name;
    private Type type;
    /**
     * ParamEnum
     * @param name Name of param
     * @param type The type
     */
    ParamEnum(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Find param from given name
     * @param name Name
     * @return param | None
     */
    public static ParamEnum findParam(String name) {
        for(ParamEnum param : values()) {
            if(param.name.equalsIgnoreCase(name)) {
                return param;
            }
        }

        return NONE;
    }

    /**
     * Return the type
     * @return The type
     */
    public Type getType() {
        return type;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }

    public enum Type{COLOR, INTEGER, NOTYPE}

}
