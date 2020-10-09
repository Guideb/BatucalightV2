package fr.eseo.dis.tristan.batucadacommander.communication;

public interface ISerialSendable {

    /**
     * Get the content of the message to send through Serial USB
     * @return The message content
     */
    MessageContent getContent();
}
