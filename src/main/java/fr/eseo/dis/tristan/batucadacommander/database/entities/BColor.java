package fr.eseo.dis.tristan.batucadacommander.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Color;
import android.support.annotation.NonNull;

/**
 * @author Tristan LE GACQUE
 * Created 12/10/2018
 */
@Entity
public class BColor {

    public static final BColor B_WHITE = new BColor(0, 0, 255, 255, 255);
    public static final BColor B_GRAY = new BColor(0, 0, 210, 210, 210);
    public static final BColor B_PINK = new BColor(0, 0, 255, 20, 147);
    public static final BColor B_BLACK = new BColor(0, 0, 0, 0, 0);
    public static final BColor B_GREEN = new BColor(0, 0, 0,255,0);
    public static final BColor B_RED = new BColor(0, 0, 217,83,79);
    public static final BColor B_ORANGE = new BColor(0, 0, 255,136,0);
    public static final BColor B_BLUE = new BColor(0, 0, 0, 153, 204);


    public static final int WHITE = B_WHITE.getColor();
    public static final int GRAY = B_GRAY.getColor();
    public static final int PINK = B_PINK.getColor();
    public static final int BLACK = B_BLACK.getColor();
    public static final int GREEN = B_GREEN.getColor();
    public static final int RED = B_RED.getColor();
    public static final int ORANGE = B_ORANGE.getColor();
    public static final int BLUE = B_BLUE.getColor();

    @PrimaryKey(autoGenerate = true)
    private int idColor;

    private int position;
    private int red;
    private int green;
    private int blue;

    /**
     * Constructor
     * @param idColor UNIQUE ID
     * @param position position on grid
     * @param red RED [0-255]
     * @param green GREEN [0-255]
     * @param blue BLUE [0-255]
     */
    public BColor(int idColor, int position, int red, int green, int blue) {
        this.idColor = idColor;
        this.position = position;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Get color ID
     * @return Color ID
     */
    public int getIdColor() {
        return idColor;
    }

    /**
     * Set color ID
     * @param idColor color ID
     */
    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }

    /**
     * Get position
     * @return the position on the grid
     */
    public int getPosition() {
        return position;
    }

    /**
     * Set the position on the grid
     * @param position the position on the grid
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Get red [0-255]
     * @return Red
     */
    public int getRed() {
        return red;
    }

    /**
     * Set red [0-255]
     * @param red red
     */
    public void setRed(int red) {
        this.red = red;
    }

    /**
     * Get green [0-255]
     * @return green
     */
    public int getGreen() {
        return green;
    }

    /**
     * Set green [0-255]
     * @param green green
     */
    public void setGreen(int green) {
        this.green = green;
    }

    /**
     * Get blue [0-255]
     * @return blue
     */
    public int getBlue() {
        return blue;
    }

    /**
     * Set blue [0-255]
     * @param blue blue
     */
    public void setBlue(int blue) {
        this.blue = blue;
    }

    /**
     * Get int color
     * @return color as int
     */
    public int getColor() {
        return Color.argb(255, this.red, this.green, this.blue);
    }

    @NonNull
    @Override
    public String toString() {
        return "(" + this.getRed() + " " + this.getGreen() + " " + this.getBlue() + ")";
    }
}
