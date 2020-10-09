package fr.eseo.dis.tristan.batucadacommander.communication;

public interface LogListener {

    /**
     * Log a message
     * @param tag The tag
     * @param message The message
     */
    void log(String tag, String message);
}
