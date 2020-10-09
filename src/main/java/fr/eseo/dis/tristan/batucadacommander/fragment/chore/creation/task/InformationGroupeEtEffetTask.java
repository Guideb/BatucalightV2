package fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.task;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Effet;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.RightFragment;

public class InformationGroupeEtEffetTask extends AsyncTask<Integer,Void,Void> {
    private final AppDatabase database;
    private ArrayList<String> informations;


    /**
     * Tache pour avoir les informations sur des Groupes et d'effet
     * @param fragment Le fragment
     */
    public InformationGroupeEtEffetTask(ArrayList<String> informations,RightFragment fragment){
        this.database = AppDatabase.getDatabase(fragment.getContext());
        this.informations = informations;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        Effet effet = database.effetDao().getById(integers[0]);
        BColor color1 = database.colorDao().getById(effet.getRefCouleurUne());
        BColor color2 = database.colorDao().getById(effet.getRefCouleurDeux());

        informations.add("Couleur n° 1 : Couleur  " +color1.getIdColor());
        informations.add("Couleur n° 2 : Couleur  " +color2.getIdColor());
        informations.add("Fréquence " + effet.getFrequence());
        informations.add("Durée " + effet.getDuree());

return null;
    }
}
