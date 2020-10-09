package fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.OnEffectInteraction;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.ParamEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.factory.ModuleFactory;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.ModulePosition;

/**
 * @author Tristan LE GACQUE
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnEffectInteraction} interface
 * to handle interaction events.
 * Use the {@link EffectStroboscopeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EffectStroboscopeFragment extends EffectFragmentContainer<OnEffectInteraction> {

    /**
     * Fragment to set color for colorEffect
     * Contains one color palette
     * And one panel to select the position of color
     */
    public EffectStroboscopeFragment() {
        super();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment
     */
    public static EffectStroboscopeFragment newInstance() {
        return new EffectStroboscopeFragment();
    }

    @Override
    void configureModules() {
        ModuleFactory factory = ModuleFactory.getInstance();

        this.paletteModule = factory.createPaletteModule();

        List<ParamEnum> paramsNeeded = new ArrayList<>();
        paramsNeeded.add(ParamEnum.COLOR_1);
        paramsNeeded.add(ParamEnum.TIME);

        this.paramModule = factory.createParamModule(paramsNeeded.toArray(new ParamEnum[0]));

        factory.replaceFragment(this, this.paletteModule, ModulePosition.TOP);
        factory.replaceFragment(this, this.paramModule, ModulePosition.BOTTOM);
    }

}
