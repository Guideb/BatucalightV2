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
public class StroboscopeEffectPreview extends EffectPreview {

    private static final int MAX_FREQ = 200;
    private static final String TEXT = "STROB";

    private int color;
    private int freq;

    /**
     * Define preview for ColorEffect Simple
     * @param color The color to display
     * @param freq The frequency
     */
    public StroboscopeEffectPreview(BColor color, Integer freq) {
        this.color = color.getColor();
        this.freq = freq;
    }

    /**
     * Get the selected color
     * @return The selected color
     */
    private int getColor() {
        return color;
    }

    /**
     * Define the color to display
     * @param color the color to set
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Get the frequency
     * @return The frequency [0 - 200]
     */
    private int getFreq() {
        return freq;
    }

    /**
     * Set the frequency
     * @param freq the frequency [0 - 200]
     */
    public void setFreq(int freq) {
        if(freq < 0) {
            this.freq = 0;
        } else if(freq > MAX_FREQ) {
            this.freq = MAX_FREQ;
        } else {
            this.freq = freq;
        }
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
        width = (int) ( ((float)(this.getFreq() * (width- BORDER_SIZE))) / MAX_FREQ);
        canvas.drawRect(BORDER_SIZE, BORDER_SIZE + sHeight,
                width, height - BORDER_SIZE,
                backgroundPaint);
    }

}
