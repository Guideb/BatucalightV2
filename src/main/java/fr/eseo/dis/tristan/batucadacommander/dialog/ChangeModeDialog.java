package fr.eseo.dis.tristan.batucadacommander.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.activity.BatucadaActivity;

/**
 * @author Tristan LE GACQUE
 * Created 06/12/2018
 */
public class ChangeModeDialog {

    private final Dialog dialog;
    private Class<? extends Activity> to;
    private BatucadaActivity from;

    /**
     * Create the dialog to change mode
     * @param from From where
     * @param to Where to go
     */
    public ChangeModeDialog(BatucadaActivity from, Class<? extends Activity>  to) {
        this.to = to;
        this.from = from;
        this.dialog = this.createDialog(from);
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
    private Dialog createDialog(Activity context) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.mode_change_title)
                .setMessage(context.getString(R.string.change_mode_message, this.from.getModeName()))
                .setPositiveButton(R.string.mode_change_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(from, to);
                        from.startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.dialog_add_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Nothing special to do
                    }
                });

        return builder.create();
    }

}
