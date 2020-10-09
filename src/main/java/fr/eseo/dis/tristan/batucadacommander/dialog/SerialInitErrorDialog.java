package fr.eseo.dis.tristan.batucadacommander.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.activity.BatucadaActivity;

/**
 * @author Tristan LE GACQUE
 * Created 06/12/2018
 */
public class SerialInitErrorDialog {

    private final Dialog dialog;

    /**
     * Create the dialog to display error
     * @param context The context
     */
    public SerialInitErrorDialog(BatucadaActivity context) {
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

        builder.setTitle(R.string.com_error_init_title)
                .setMessage(R.string.com_error_init_message)
                .setPositiveButton(R.string.com_error_init_retry, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        context.initCommunicationManager();
                    }
                })
                .setNegativeButton(R.string.com_error_init_quit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        context.finishAffinity();
                    }
                }).setCancelable(false);

        return builder.create();
    }

}
