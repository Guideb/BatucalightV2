package fr.eseo.dis.tristan.batucadacommander.fragment.live.ui;

import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.Button;

import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.enums.ParamEnum;

/**
 * @author Tristan LE GACQUE
 * Created 12/10/2018
 */
public class ParamElement {

    private ParamEnum param;
    private View view;
    private boolean selected;
    private Object value;

    /**
     * UI Element that store the param value
     * @param view The view to enable to select the param
     * @param param The param
     */
    public ParamElement(ParamEnum param, View view) {
        this.param = param;
        this.view = view;
        this.selected = false;
        this.value = null;

        if(ParamEnum.Type.COLOR.equals(param.getType())) {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            //Si probleme, penser a ajouter l'objet ET le tag dans le layout
            view.setBackground(drawable);
        }

        this.updateColor();
    }

    /**
     * Update the displayed color of the ui machine object
     */
    private void updateColor() {
        if(ParamEnum.Type.COLOR.equals(param.getType())) {
            GradientDrawable drawable = (GradientDrawable) this.view.getBackground();

            if(this.isSelected()) {
                drawable.setStroke(10, BColor.PINK);
            } else {
                drawable.setStroke(0, BColor.WHITE);
            }


            if(this.getValue() instanceof BColor) {
                drawable.setColor(((BColor)this.getValue()).getColor());
            } else {
                drawable.setColor(BColor.WHITE);
            }
        }
    }

    /**
     * Is selected
     * @return true if selected, false otherwise
     */
    public boolean isSelected() {
        return this.view instanceof Button && selected;
    }

    /**
     * Set the selected status
      * @param selected status
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
        this.updateColor();
    }

    /**
     * Get the value
     * @return BColor object if color, Integer if integer
     */
    public Object getValue() {
        return value;
    }

    /**
     * Get the associated param
     * @return The param
     */
    public ParamEnum getParam() {
        return param;
    }

    /**
     * Set the value of param
      * @param value Integer if param type is INTEGER, BColor is param type is COLOR
     */
    public void setValue(Object value) {
        this.value = value;
        if(value instanceof BColor) {
            this.updateColor();
        }
    }

}
