package fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;

import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;

public class ArcencielEffectPreview extends EffectPreview{

    private static final int MAX_FREQ = 200;
    private int freq;

    public ArcencielEffectPreview(Integer frequence){
        this.freq = frequence;
    }

    private int getFreq() {
        return this.freq;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        // get drawable dimensions
        Rect bounds = getBounds();
        int width = bounds.right - bounds.left;
        int height = bounds.bottom - bounds.top;
        Paint backgroundPaint = new Paint();

        this.drawBorders(backgroundPaint, canvas, width, height);
        canvas.drawRect(BORDER_SIZE, BORDER_SIZE,
                width- BORDER_SIZE, height- BORDER_SIZE,
                backgroundPaint);

        //Draw line to show the frequency
        backgroundPaint.setColor(BColor.BLACK);

        width = (int) ( ((float)(this.getFreq() * (width- BORDER_SIZE))) / MAX_FREQ);
        canvas.drawRect(BORDER_SIZE, BORDER_SIZE + (int) ((float)height * 1f/1.5f),
                width, height - BORDER_SIZE,
                backgroundPaint);
    }
}
