package fr.eseo.dis.tristan.batucadacommander.fragment.color.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Locale;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;

/**
 * @author Tristan LE GACQUE
 * Created 06/12/2018
 */
public class ChangeColorDialog {

    private final Dialog dialog;
    private final OnColorChangeListener listener;

    private EditText r;
    private EditText g;
    private EditText b;
    private ImageView preview;

    /**
     * Create the dialog to add machine
     * @param context The context
     * @param current The current selected color
     * @param listener the listener
     */
    public ChangeColorDialog(Context context, final BColor current, OnColorChangeListener listener) {
        this.listener = listener;
        this.dialog = this.createDialog(context);


        this.dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                //Define default
                ChangeColorDialog.this.r.setText(String.format(Locale.FRANCE, "%d", current.getRed()));
                ChangeColorDialog.this.g.setText(String.format(Locale.FRANCE, "%d", current.getGreen()));
                ChangeColorDialog.this.b.setText(String.format(Locale.FRANCE, "%d", current.getBlue()));

                //Set preview
                ChangeColorDialog.this.updatePreviewColor();
            }
        });

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
    private Dialog createDialog(Context context) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.dialog_pick_color, null);

        this.r = view.findViewById(R.id.colorpicker_red);
        this.g = view.findViewById(R.id.colorpicker_green);
        this.b = view.findViewById(R.id.colorpicker_blue);
        this.preview = view.findViewById(R.id.color_preview);

        //Handle responses
        this.configureColorInput(this.r);
        this.configureColorInput(this.g);
        this.configureColorInput(this.b);

        //Inflate the layout
        builder.setView(view)
                .setPositiveButton(R.string.dialog_set_setcolor, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onColorPicked(getSelectedColor());
                    }
                })
                .setNegativeButton(R.string.dialog_add_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Nothing special to do
                    }
                }).setCancelable(false);


        return builder.create();
    }

    /**
     * Configure the input as int input [0-255] and handle color change on value change
     * @param editText The input
     */
    private void configureColorInput(EditText editText) {
        editText.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 255)});

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //On text change, update color
                updatePreviewColor();
            }
        });
    }


    /**
     * Update the preview color
     */
    private void updatePreviewColor() {
        this.preview.setColorFilter(this.getSelectedColor().getColor());
    }

    /**
     * Get color RGB in input
     * @return The BColor
     */
    private BColor getSelectedColor() {
        int red = getInteger(r.getText().toString());
        int green = getInteger(g.getText().toString());
        int blue = getInteger(b.getText().toString());

        return new BColor(0, 0, red, green, blue);
    }

    /**
     * Get integer, 0 as defautl value
     * @param s The string
     * @return The integer
     */
    private int getInteger(String s) {
        int i;
        try {
            i = Integer.parseInt(s);
        } catch (Exception e) {
            i = 0;
        }

        return i;
    }

    public interface OnColorChangeListener {
        /**
         * Fired when color is picked
         * @param color The color
         */
        void onColorPicked(BColor color);
    }


}
