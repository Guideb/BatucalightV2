package fr.eseo.dis.tristan.batucadacommander.fragment.live.modules;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.AppDatabase;
import fr.eseo.dis.tristan.batucadacommander.database.dao.BColorDao;
import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;
import fr.eseo.dis.tristan.batucadacommander.fragment.ObservableFragment;

/**
 * @author Tristan LE GACQUE
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PaletteModuleFragment.OnPaletteInteraction} interface
 * to handle interaction events.
 * Use the {@link PaletteModuleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaletteModuleFragment extends ObservableFragment<PaletteModuleFragment.OnPaletteInteraction> implements View.OnClickListener{

    private List<BColor> couleurList;
    private Map<Integer, Button> buttons;

    /**
     * Fragment for color palette
     */
    public PaletteModuleFragment() {
        super();
    }

    /**
     * Get a new instance of color palette
     * @return A paletteModuleFragment
     */
    public static PaletteModuleFragment newInstance() {
        return new PaletteModuleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.couleurList = new ArrayList<>();
        this.buttons = new HashMap<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.module_palette, container, false);

        // Set button listener
        this.buttons.put(0, (Button) view.findViewById(R.id.color_button_1));
        this.buttons.put(1, (Button) view.findViewById(R.id.color_button_2));
        this.buttons.put(2, (Button) view.findViewById(R.id.color_button_3));
        this.buttons.put(3, (Button) view.findViewById(R.id.color_button_4));
        this.buttons.put(4, (Button) view.findViewById(R.id.color_button_5));
        this.buttons.put(5, (Button) view.findViewById(R.id.color_button_6));
        this.buttons.put(6, (Button) view.findViewById(R.id.color_button_7));
        this.buttons.put(7, (Button) view.findViewById(R.id.color_button_8));
        this.buttons.put(8, (Button) view.findViewById(R.id.color_button_9));

        for(Button button : this.buttons.values()) {
            button.setOnClickListener(this);
        }

        // Récupération des données couleurs en BDD
        new GetCouleursTask(AppDatabase.getDatabase(this.getContext()).colorDao(), this).execute();

        return view;
    }

    /**
     * Affiche les couleurs sur la palette
     */
    public void populateColorPalette() {
        // Définir les paramètres
        for(int i = 0; i < this.couleurList.size() && i < 9; i++) {
            BColor couleur = this.couleurList.get(i);

            Button button = this.buttons.get(i);

            if(button != null) {
                button.setTag(couleur);

                button.setBackgroundColor(couleur.getColor());
                button.setOnClickListener(this);
            }
        }
    }

    /**
     * Appelé lorsqu'un bouton est pressé via l'event onClick()
     * @param button Bouton qui est press
     */
    public void onButtonPressed(Button button) {
        if (this.getListener() != null) {
            this.getListener().onChangeColor((BColor) button.getTag());
        }
    }

    @Override
    public void onClick(View v) {
        if(v instanceof Button) {
            onButtonPressed((Button)v);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnPaletteInteraction {
        /**
         * Called when color is pressed
         * @param couleur color
         */
        void onChangeColor(BColor couleur);

    }

    private static class GetCouleursTask extends AsyncTask<Void, Void, List<BColor>> {

        private BColorDao couleurDao;
        private WeakReference<PaletteModuleFragment> paletteFragmentWeakReference;

        /**
         * Retrieve colors from database
         * @param dao The DAO
         * @param paletteFragment The fragment to populate
         */
        GetCouleursTask(BColorDao dao, PaletteModuleFragment paletteFragment) {
            this.couleurDao = dao;
            this.paletteFragmentWeakReference = new WeakReference<>(paletteFragment);
        }

        @Override
        protected List<BColor> doInBackground(final Void... params) {
            return this.couleurDao.getAllColors();
        }

        @Override
        protected void onPostExecute(List<BColor> couleurs) {
            PaletteModuleFragment paletteFragment = this.paletteFragmentWeakReference.get();
            paletteFragment.couleurList = couleurs;
            paletteFragment.populateColorPalette();
        }
    }

}
