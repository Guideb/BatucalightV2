package fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.OnEffectInteraction;

/**
 * @author Tristan LE GACQUE
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnEffectInteraction} interface
 * to handle interaction events.
 * Use the {@link EffectChoregraphieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EffectChoregraphieFragment extends EffectFragmentContainer<OnEffectInteraction> {

    private static final String EFFET_GROUPES = "effetnbgroupes";

    private int nbGroupes;

    /**
     * Fragment to set color for colorEffect
     * Contains one color palette
     * And one panel to select the position of color
     */
    public EffectChoregraphieFragment() {
        super();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param nbGroupes number of groups needed for the specific effect
     * @return A new instance of fragment
     */
    public static EffectChoregraphieFragment newInstance(int nbGroupes) {
        EffectChoregraphieFragment fragment = new EffectChoregraphieFragment();
        Bundle args = new Bundle();
        args.putInt(EFFET_GROUPES, nbGroupes);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.nbGroupes = getArguments().getInt(EFFET_GROUPES);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.module_chore, container, false);

        for(int i = 0; i < this.nbGroupes; i++) {
            Button button = new Button(this.getContext());
            button.setTag(i);
            button.setText("Définir le groupe " + (i+1));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonPressed((Button) v);
                }
            });

            ((LinearLayout) view).addView(button);
        }

        return view;
    }

    /**
     * Appelé lorsqu'un bouton est pressé via l'event onClick()
     * @param button Bouton qui est press
     */
    public void onButtonPressed(Button button) {
        //IDEE - Récupérer les machines selectionnées, et afficher un numéro
    }

    @Override
    void configureModules() {
        //DO nothing
    }

    //////////////////////////////////////////
    // ECOUTE DES MODULES
    //////////////////////////////////////////


}
