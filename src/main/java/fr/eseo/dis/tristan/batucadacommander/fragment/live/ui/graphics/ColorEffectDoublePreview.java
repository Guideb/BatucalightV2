package fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.graphics;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.support.annotation.NonNull;

import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;

/**
 * @author Tristan LE GACQUE
 * Created 09/11/2018
 */
public class ColorEffectDoublePreview extends EffectPreview {

    private static final int MAX_FREQ = 200;

    private int color1;
    private int color2;
    private int freq;

    /**
     * Define preview for ColorEffect Simple
     * @param color1 The first color to display
     * @param color2 The second color to display
     * @param frequence The frequence from color 1 to color 2
     */
    public ColorEffectDoublePreview(BColor color1, BColor color2, Integer frequence) {
        this.color1 = color1.getColor();
        this.color2 = color2.getColor();
        this.freq = frequence;
    }

    /**
     * Get the selected color
     * @return The selected color
     */
    private int[] getColors() {
        return new int[]{color1, color2};
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

        // draw background gradient
        Paint backgroundPaint = new Paint();

        this.drawBorders(backgroundPaint, canvas, width, height);

        int barWidth = (width - BORDER_SIZE) / this.getColors().length;
        int barWidthRemainder = (width - BORDER_SIZE) % this.getColors().length;
        for (int i = 0; i < this.getColors().length; i++) {
            backgroundPaint.setColor(this.getColors()[i]);
            canvas.drawRect(
                    BORDER_SIZE + (i * barWidth),
                    BORDER_SIZE,
                    (i + 1) * barWidth,
                    height - BORDER_SIZE,
                    backgroundPaint);
        }

        if (barWidthRemainder > 0) {
            canvas.drawRect((this.getColors().length * barWidth), 0,
                    this.getColors().length * barWidth + barWidthRemainder,
                    height, backgroundPaint);
        }

        //Draw line to show the frequency
        backgroundPaint.setColor(BColor.BLACK);

        width = (int) ( ((float)(this.getFreq() * (width- BORDER_SIZE))) / MAX_FREQ);
        canvas.drawRect(BORDER_SIZE, BORDER_SIZE + (int) ((float)height * 1f/1.5f),
                width, height - BORDER_SIZE,
                backgroundPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        //Not supported
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        //Not supported
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    /**
     * Define colors
     * @param color1 color1
     * @param color2 color2
     */
    public void setColors(BColor color1, BColor color2) {
        this.setColors(color1.getColor(), color2.getColor());
    }

    /**
     * Define colors
     * @param color1 color1
     * @param color2 color2
     */
    private void setColors(int color1, int color2) {
        this.color1 = color1;
        this.color2 = color2;
    }
}
