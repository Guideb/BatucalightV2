package fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments;

import android.support.v4.app.Fragment;

/**
 * @author Tristan LE GACQUE
 * A simple {@link Fragment} subclass.
 * Use the {@link EffectBlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EffectBlankFragment extends EffectFragment<Void> {

    /**
     * Blank effect
     */
    public EffectBlankFragment() {
        super();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment
     */
    public static EffectBlankFragment newInstance() {
        return new EffectBlankFragment();
    }

    @Override
    void configureModules() {
        //Nothing to configure
    }

}
