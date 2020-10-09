package fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui;

import android.view.View;

public class MachineUI {

    private View view;

    /**
     * Constructor for the Groupe view
     * @param button The view
     */
    public MachineUI(View button) {
        this.view = button;
    }

    /**
     * Get the UI component
     * @return The view
     */
    public View getView() {
        return view;
    }

}
