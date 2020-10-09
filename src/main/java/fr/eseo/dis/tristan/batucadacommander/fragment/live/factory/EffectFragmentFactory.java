package fr.eseo.dis.tristan.batucadacommander.fragment.live.factory;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.EffectColorType;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments.EffectArcEnCielFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments.EffectAutoFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments.EffectBlankFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments.EffectColorFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments.EffectFadeFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments.EffectStroboscopeFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.EffectFade;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.ModulePosition;

/**
 * @author Tristan LE GACQUE
 * Created 19/10/2018
 */
public class EffectFragmentFactory extends FragmentFactory{
    private static final int ID = R.id.right_fragment_frame;

    private static final EffectFragmentFactory ourInstance = new EffectFragmentFactory();

    /**
     * Factory to create effect fragment
     */
    private EffectFragmentFactory() {
    }

    /**
     * Get singleton instance
     * @return THE instance
     */
    public static EffectFragmentFactory getInstance() {
        return ourInstance;
    }

    @Override
    int getID(ModulePosition position) {
        return ID;
    }

    ///////////////////////////
    // Définition des effets
    ///////////////////////////

    /**
     * Create color effect (Simple, Double)
     * @param type SIMPLE or DOUBLE
     * @return The fragment
     */
    public EffectColorFragment createEffectColor(EffectColorType type) {
        return EffectColorFragment.newInstance(type);
    }

    /**
     * Create blank effect fragment
     * @return The fragment
     */
    public EffectBlankFragment createEffectBlank() {
        return EffectBlankFragment.newInstance();
    }

    /**
     * Create the stroboscope effect fragment
     * @return The fragment
     */
    public EffectStroboscopeFragment createEffectStroboscope() {
        return EffectStroboscopeFragment.newInstance();
    }

    /**
     * Create the auto effect fragment
     * @return The fragment
     */
    public EffectAutoFragment createEffectAuto() {
        return EffectAutoFragment.newInstance();
    }

    /**Create the fade effect fragment
     *
     * @return The fragment
     */
    public EffectFadeFragment createEffectFade(){
        return  EffectFadeFragment.newInstance();
    }

    /**
     * Create the arc en ciel fragment
     * @return the fragment
     */
    public EffectArcEnCielFragment createEffectArcenCiel(){
        return EffectArcEnCielFragment.newInstance();
    }

}
