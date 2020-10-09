package fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.OnEffectInteraction;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.ParamEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.factory.ModuleFactory;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.ModulePosition;

public class EffectArcEnCielFragment extends EffectFragmentContainer<OnEffectInteraction> {

    public EffectArcEnCielFragment() {
        super();
    }

    public static EffectArcEnCielFragment newInstance() {
        return new EffectArcEnCielFragment();
    }

    @Override
    void configureModules() {
        ModuleFactory factory = ModuleFactory.getInstance();

        List<ParamEnum> params = new ArrayList<>();
        params.add(ParamEnum.TIME);

        this.paramModule = factory.createParamModule(params.toArray(new ParamEnum[0]));
        factory.replaceFragment(this, this.paramModule, ModulePosition.TOP);
    }
}
