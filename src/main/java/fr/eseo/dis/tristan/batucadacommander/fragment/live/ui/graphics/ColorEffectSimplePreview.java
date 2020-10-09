package fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;

import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;

/**
 * @author Tristan LE GACQUE
 * Created 09/11/2018
 */
public class ColorEffectSimplePreview extends EffectPreview {

    private int color;

    /**
     * Define preview for ColorEffect Simple
     * @param color The color to display
     */
    public ColorEffectSimplePreview(BColor color) {
        this.color = color.getColor();
    }

    /**
     * Get the selected color
     * @return The selected color
     */
    public int getColor() {
        return color;
    }

    /**
     * Define the color to display
     * @param color the color to set
     */
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        // get drawable dimensions
        Rect bounds = getBounds();

        int width = bounds.right - bounds.left;
        int height = bounds.bottom - bounds.top;

        // draw background gradient
        Paint backgroundPaint = new Paint();

        this.drawBorders(backgroundPaint, canvas, width, height);

        backgroundPaint.setColor(this.getColor());
        canvas.drawRect(BORDER_SIZE, BORDER_SIZE,
                width- BORDER_SIZE, height- BORDER_SIZE,
                backgroundPaint);

    }

}
