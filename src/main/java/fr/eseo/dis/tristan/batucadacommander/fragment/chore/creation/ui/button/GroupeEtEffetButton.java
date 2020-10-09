package fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.ui.button;

import android.widget.Button;

import fr.eseo.dis.tristan.batucadacommander.R;

public class GroupeEtEffetButton {

    private Button button;

    private final int selectedColor;
    private final int defaultColor;

    /**
     * Constructor for the Bloc button and initialization of the color
     * @param button The button
     */
    public GroupeEtEffetButton(Button button){
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
