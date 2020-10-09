package fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments;

import android.support.v4.app.Fragment;

import fr.eseo.dis.tristan.batucadacommander.R;
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
 * Use the {@link EffectAutoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EffectAutoFragment extends EffectFragmentContainer<OnEffectInteraction> {

    /**
     * Fragment to set color for colorEffect
     * Contains one color palette
     * And one panel to select the position of color
     */
    public EffectAutoFragment() {
        super();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment
     */
    public static EffectAutoFragment newInstance() {
        return new EffectAutoFragment();
    }

    @Override
    void configureModules() {
        ModuleFactory factory = ModuleFactory.getInstance();

        this.paletteModule = factory.createPaletteModule();
        this.paramModule = factory.createParamModule(ParamEnum.COLOR_1);

        factory.replaceFragment(this, this.paletteModule, ModulePosition.TOP);
        factory.replaceFragment(this, this.paramModule, ModulePosition.BOTTOM);

        this.paramModule.showText(getResources().getString(R.string.text_mode_auto));
    }

}
