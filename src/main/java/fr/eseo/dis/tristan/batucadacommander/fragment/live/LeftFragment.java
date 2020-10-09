package fr.eseo.dis.tristan.batucadacommander.fragment.live;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.fragment.MainFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.EffectEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.EffectElement;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.EffectElementFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LeftFragment.OnLeftFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LeftFragment#newInstance} factory method to
 * create an instance of this fragment.
 * @author Tristan LE GACQUE
 */
public class LeftFragment extends MainFragment<LeftFragment.OnLeftFragmentInteractionListener> implements View.OnClickListener{

    private Map<Integer, EffectElement> effectsMap;


    private LinearLayout mainView;

    /**
     * The left LIVE fragment
     * Used to select the effect
     */
    public LeftFragment() {
        super();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LeftFragment.
     */
    public static LeftFragment newInstance() {
        return new LeftFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.effectsMap = new HashMap<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        this.mainView = (LinearLayout) inflater.inflate(R.layout.fragment_left, container, false);

        // Define the effects
        defineEffects();

        return this.mainView;
    }

    /**
     * Define the effets and adds them to the main view
     */
    private void defineEffects() {
        EffectElementFactory factory = EffectElementFactory.getNewInstance(this.getContext());
        for(EffectEnum effect : EffectEnum.values()) {
            EffectElement effectElement = factory.createEffectElement(effect);

            // Trigger button action and add to the view
            Button button = effectElement.getButton();
            button.setTag(effect.getId());

            this.mainView.addView(button);
            button.setOnClickListener(this);

            // Add element to the list
            this.effectsMap.put(effect.getId(), effectElement);
        }
    }


    @Override
    public void onClick(View v) {
        if(v instanceof Button) {
            Button button = (Button) v;
            int id = (int) button.getTag();

            EffectElement effect = this.effectsMap.get(id);

            if(effect != null) {
                unselectAll();

                if(!effect.getType().equals(EffectEnum.NONE)) {
                    effect.setSelected(true);
                }

                updateEffectsSelectedState();
                this.getListener().onEffectChange(effect.getType());
            }
        }
    }

    /**
     * Unselect all the effects
     */
    private void unselectAll() {
        for(EffectElement element : this.effectsMap.values()) {
            element.setSelected(false);
        }
    }

    /**
     * Update the graphical states of effects
     */
    private void updateEffectsSelectedState() {
        for(EffectElement element : this.effectsMap.values()) {
            element.updatePreview();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnLeftFragmentInteractionListener {
        /**
         * Triggered when an effect change
         * @param effect The effect type
         */
        void onEffectChange(EffectEnum effect);
    }
}
