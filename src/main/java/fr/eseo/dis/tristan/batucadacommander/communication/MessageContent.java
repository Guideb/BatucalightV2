package fr.eseo.dis.tristan.batucadacommander.communication;

import android.util.Log;

import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;
import fr.eseo.dis.tristan.batucadacommander.lib.usbserial.driver.ProlificSerialDriver;

public class MessageContent {

    private int mode;
    private int red;
    private int green;
    private int blue;
    private int speed;
    private int red2;
    private int green2;
    private int blue2;
    private int period;
    private int duration;
    private int trigger;



    private byte[] message2Build;





    // TODO Mettre doc
    public MessageContent(int mode, int r, int v, int b, int x,int d) {
        this.mode = mode;
        this.red = r;
        this.green = v;
        this.blue = b;
        this.speed = x;
        this.duration = d;
        switch (mode) {
            case 3:
                message2Build = Protocol.createStrob(r, v, b, x, d);
                break;
        }
    }


    // TODO Mettre la doc
    public MessageContent(int mode, int r1, int v1, int b1, int p, int d, int r2, int v2, int b2, int trigger){
        this.mode = mode;
        this.red = r1;
        this.green = v1;
        this.blue = b1;
        this.period = p;
        this.duration = d;
        this.red2 = r2;
        this.green2 = v2;
        this.blue2 = b2;
        this.trigger = trigger;
        message2Build = Protocol.creationEffect(mode, r1, v1, b1, p, d, r2, v2, b2, trigger);

    }



    // TODO Mettre la doc
    public MessageContent(int mode){
        this.mode = mode;
        switch (mode){
            case 99:
                message2Build = Protocol.createEteindre();
                break;
            case 100:
                message2Build = Protocol.createTest();
                break;
            default:
                break;
        }
    }


    /**
     * Get the message content
     * It represent the content as an array of 8 bytes
     * @param intensity The intensity of RGB
     * @return The content as byte array
     */
    public byte[] getMessageContent(int intensity) {
        Log.i("COM", "message2Build = " + message2Build);

        byte[] message = new byte[message2Build.length];
        for(int i = 0; i<message2Build.length;i++){
            message[i] = message2Build[i];
        }
        return message;
    }

    /**
     * Apply intensity on R, G, B value.
     * Intensity if a ratio [0-1]
     * EX : 255,255,255 with Intensity of 50 => 127, 127, 127
     * @param btw0and255 The value [0 - 255]
     * @param percent The intensity [0 - 100]
     * @return The final value
     */
    private static int applyIntensity(int btw0and255, int percent) {
        return (int) (btw0and255 * ((float) percent / 100f));
    }

    /**
     * Convert speed between [0 - 200] on [0 - 255]
     * @param speedBetween0And200 The speec [0 - 200]
     * @return The speed [0 - 255]
     */
    public static int computeSpeedOn255(int speedBetween0And200) {
        return (int) (255 - (speedBetween0And200 * 1.275d));
    }
}
