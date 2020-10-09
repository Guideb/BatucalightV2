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
public class AutoEffectPreview extends EffectPreview {

    private static final String TEXT = "AUTO";
    private int color;

    /**
     * Define preview for ColorEffect Simple
     * @param color The color to display
     */
    public AutoEffectPreview(BColor color) {
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

        int sHeight = (int) ((float)height * 1f/2f);

        // draw background gradient
        Paint backgroundPaint = new Paint();

        this.drawBorders(backgroundPaint, canvas, width, height);

        //Set blank background
        backgroundPaint.setColor(BColor.WHITE);
        canvas.drawRect(BORDER_SIZE, BORDER_SIZE, width- BORDER_SIZE, height- BORDER_SIZE, backgroundPaint);

        //Write the text
        backgroundPaint.setColor(BColor.BLACK);
        GraphUtil.setTextSizeForWidth(backgroundPaint, width- 3* BORDER_SIZE, sHeight, TEXT);
        canvas.drawText(TEXT, BORDER_SIZE + 2, BORDER_SIZE + 2 + backgroundPaint.getTextSize(), backgroundPaint);

        backgroundPaint.setColor(this.getColor());
        canvas.drawRect(BORDER_SIZE, BORDER_SIZE + sHeight,
                width- BORDER_SIZE, height - BORDER_SIZE,
                backgroundPaint);
    }

}
