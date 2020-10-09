package fr.eseo.dis.tristan.batucadacommander.communication;

import android.util.Log;

import fr.eseo.dis.tristan.batucadacommander.communication.enums.ModeMessage;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.Effect;

public class Protocol {

    /**
     * EXPLICATION DU PROTOCOLE DE COMMUNICATION
     *
     * Cette section a pour but d'expliquer brievement le protocole de communication
     * J'invite le développeur à lire le document présent sur le Drive nommé : ProtocolComV1
     *
     *
     *
     * Construction du message
     *
     * Préfixe UART
     *      Début du message : BA
     *      Taille du message ESB : taille de la partie Préfixe ESB et Données
     *
     * Préfixe ESB
     *      Identifiant du maitre : 0 sinon en mode Master/Slave
     *      Identifiant du groupe : à récuperer grâce à la machine
     *      Identifiant des esclaves : voir ci-dessous
     *      Identifiant du message : identifiant de l'effet rédiger dans l'énumération ModelMessage
     *      Taille de la donnée : taille de la partie décrite dans la section Données
     *
     * Données
     *      Données : dépend en fonction de l'effet souhaité, cf doc
     *
     * Suffixe UART
     *      Fin du message : DA
     *
     * Le message envoyé se compose de la manière suivante:
     *      Préfixe UART + Préfixe ESB + Données + Suffixe UART
     *      Soit de manière plus détaillé
     *      DébutMessage + TailleESB + IdMaitre + IdGroupe + IdEsclave + IdMessage + TailleDonnées + Données + FinMessage
     *
     * Exemple : BA 0D 00 00 FF FF FF FF FF FF 01 03 7F 00 00 DA
     *
     *
     *
     * Explication pour la désignation de l'identifiant esclave
     * TODO
     */


    static public byte[] creationEffect(int effect, int r1, int v1, int b1, int p, int d, int r2, int v2, int b2, int trigger){
        byte[] answer = new byte[13];
        byte[] data = new byte[11];

        data[0] = (byte) r1;
        data[1] = (byte) v1;
        data[2] = (byte) b1;
        data[3] = (byte) 0x00;
        data[4] = (byte) p;
        data[5] = (byte) 0x00;
        data[6] = (byte) 500;
        data[7] = (byte) r2;
        data[8] = (byte) v2;
        data[9] = (byte) b2;
        data[10] = (byte) trigger;

        int size = data.length;

        answer[0] = (byte) effect;
        answer[1] = (byte) size;

        Log.i("Protocol :", "id effet : " + effect);

        for (int i = 0; i < data.length; i++) {
            answer[2+i] = data[i];
        }

        return answer;
    }


    /**
     * Création de la partie donnée du mode Stroboscope
     * @param r code rouge
     * @param v code vert
     * @param b code bleu
     * @param p période
     * @return identifiant du message + taille des données + données
     */
    static public byte[] createStrob(int r, int v, int b, int p, int d){
        byte[] answer = new byte[9];
        byte[] data = new byte[7];

        data[0] = (byte) r;
        data[1] = (byte) v;
        data[2] = (byte) b;
        data[3] = (byte) 0x00;
        data[4] = (byte) p;
        data[5] = (byte) 0x00;
        data[6] = (byte) 100;

        int size = data.length;

        answer[0] = (byte) ModeMessage.STROBOSCOPE.getVal();
        answer[1] = (byte) size;
        for (int i = 0; i < data.length; i++) {
            answer[2+i] = data[i];
        }

        return answer;
    }

    /**
     * Création de la partie données du mode eteindre
     * @return identifiant du message + taille des données + données
     */
    static public byte[] createEteindre(){
        byte[] answer = new byte[2];
        answer[0] = (byte) ModeMessage.ETEINDRE.getVal();
        answer[1] = (byte) 0;
        return answer;
    }

    /**
     * Création de la partie donnée du mode test
     * @return identifiant du message + taille des données + données
     */
    static public byte[] createTest(){
        byte[] answer = new byte[2];
        answer[0] = (byte) ModeMessage.TEST.getVal();
        answer[1] = (byte) 0;
        return answer;
    }
}
