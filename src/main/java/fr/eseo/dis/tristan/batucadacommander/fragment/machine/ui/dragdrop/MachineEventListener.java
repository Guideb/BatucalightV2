package fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dragdrop;

import android.view.DragEvent;

import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;

public interface MachineEventListener {

    /**
     * Fire when machine is selected
     * @param machine the selected machine
     * @param from From where
     */
    void onMachineMove(Machine machine, DropFragmentEnum from);

    /**
     * Fire when machine is dropped (can also fire onMachineMove)
     * @param machine The machine
     * @param event The associated event
     */
    void onMachineDrop(Machine machine, DragEvent event);

    /**
     * Fire when machine is clicked
     * @param machine the clicked machine
     * @param position The position on Adapter
     */
    void onMachineClicked(Machine machine, int position);

    /**
     * Fire when machine enter into dropzone
     * @param machine The machine
     */
    void onMachineEnterDropzone(Machine machine);

    /**
     * Fire when machine exit a dropzone
     * @param machine The machine
     */
    void onMachineExitDropzone(Machine machine);
}
