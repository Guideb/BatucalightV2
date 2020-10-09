package fr.eseo.dis.tristan.batucadacommander.database.entities;

/**
 * Cette classe sert avoir le nom de chaque élémenet de groupeEtEffet (car il n'y a que des id )
 */
public class GroupeEtEffetNom {

    String nomBloc;
    String nomEffet;
    String nomGroupe;


    public GroupeEtEffetNom(String nomBloc, String nomEffet, String nomGroupe) {
        this.nomBloc = nomBloc;
        this.nomEffet = nomEffet;
        this.nomGroupe = nomGroupe;
    }



    public String getNomBloc() {
        return nomBloc;
    }

    public void setNomBloc(String nomBloc) {
        this.nomBloc = nomBloc;
    }

    public String getNomEffet() {
        return nomEffet;
    }

    public void setNomEffet(String nomEffet) {
        this.nomEffet = nomEffet;
    }

    public String getNomGroupe() {
        return nomGroupe;
    }

    public void setNomGroupe(String nomGroupe) {
        this.nomGroupe = nomGroupe;
    }

    @Override
    public String toString() {
        return "GroupeEtEffetNom{" +
                "nomBloc='" + nomBloc + '\'' +
                ", nomEffet='" + nomEffet + '\'' +
                ", nomGroupe='" + nomGroupe + '\'' +
                '}';
    }
}
