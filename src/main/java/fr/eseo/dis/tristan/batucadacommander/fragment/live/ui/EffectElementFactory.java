package fr.eseo.dis.tristan.batucadacommander.fragment.live.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.EffectEnum;

/**
 * @author Tristan LE GACQUE
 * Created 19/10/2018
 */
public class EffectElementFactory {

    private Context context;

    /**
     * Factory to create effect element and autoInflate
     * @param context The context
     */
    private EffectElementFactory(Context context) {
        this.context = context;
    }

    /**
     * Get new instance of the factory
     * @param context The context
     * @return Instance of the effectElementFactory
     */
    public static EffectElementFactory getNewInstance(Context context) {
        return new EffectElementFactory(context);
    }

    /**
     * Create effect element
     * @param effect The effect to create
     * @return The effect element
     */
    public EffectElement createEffectElement(EffectEnum effect) {
        return new EffectElement(effect, this.inflateButton(effect.getName()));
    }

    /**
     * Inflate the button used to select the effect element
     * @param name The name of the effect
     * @return The button
     */
    private Button inflateButton(String name) {
        Button button = (Button) LayoutInflater
                .from(this.context)
                .inflate(R.layout.effect_button_layout, null);

        button.setText(name);
        return button;
    }

}
