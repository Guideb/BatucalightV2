package fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Machine;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.MachineUI;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dragdrop.DropFragmentEnum;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.dragdrop.MachineEventListener;

public class RecyclerMachinesAdapter extends RecyclerView.Adapter<RecyclerMachinesAdapter.RMViewHolder> {

    private List<Machine> machines;
    private LayoutInflater mInflater;
    private MachineEventListener mEventListener;
    private DropFragmentEnum fragmentFrom;

    /**
     * Adapter containing all the groups
     * @param context The app context
     * @param main Fragment containing the recycler view
     * @param from From where it was dropped
     */
    public RecyclerMachinesAdapter(Context context, MachineEventListener main, DropFragmentEnum from) {
        this.mInflater = LayoutInflater.from(context);
        this.machines = new ArrayList<>();
        this.mEventListener = main;
        this.fragmentFrom = from;
    }

    @NonNull
    @Override
    public RMViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_machine_card, parent, false);

        return new RMViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull RMViewHolder holder, int position) {
        Machine machine = this.machines.get(position);

        holder.address.setText(String.format(Locale.FRENCH, "#%d", machine.getAdresse()));
        holder.machine = machine;
        holder.position = holder.getAdapterPosition();
        holder.state.setText(machine.getState() ? "Online" : "Offline");
        holder.state.setTextColor(machine.getState() ? BColor.GREEN : BColor.RED);

        //TODO - Implement icons
        holder.icon.setText(R.string.machine_adapter_title_machine);
    }

    @Override
    public int getItemCount() {
        return this.machines.size();
    }

    /**
     * Stores and recycles views as they are scrolled off screen
     */
    public class RMViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        MachineUI ui;
        TextView icon;
        TextView address;
        TextView state;
        Machine machine;
        int position;

        /**
         * Constructor
         * @param itemView The itemview to render
         */
        RMViewHolder(View itemView) {
            super(itemView);
            ui = new MachineUI(itemView.findViewById(R.id.cv_machine));
            icon = itemView.findViewById(R.id.machine_icon);
            address = itemView.findViewById(R.id.machine_address);
            state = itemView.findViewById(R.id.machine_state);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mEventListener.onMachineClicked(machine, position);
        }


        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("FROM", fragmentFrom.toString());
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.setTag(machine);
            v.startDrag(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    /**
     * Set the machines
     * @param machines machines
     */
    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }


}