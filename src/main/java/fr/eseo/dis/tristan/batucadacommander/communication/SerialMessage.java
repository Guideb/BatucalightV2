package fr.eseo.dis.tristan.batucadacommander.communication;

import android.util.Log;

import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;

/**
 * Idée de développement pour la prochaine version du code.
 * Utiliser un ByteBuffer au lieu d'un "byte[] recipient".
 * Cela permettra de stocker les données plus rapidement.
 * @author Marc Saint-Antonin
 */

public class SerialMessage {
    private static final int SIZE = 33;

    private byte[] message;
    private int idGroupe;
    private AppDatabase appDatabase;
    private List<Machine> listMachine;

    /**
     * Constructor of SerialMessage
     * It contains array of bytes to send through Serial USB
     * @param message The message (array of bytes) that will be sent over usb
     */
    private SerialMessage(byte[] message) {
        if(message.length > SIZE+1) {
            Log.i("COM", message.length +"" );
            throw new IllegalArgumentException("La taille doit être de " + SIZE + " elements !");
        }

        this.message = message;
    }

    /**
     * Get the full message
     * @return The message to send through Serial
     */
    public byte[] getMessage() {
        return message;
    }



    /**
     * Method which set a bit into a byte
     * @param value
     * @param bit
     * @return a byte with the required bit set
     */
    public static byte set(byte value, int bit){
        return (byte)(value|(1<<bit));
    }

    /**
     * Build a SerialMessage
     * @param idGroupe the id of the group
     * @param listMachine List of machines
     * @param sendable The content
     * @param intensity The intensity of the effect
     * @return The serial message
     */
    public static SerialMessage build(int idGroupe, int[] listMachine, ISerialSendable sendable, int intensity) {
        byte[] messageContent = sendable.getContent().getMessageContent(intensity);

        String s = new String(messageContent);
        Log.i("COM", "Message content (string)" + s);


        /** Modification du protocole par Marc **/
        for(int i = 0; i<listMachine.length; i++){
            Log.i("SERIAL MESSAGE : ", "ID machines dans le groupe : " + listMachine[i]);
        }

        byte[] recipient = new byte[6];

        for (int i = 0; i< recipient.length;i++){
            recipient[i] = (byte)0x00;
        }

        int[] machines = new int[48];
        for(int i =0 ; i < listMachine.length; i++){
            machines[machines.length-1-i] = listMachine[i];
        }

        Log.i("Serial Message ", "Première machine de la liste dans le groupe : " + machines[machines.length-1]);
        Log.i("Serial Message ", "Deuxième machine de la liste dans le groupe : " + machines[machines.length-2]);

        /** Si l'adresse de la première machine du groupe est différent de 0 **/
        if(machines[machines.length-1] != 0){

            Log.i("Serial Message ", "Je m'adresse aux machines du groupe : " + idGroupe);

            for(int i = 0; i <machines.length; i++){

                if(machines[i] > 0 && machines[i] <= 8){
                    recipient[5] = set(recipient[5],machines[i]);
                }
                if (machines[i] > 8 && machines[i] <= 16){
                    recipient[4] = set(recipient[4],machines[i]-8);
                }
                if (machines[i] > 16 && machines[i] <= 24){
                    recipient[3] = set(recipient[3],machines[i]-16);
                }
                if (machines[i] > 24 && machines[i] <= 32){
                    recipient[2] = set(recipient[2],machines[i]-24);
                }
                if (machines[i] > 32 && machines[i] <= 40){
                    recipient[1] = set(recipient[1],machines[i]-32);
                }
                if (machines[i] > 40 && machines[i] <= 48){
                    recipient[0] = set(recipient[0],machines[i]-40);
                }
            }
        }else{
            for (int i = 0; i< recipient.length;i++){
                recipient[i] = (byte)0xFF;
                Log.i("Serial Message ", "Je m'adresse à toutes les machines !");
            }
        }

        Log.i("SERIAL MESSAGE : ", "ID du groupe : " + idGroupe);

        byte[] aux = new byte[11+messageContent.length];
        int size = (8 + messageContent.length);
        aux[0] = (byte)0xBA;        // Start of frame
        aux[1] = (byte)size;        // Size of data
        aux[2] = 0x00;              // Master slave = 1 sinon 0
        aux[3] = (byte) idGroupe;   // Id du groupe

        aux[4] = recipient[0];
        aux[5] = recipient[1];
        aux[6] = recipient[2];
        aux[7] = recipient[3];
        aux[8] = recipient[4];
        aux[9] = recipient[5];

        /** On met le reste des données **/
        for(int i = 0; i< messageContent.length; i++){
            aux[10+i] = messageContent[i];
        }
        aux[size +2] =(byte) 0xDA;          // End of frame
        /** Fin de la modification **/

        Log.i("COM", "Message content " + aux);
        Log.i("COM", "Message content (hexToString) " + aux.toString());
        String s1 = new String(aux);
        Log.i("COM", "Message content (string) " + s1);
        //Append 'address' at the beginning of the byte array
        // finalMessage[0] = (byte) address;
        //System.arraycopy(messageContent, 0, finalMessage, 1, SIZE - 1);

        return new SerialMessage(aux);
    }

    /**
     * Build a serial message with only the mode and the target machine
     * @param address Address
     * @param mode the mode
     * @return The serial message
     */
    static SerialMessage build(int address, int mode) {
        byte[] finalMessage = new byte[SIZE];

        //Append 'address' at the beginning of the byte array
        finalMessage[0] = (byte) address;
        finalMessage[1] = (byte) mode;
        for(int i = 2; i < SIZE; i++) {
            finalMessage[i] = 0;
        }
        return new SerialMessage(finalMessage);
    }
}
