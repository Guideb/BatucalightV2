package fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.fragment.ObservableFragment;

/**
 * @author Tristan LE GACQUE
 * Created 19/10/2018
 */
public abstract class EffectFragment<T> extends ObservableFragment<T> {

    View mainView;

    /**
     * Fragment for effect
     * To be places on RightFragment
     */
    EffectFragment() {
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
     * Get the main view, that is the layout container
     *
     * @return The main view
     */
    public View getMainView() {
        return mainView;
    }

    /**
     * Configure the modules of the effect (palette, params...)
     */
    abstract void configureModules();


}
