package fr.eseo.dis.tristan.batucadacommander.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.activity.BatucadaActivity;
import fr.eseo.dis.tristan.batucadacommander.communication.CommunicationManager;
import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.EffectAuto;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.task.GetAllMachinesTask;

/**
 * @author Tristan LE GACQUE
 * Created 06/12/2018
 */
public class QuitConfirmDialog {

    private final Dialog dialog;

    /**
     * Create the dialog to display error
     * @param context The context
     */
    public QuitConfirmDialog(BatucadaActivity context) {
        this.dialog = this.createDialog(context);
    }


    /**
     * Show the dialog
     */
    public void show(){
        this.dialog.show();
    }

    /**
     * Create dialog
     * @param context The context
     * @return The dialog created
     */
    private Dialog createDialog(final BatucadaActivity context) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.main_dialog_quit_title)
                .setMessage(R.string.main_dialog_quit_message)
                .setPositiveButton(R.string.main_dialog_quit_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Get the machines
                        List<Machine> machines;
                        ArrayList<Machine> listMachines = new ArrayList<>();
                        try {
                            machines = new GetAllMachinesTask(context).execute().get();
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                            machines = new ArrayList<>();
                        }

                        //Send "AUTO White" to all machines
                        CommunicationManager communicationManager = context.getCommunicationManager();
                        EffectAuto effectAuto = new EffectAuto(BColor.B_WHITE, 254);

                        for(Machine machine : machines) {
                            int idGroupe = machine.getRefGroupe();
                            listMachines.add(machine);
                            communicationManager.sendEffectToMachine(effectAuto,idGroupe, listMachines,machine.getIntensity());
                        }

                        //End the application
                        context.finishAffinity();
                    }
                })
                .setNegativeButton(R.string.dialog_add_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Do nothing
                    }
                });

        return builder.create();
    }

}
