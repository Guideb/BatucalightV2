package fr.eseo.dis.tristan.batucadacommander.fragment.live.modules;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;
import fr.eseo.dis.tristan.batucadacommander.fragment.ObservableFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.ParamEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.ParamElement;

import static android.support.constraint.Constraints.TAG;

/**
 * @author Tristan LE GACQUE
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ParamsModuleFragment.OnParamsInteraction} interface
 * to handle interaction events.
 * Use the {@link ParamsModuleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParamsModuleFragment extends ObservableFragment<ParamsModuleFragment.OnParamsInteraction> implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private static final String BUNDLE_PARAM = "BUNDLE_PARAM";

    private Map<Button, ParamElement> buttons;
    private Map<SeekBar, ParamElement> inputs;
    private List<ParamEnum> params;
    private String textStr;
    private TextView textView;

    /**
     * Fragment for color palette
     */
    public ParamsModuleFragment() {
        super();
    }

    /**
     * Get a new instance of param module
     * @param params Parameters
     * @return A paletteModuleFragment
     */
    public static ParamsModuleFragment newInstance(ParamEnum... params) {
        ParamsModuleFragment paramsModuleFragment = new ParamsModuleFragment();
        ArrayList<String> paramList = new ArrayList<>();
        for(ParamEnum param : params) {
            paramList.add(param.toString());
        }

        Bundle args = new Bundle();
        args.putStringArrayList(BUNDLE_PARAM, paramList);
        paramsModuleFragment.setArguments(args);

        return paramsModuleFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.params = new ArrayList<>();

            List<String> paramStr = getArguments().getStringArrayList(BUNDLE_PARAM);
            if(paramStr != null) {
                for(String paramS : paramStr) {
                    this.params.add(ParamEnum.findParam(paramS));
                }
            }
        }

        this.buttons = new HashMap<>();
        this.inputs = new HashMap<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.module_param, container, false);

        // Build the parameters
        for(ParamEnum param : this.params) {
            this.buildParameter(view, param);
        }

        this.textView = view.findViewById(R.id.module_text_text);
        this.showText(this.textStr);


        //Select the first
        if (this.buttons.values().toArray().length > 0){
            ((ParamElement)this.buttons.values().toArray()[0]).setSelected(true);
            this.selectNextButton();
        }

        return view;
    }

    /**
     * Appelé lorsqu'un bouton est pressé via l'event onClick()
     * @param button Bouton qui est press
     */
    public void onButtonPressed(Button button) {
        ParamElement paramElement = this.buttons.get(button);
        for(ParamElement b : this.buttons.values()) {
            b.setSelected(false);
        }

        if (paramElement != null) {
            paramElement.setSelected(true);
        }
    }

    /**
     * Select the next button
     */
    public void selectNextButton() {
        int selected = 0;
        int max = this.buttons.values().size();
        for(ParamElement pe : this.buttons.values()) {
            if(pe.isSelected()) {
                break;
            }
            selected++;
        }

        if(selected != max) {
            selected = selected + 1 < max ? selected + 1 : 0;
            int i = 0;
            for(ParamElement pe : this.buttons.values()) {
                pe.setSelected(i == selected);
                i++;
            }
        }

    }
    /**
     * Build parameter ui element
     * @param view The main view
     * @param paramEnum The parameter
     */
    private void buildParameter(View view, ParamEnum paramEnum) {
        switch (paramEnum.getType()) {
            case COLOR:
                Button button = view.findViewWithTag(paramEnum.toString());
                this.buttons.put(button, new ParamElement(paramEnum, button));
                button.setOnClickListener(this);

                button.setVisibility(View.VISIBLE);
                break;
            case INTEGER:
                SeekBar bar = view.findViewWithTag(paramEnum.toString()+ "_BAR");
                TextView textView = view.findViewWithTag(paramEnum.toString()+ "_TEXT");
                this.inputs.put(bar, new ParamElement(paramEnum, bar));
                bar.setOnSeekBarChangeListener(this);
                ((View) bar.getParent()).setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                bar.setVisibility(View.VISIBLE);
                this.changeLabelValue(bar, bar.getProgress());

                //FADE

                SeekBar barIntensity = view.findViewWithTag((paramEnum.toString()+ "_BAR"));
                TextView textIntensity = view.findViewWithTag((paramEnum.toString()+ "_TEXT"));
                barIntensity.setVisibility(View.VISIBLE);
                textIntensity.setVisibility(View.VISIBLE);
                this.changeLabelValueIntensity(barIntensity, barIntensity.getProgress());

                break;
            case NOTYPE:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if(v instanceof Button) {
            onButtonPressed((Button)v);
        }
    }

    /**
     * Set color on selected Element
     * @param color color
     */
    public void setColorOnSelected(BColor color) {
        ParamElement paramSelected = getParamSelected();
        if(paramSelected != null) {
            paramSelected.setValue(color);

            this.notifyParamUpdate();
        }
    }

    /**
     * Notify listener that param changed
     */
    public void notifyParamUpdate() {
        if(this.getListener() != null) {
            List<ParamElement> paramElementList = new ArrayList<>(this.buttons.values());
            paramElementList.addAll(this.inputs.values());

            this.getListener().onParamUpdated(paramElementList.toArray(new ParamElement[0]));
        }
    }

    /**
     * Get the selected Param
     * @return The selected param
     */
    public ParamElement getParamSelected() {
        for(ParamElement pe : this.buttons.values()) {
            if(pe.isSelected()) {
                return pe;
            }
        }
        return null;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        changeLabelValue(seekBar, progress);

        //FADE
        changeLabelValueIntensity(seekBar, progress);
        this.notifyParamUpdate();
    }

    /**
     * Change the label value for SEEKBAR
     * @param seekBar The seekbar
     * @param progress The value
     */
    private void changeLabelValue(SeekBar seekBar, int progress) {
        View view = (View) seekBar.getParent();
        TextView label = view.findViewById(R.id.param_slider_label);
        label.setText(this.getResources().getString(R.string.param_frequence, (float)progress/100f));

        //Update input value
        ParamElement paramElement = this.inputs.get(seekBar);
        if(paramElement != null) {
            paramElement.setValue(progress);
        }
    }

    /**
     * Change the label value for SEEKBAR
     * @param seekBar The seekbar
     * @param progress The value
     */
    private void changeLabelValueIntensity(SeekBar seekBar, int progress) {
        View view = (View) seekBar.getParent();
        TextView label = view.findViewById(R.id.param_slider_label_intensite);
        label.setText(this.getResources().getString(R.string.param_intensite, (float)progress/100f));

        //Update input value
        ParamElement paramElement = this.inputs.get(seekBar);
        if(paramElement != null) {
            paramElement.setValue(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //Don't care
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //Don't care
    }

    /**
     * Show text in bottom of the fragment
     * @param text The text to show, null to hide
     */
    public void showText(String text) {
        this.textStr = text;

        if(this.textView != null) {
            this.textView.setText(text);
            this.textView.setVisibility(text == null ? View.INVISIBLE : View.VISIBLE);
        }
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnParamsInteraction {
        /**
         * Called when param color is updated
         * @param params The params
         */
        void onParamUpdated(ParamElement[] params);
    }


}
