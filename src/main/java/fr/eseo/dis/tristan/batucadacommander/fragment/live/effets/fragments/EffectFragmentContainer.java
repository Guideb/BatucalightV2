package fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.EnumMap;
import java.util.Map;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.OnEffectInteraction;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.ParamEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.modules.PaletteModuleFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.modules.ParamsModuleFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.ParamElement;

/**
 * @author Tristan LE GACQUE
 * Created 19/10/2018
 * INFO : T element in 'T extends OnEffectInteraction' is used to verify Listener, do not remove it
 */
public abstract class EffectFragmentContainer<T extends OnEffectInteraction> extends EffectFragment<OnEffectInteraction> implements PaletteModuleFragment.OnPaletteInteraction, ParamsModuleFragment.OnParamsInteraction {

    PaletteModuleFragment paletteModule;
    ParamsModuleFragment paramModule;

    /**
     * Fragment for effect
     * To be places on RightFragment
     */
    EffectFragmentContainer() {
        super();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        this.mainView = inflater.inflate(R.layout.effet_fragment, container, false);

        this.configureModules();

        return this.mainView;
    }

    /**
     * Configure the modules of the effect (palette, params...)
     */
    abstract void configureModules();


    ////////////////
    // LISTENER
    ///////////////

    @Override
    public void onChangeColor(BColor color) {
        this.paramModule.setColorOnSelected(color);
    }

    @Override
    public void onParamUpdated(ParamElement[] elements) {
        //Triggered when color is picked
        Map<ParamEnum, Object> params = new EnumMap<>(ParamEnum.class);

        for(ParamElement element : elements) {

            Object value = element.getValue();
            if(ParamEnum.Type.COLOR.equals(element.getParam().getType()) && value == null) {
                value = BColor.B_WHITE;
            }

            params.put(element.getParam(), value);
        }

        this.getListener().onEffectParamsChange(params);
    }



}
