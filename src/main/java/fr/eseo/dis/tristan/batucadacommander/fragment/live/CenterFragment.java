package fr.eseo.dis.tristan.batucadacommander.fragment.live;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.entities.GroupeAndMachines;
import fr.eseo.dis.tristan.batucadacommander.fragment.MainFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.effets.objects.Effect;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.task.PopulateCenterGroupsTask;
import fr.eseo.dis.tristan.batucadacommander.fragment.live.ui.MachineElement;

/**
 * @author Tristan LE GACQUE
 * A simple {@link Fragment} subclass.
 * Use the {@link CenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CenterFragment extends MainFragment<CenterFragment.OnCenterFragmentInteractionListener> implements View.OnClickListener {

    private static final int GROUPS_PER_LINE = 5;

    private Map<String, MachineElement> machineElementList;
    private LinearLayout mainLayout;
    //private TextView logger;

    /**
     * The center fragment
     */
    public CenterFragment() {
        super();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CenterFragment.
     */
    public static CenterFragment newInstance() {
        return new CenterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.machineElementList = new HashMap<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_center, container, false);
        this.mainLayout = view.findViewById(R.id.right_fragment_layout);

        /*
        this.logger = view.findViewById(R.id.log_console);
        this.logger.setMovementMethod(new ScrollingMovementMethod());
        this.logger.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                logger.setText("");
                return false;
            }
        });
        */

        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Populate the fragment with all the groupes
     * This methos no require list and query the database
     */
    public void populateWithGroups() {
        new PopulateCenterGroupsTask(this).execute();
    }

    /**
     * Populate the fragment with all the groups
     * @param groupes The groups
     */
    public void populateWithGroups(List<GroupeAndMachines> groupes) {
        this.machineElementList.clear();
        this.mainLayout.removeAllViewsInLayout();

        //Define the params for buttons
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 150);
        layoutParams.weight = 20;
        layoutParams.setMargins(10, 10, 10, 10);

        //Prepare the populate method and first line
        int elementsOnLine = 0;
        LinearLayout linearLayout = this.getNewLinearLayout();
        this.mainLayout.addView(linearLayout);

        for(int i = 0; i < groupes.size(); i++) {
            //If new line, get a proper one and add to the view
            if(elementsOnLine == GROUPS_PER_LINE) {
                elementsOnLine = 0;
                linearLayout = this.getNewLinearLayout();
                this.mainLayout.addView(linearLayout);
            }

            //Get the groupe
            GroupeAndMachines groupe = groupes.get(i);

            //Define the button
            LinearLayout machineLayout = this.getMachineLayout();
            Button button = machineLayout.findViewById(R.id.machine_button);
            button.setTag("mac"+i);
            button.setOnClickListener(this);

            //Define the text view with proper height
            TextView nameView = machineLayout.findViewById(R.id.machine_name);
            nameView.setText(groupe.getGroupe().getNom());

            //Add the button to the layout
            linearLayout.addView(machineLayout, layoutParams);

            //And to the list
            this.machineElementList.put(button.getTag().toString(), new MachineElement(button, groupe.getMachines()));
            elementsOnLine++;
        }

        //Add buttons invisible to respect the layout
        for(int i = elementsOnLine; i < GROUPS_PER_LINE; i++) {
            Button invisible = new Button(this.getContext());
            invisible.setVisibility(View.INVISIBLE);
            linearLayout.addView(invisible, layoutParams);
        }

        this.mainLayout.invalidate();
    }

    /**
     * get a new just inflated Linear layout
     * @return The linear layout
     */
    private LinearLayout getNewLinearLayout() {
        return (LinearLayout) LayoutInflater.from(this.getContext()).inflate(R.layout.linear_layout_row, null);
    }

    /**
     * Get a machine layout from inflater
     * @return The machine layout
     */
    private LinearLayout getMachineLayout() {
        return (LinearLayout) LayoutInflater.from(this.getContext()).inflate(R.layout.machine_element, null);
    }


    @Override
    public void onClick(View v) {
        //Remove color of buttons
        Objects.requireNonNull(this.machineElementList.get(v.getTag().toString())).toggleSelected();
    }


    /////////////////////////////
    // MODULE LISTENERS
    /////////////////////////////


    /**
     * Triggered when other effect is selected
     * @param effect The effect
     */
    public void onEffectChange(Effect effect) {
        // Apply to selectec machines
        List<MachineElement> selectedMachines = new ArrayList<>();
        for(Map.Entry<String, MachineElement> entry : this.machineElementList.entrySet()) {
            MachineElement machine = entry.getValue();
            if(machine.isSelected()) {
                machine.setEffect(effect);
                selectedMachines.add(machine);
            }
        }
        Log.d("DEBUG", "Affichage de l'effet : " + effect.toString());
        this.getListener().onMachinesEffectChange(selectedMachines);
    }

    /**
     * Deselect all groups
     */
    public void deselectAllGroups() {
        for(MachineElement machine : this.machineElementList.values()) {
            machine.setSelected(false);
        }
    }

    /**
     * Write log in console
     * @param tag The tag
     * @param message The message to write
     */
    /*
    public void writeLog(String tag, String message) {
        this.logger.append(String.format(Locale.FRENCH, "%n[%s] %s", tag.toUpperCase().substring(0, 3), message));

        //Scroll down
        final int scrollAmount = this.logger.getLayout().getLineTop(this.logger.getLineCount()) - this.logger.getHeight();
        this.logger.scrollTo(0, scrollAmount > 0 ? scrollAmount : 0);
    }
    */

    /**
     * Return all the machines
     * @return The machines
     */
    public List<MachineElement> getMachines() {
        return new ArrayList<>(machineElementList.values());
    }

    /////////////////////////////
    // DEFINITION DE L'INTERFACE
    /////////////////////////////

    public interface OnCenterFragmentInteractionListener {

        /**
         * Triggered when machines elements changed
         * @param machines The machines
         */
        void onMachinesEffectChange(List<MachineElement> machines);
    }

}
