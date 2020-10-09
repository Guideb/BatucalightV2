package fr.eseo.dis.tristan.batucadacommander.fragment.live.factory;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.ModulePosition;

/**
 * @author Tristan LE GACQUE
 * Created 19/10/2018
 */
public abstract class FragmentFactory {

    /**
     * Get the ID of the main fragment
     * @param position Position (TOP / BOTTOM)
     * @return THE ID
     */
    abstract int getID(ModulePosition position);

    /**
     * Replace one fragment by another one
     * @param main First fragment
     * @param fragment Fragment that rempalce the fragment
     * @param position The position (TOP or BOT) of the new fragment
     */
    public void replaceFragment(Fragment main, Fragment fragment, ModulePosition position) {
        FragmentTransaction ft = main.getChildFragmentManager().beginTransaction();

        ft.replace(this.getID(position), fragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Replace one fragment by another one in DEFAULT position
     * @param main First fragment
     * @param fragment Fragment that remplace the first one
     */
    public void replaceFragment(Fragment main, Fragment fragment) {
        this.replaceFragment(main, fragment, ModulePosition.DEFAUT);
    }

}
