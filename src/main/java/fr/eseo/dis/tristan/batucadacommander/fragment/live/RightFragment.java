package fr.eseo.dis.tristan.batucadacommander.fragment.live;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.fragment.MainFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.OnEffectInteraction;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.EffectColorType;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.EffectEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.ParamEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments.EffectArcEnCielFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments.EffectAutoFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments.EffectBlankFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments.EffectColorFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments.EffectFadeFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments.EffectFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments.EffectStroboscopeFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.EffectFade;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.factory.EffectFragmentFactory;

/**
 * @author Tristan LE GACQUE
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnRightFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RightFragment extends MainFragment<RightFragment.OnRightFragmentInteractionListener> implements OnEffectInteraction {

    private EffectColorFragment eColorSimpleFragment;
    private EffectColorFragment eColorDoubleFragment;
    private EffectStroboscopeFragment eStroboscopeFragment;
    private EffectBlankFragment eBlankFragment;
    private EffectAutoFragment eAutoFragment;
    private EffectArcEnCielFragment eArcencielFragment;

    /**
     * Test
     */
    private EffectFadeFragment eFadeFragment;



    /**
     * The right fragment
     */
    public RightFragment() {
        super();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment
     *
     * @return A new instance of fragment RightFragment.
     */
    public static RightFragment newInstance() {
        return new RightFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_right, container, false);

        // Declare ALL the effects fragments
        EffectFragmentFactory factory = EffectFragmentFactory.getInstance();
        this.eBlankFragment = factory.createEffectBlank();
        this.eColorSimpleFragment   = factory.createEffectColor(EffectColorType.SIMPLE);
        this.eColorDoubleFragment   = factory.createEffectColor(EffectColorType.DOUBLE);
        this.eStroboscopeFragment   = factory.createEffectStroboscope();
        this.eAutoFragment          = factory.createEffectAuto();
        this.eFadeFragment          = factory.createEffectFade();
        this.eArcencielFragment     = factory.createEffectArcenCiel();

        //INFO - Add all fragment relatives to effect HERE

        return mainView;
    }

    /**
     * Change the effect color fragment
     * @param effect The effect
     */
    public void changeEffectFragment(EffectEnum effect) {
        EffectFragment currentFragment;
        switch(effect) {
            case COLOR_SIMPLE:
                currentFragment = this.eColorSimpleFragment;
                break;
            case COLOR_DOUBLE:
                currentFragment = this.eColorDoubleFragment;
                break;
            case STROBOSCOPE:
                currentFragment = this.eStroboscopeFragment;
                break;
            case AUTO:
                currentFragment = this.eAutoFragment;
                break;
            case FADE:
                currentFragment = this.eFadeFragment;
                break;
            case ARCENCIEL:
                currentFragment = this.eArcencielFragment;
                break;
            case NONE:
                currentFragment = this.eBlankFragment;
                break;

            default:
                currentFragment = this.eBlankFragment;
                break;
                //INFO - Add all other effects HERE
        }

        EffectFragmentFactory.getInstance().replaceFragment(this, currentFragment);
    }


    //////////////////////////
    // LISTENERS
    ////////////////////////

    @Override
    public void onEffectParamsChange(Map<ParamEnum, Object> params) {
        //Tells listeners that params changed
        this.getListener().onEffectParamsChange(params);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnRightFragmentInteractionListener extends OnEffectInteraction{

    }
}
