package fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.graphics;

import android.graphics.Paint;
import android.graphics.Rect;

/**
 * @author Tristan LE GACQUE
 * Created 23/11/2018
 */
class GraphUtil {

    /**
     * Utilitary class
     */
    private GraphUtil() {
    }

    /**
     * Change the paint to draw text on the desired width
     * @param paint The pain
     * @param desiredWidth The desired width
     * @param maxHeight The max height
     * @param text The text to draw
     */
    static void setTextSizeForWidth(Paint paint, float desiredWidth, float maxHeight, String text) {
        // Set the paint for that size.
        paint.setTextSize(getTextSizeForWidthAndHeight(desiredWidth, maxHeight, text));
    }

    /**
     * Get the best text size to fit both height and width
     * @param desiredWidth Max width
     * @param maxHeight Max height
     * @param text The text to fit in
     * @return The magic text size
     */
    public static float getTextSizeForWidthAndHeight(float desiredWidth, float maxHeight, String text) {
        Paint paint = new Paint();

        // Pick a reasonably large value for the test. Larger values produce
        // more accurate results, but may cause problems with hardware
        // acceleration. But there are workarounds for that, too; refer to
        // http://stackoverflow.com/questions/6253528/font-size-too-large-to-fit-in-cache
        final float testTextSize = 48f;

        // Get the bounds of the text, using our testTextSize.
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        // Calculate the desired size as a proportion of our testTextSize.
        float desiredTextSizeW = testTextSize * desiredWidth / bounds.width();
        float desiredTextSizeH = testTextSize * maxHeight / bounds.height();

        return Math.min(desiredTextSizeH, desiredTextSizeW);
    }
}
