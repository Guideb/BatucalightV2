package fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.graphics;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;

import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;

/**
 * @author Tristan LE GACQUE
 * Created 09/11/2018
 */
public abstract class EffectPreview extends Drawable{

    static final int BORDER_SIZE = 10;

    private boolean selected;

    /**
     * Constructor
     */
    EffectPreview() {
        this.selected = false;
    }

    /**
     * Check if the ui element is selected
     * @return True if selected, false otherwise
     */
    public boolean isSelected() {
        return this.selected;
    }

    /**
     * Set the selected state of the effect preview
     * @param selected Selected state
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Draw the border with the paint on canvas
     * @param paint The paint
     * @param canvas The canvas
     * @param width The width
     * @param height The height
     */
    void drawBorders(Paint paint, Canvas canvas, int width, int height) {
        // draw border
        Log.d("DEBUGCOLOR", "DRAW !");
        if(this.isSelected()) {
            Log.d("DEBUGCOLOR", "SELECTED !");
            paint.setColor(BColor.PINK);
            canvas.drawRect(0, 0, width, height, paint);
        }
    }


    @Override
    public void setAlpha(int alpha) {}

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
