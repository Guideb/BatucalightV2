package fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dragdrop;

public enum DropFragmentEnum {
    GROUP,
    POOL;

    /**
     * Get the drop fragment enum by name
     * @param s The name to fetch
     * @return The drop fragment | null
     */
    public static DropFragmentEnum getByName(String s) {
        for(DropFragmentEnum f : values()) {
            if(f.toString().equalsIgnoreCase(s)) {
                return f;
            }
        }
        return null;
    }


}
