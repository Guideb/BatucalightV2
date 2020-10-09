package fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.OnEffectInteraction;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.EffectColorType;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.ParamEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.factory.ModuleFactory;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.ModulePosition;

/**
 * @author Tristan LE GACQUE
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnEffectInteraction} interface
 * to handle interaction events.
 * Use the {@link EffectColorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EffectColorFragment extends EffectFragmentContainer<OnEffectInteraction> {

    private static final String EFFET_COULEUR = "effetcouleur";

    private EffectColorType effetCouleurType;


    /**
     * Fragment to set color for colorEffect
     * Contains one color palette
     * And one panel to select the position of color
     */
    public EffectColorFragment() {
        super();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param type type of color effect (simple, double, etc)
     * @return A new instance of fragment
     */
    public static EffectColorFragment newInstance(EffectColorType type) {
        EffectColorFragment fragment = new EffectColorFragment();
        Bundle args = new Bundle();
        args.putString(EFFET_COULEUR, type.toString());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            effetCouleurType = EffectColorType.getType(getArguments().getString(EFFET_COULEUR));
        }
    }

    @Override
    void configureModules() {
        ModuleFactory factory = ModuleFactory.getInstance();

        this.paletteModule = factory.createPaletteModule();

        List<ParamEnum> paramsNeeded = new ArrayList<>();
        switch (this.effetCouleurType) {
            case SIMPLE:
                paramsNeeded.add(ParamEnum.COLOR_1);
                break;
            case DOUBLE:
                paramsNeeded.add(ParamEnum.COLOR_1);
                paramsNeeded.add(ParamEnum.COLOR_2);
                paramsNeeded.add(ParamEnum.TIME);
                break;
        }

        this.paramModule = factory.createParamModule(paramsNeeded.toArray(new ParamEnum[0]));

        factory.replaceFragment(this, this.paletteModule, ModulePosition.TOP);
        factory.replaceFragment(this, this.paramModule, ModulePosition.BOTTOM);
    }


    //////////////////////////////////////////
    // ECOUTE DES MODULES
    //////////////////////////////////////////

    @Override
    public void onChangeColor(BColor color) {
        super.onChangeColor(color);
        this.paramModule.selectNextButton();
    }

}
