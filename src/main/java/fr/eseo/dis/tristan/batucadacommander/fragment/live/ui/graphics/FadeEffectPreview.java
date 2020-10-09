package fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;

import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;

/**
 * Test pour Fade
 */

public class FadeEffectPreview extends EffectPreview{

    private int color;
    private Integer intensity;
    private static final String TEXT = "FADE";

    /**
     * Define preview for ColorEffect Fade
     * @param color The color to display
     * @param intensity The intensity of LED
     */
   public FadeEffectPreview(BColor color, Integer intensity){
       this.color = color.getColor();
       this.intensity = intensity;
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


    /**
     * Get the selected duree
     * @return The selected color
     */
    public int getIntensity() {
        return intensity;
    }

    /**
     * Define the duree to display
     * @param intensity the duree to set
     */
    public void setIntensity(int intensity) {
        this.intensity = intensity;
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

        backgroundPaint.setColor(this.getColor());
        canvas.drawRect(BORDER_SIZE, BORDER_SIZE,
                width- BORDER_SIZE, height- BORDER_SIZE,
                backgroundPaint);

        //Write the text
        backgroundPaint.setColor(BColor.BLACK);
        GraphUtil.setTextSizeForWidth(backgroundPaint, width- 3* BORDER_SIZE, sHeight, TEXT);
        canvas.drawText(TEXT, BORDER_SIZE + 2, BORDER_SIZE + 2 + backgroundPaint.getTextSize(), backgroundPaint);

    }
}
