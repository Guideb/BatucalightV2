package fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui;

import android.widget.Button;

import fr.eseo.dis.tristan.batucadacommander.R;

/**
 * Contains the UI button for group selection
 */
public class GroupeButton {

    private Button button;

    private final int selectedColor;
    private final int defaultColor;

    /**
     * Constructor for the Groupe button
     * @param button The button
     */
    public GroupeButton(Button button) {
        this.button = button;
        this.defaultColor = button.getResources().getColor(R.color.colorGray);
        this.selectedColor = button.getResources().getColor(R.color.colorPrimary);
    }

    /**
     * Get the UI component
     * @return The button
     */
    public Button getButton() {
        return button;
    }

    /**
     * Set the selected state
     * @param selected True if selected, false otherwise
     */
    public void select(boolean selected) {
        this.getButton().setBackgroundColor(selected ? selectedColor : defaultColor);
    }
}
