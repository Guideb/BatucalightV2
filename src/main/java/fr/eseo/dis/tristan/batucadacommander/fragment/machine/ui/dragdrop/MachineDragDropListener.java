package fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dragdrop;

import android.view.DragEvent;
import android.view.View;

import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;

public class MachineDragDropListener implements View.OnDragListener{

    private MachineEventListener mListener;
    private DropFragmentEnum thisFragment;

    /**
     * Listener to machine drop
     * @param thisFragment The fragment that listen drop event
     * @param listener The listener of the machine event
     */
    public MachineDragDropListener(DropFragmentEnum thisFragment, MachineEventListener listener) {
        this.thisFragment = thisFragment;
        this.mListener = listener;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        View view = (View) event.getLocalState();
        Machine machine = view.getTag() instanceof Machine ? (Machine) view.getTag() : null;

        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_ENTERED:
                if(machine != null) {
                    this.mListener.onMachineEnterDropzone(machine);
                }
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                if(machine != null) {
                    this.mListener.onMachineExitDropzone(machine);
                }
                break;
            case DragEvent.ACTION_DROP:
                if(machine != null) {
                    DropFragmentEnum from = DropFragmentEnum.getByName(event.getClipData().getItemAt(0).getText().toString());

                    //If move from one fragment to another one
                    if(!thisFragment.equals(from)) {
                        this.mListener.onMachineMove(machine, from);
                    }

                    //In all case trigger onDrop
                    this.mListener.onMachineDrop(machine, event);
                }
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                view.setVisibility(View.VISIBLE);
                this.mListener.onMachineExitDropzone(machine);
                break;
            default:
                break;
        }
        return true;
    }

}
