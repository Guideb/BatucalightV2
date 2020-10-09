package fr.eseo.dis.tristan.batucadacommander.communication.enums;

public enum ModeMessage {
    FLASH(0),
    COULEUR1(1),
    COULEURS2(2),
    STROBOSCOPE(3),
    FADE(4),
    FADE_FRAPPE(5),
    METRONOME(6),
    ARCENCIEL(7),
    ARCENCIEL_FADE(8),
    VAGUEMACHINE(9),
    VAGUEGROUPE(10),
    VAGUEMACHINE_FADE(11),
    ETEINDRE(99),
    TEST(100);



    // Valeur permettant de gérer l'index de l'enum
    private final int val;

    /**
     * Permet de créer un lien entre l'enum et une valeur
     * @param v valeur envoyé correspondant au protocole de communication
     */
    private ModeMessage(int v) {
        val = v;
    }

    /**
     * Recupere l'index de l'enum
     */
    public int getVal() {
        return val;
    }
}
