package fr.eseo.dis.tristan.batucadacommander.fragment.live.factory;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.ParamEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.modules.PaletteModuleFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.modules.ParamsModuleFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.ModulePosition;

/**
 * @author Tristan LE GACQUE
 * Created 19/10/2018
 */
public class ModuleFactory extends FragmentFactory{
    private static final ModuleFactory ourInstance = new ModuleFactory();

    /**
     * Builder of modules
     */
    private ModuleFactory() {
    }

    /**
     * Get instance of the factory
     * @return The factory
     */
    public static ModuleFactory getInstance() {
        return ourInstance;
    }

    /**
     * Find the Id from ressource of either the top or bottom frame, depending on desired position
     *
     * @param modulePosition The position (TOP or BOTTOM)
     * @return The id of the element at the position in the frame
     */
    @Override
    int getID(ModulePosition modulePosition) {
        switch(modulePosition){
            case TOP:
                return R.id.effet_fragment_top_frame;
            case BOTTOM:
                return R.id.effet_fragment_bottom_frame;
            default:
                return this.getID(ModulePosition.TOP);
        }
    }


    ///////////////////////////
    // Définition des modules
    ///////////////////////////

    /**
     * Create fragment palette of colors
     * @return The fragment
     */
    public PaletteModuleFragment createPaletteModule() {
        return PaletteModuleFragment.newInstance();
    }

    /**
     * Create Param fragment
     * @param params Params to handle
     * @return The fragment
     */
    public ParamsModuleFragment createParamModule(ParamEnum... params) {
        return ParamsModuleFragment.newInstance(
                params.length == 0 ? new ParamEnum[]{ParamEnum.NONE} : params);
    }

}
