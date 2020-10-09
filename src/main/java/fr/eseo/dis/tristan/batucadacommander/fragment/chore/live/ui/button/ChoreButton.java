package fr.eseo.dis.tristan.batucadacommander.fragment.chore.live.ui.button;

import android.widget.Button;

import fr.eseo.dis.tristan.batucadacommander.R;

public class ChoreButton {

    private Button button;

    private final int selectedColor;
    private final int defaultColor;

    /**
     * Constructor for the chore button and initialization of the color
     * @param button The button
     */
    public ChoreButton(Button button){
        this.button = button;
        this.defaultColor = button.getResources().getColor(R.color.colorGray);
        this.selectedColor = button.getResources().getColor(R.color.colorPrimary);
    }


    public Button getButton(){return button;}

    /**
     * Set the selected state
     * @param b True if selected, false otherwise
     */
    public void select(boolean b){
        this.getButton().setBackgroundColor(b ? selectedColor : defaultColor);
    }
}
