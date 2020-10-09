package fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.util.Collections;
import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.activity.BatucadaActivity;

/**
 * @author Tristan LE GACQUE
 * Created 06/12/2018
 */
public class ErrorDialog {

    private final Dialog dialog;

    /**
     * Create the dialog to display error
     * @param context The context
     * @param title The title
     * @param lines Lines to display
     */
    public ErrorDialog(BatucadaActivity context, String title, List<String> lines) {
        this.dialog = this.createDialog(context, title, lines);
    }

    /**
     * Create the dialog to display error
     * @param context The context
     * @param title The title
     * @param message the message to display
     */
    public ErrorDialog(BatucadaActivity context, String title, String message) {
        this(context, title, Collections.singletonList(message));
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
     * @param title The title
     * @param lines lines to display
     * @return The dialog created
     */
    private Dialog createDialog(final BatucadaActivity context, String title, List<String> lines) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        StringBuilder message = new StringBuilder();
        for(String line : lines) {
            message.append(line).append("\n");
        }

        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }

}
