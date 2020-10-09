package fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.entities.Groupe;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.LeftFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.machine.ui.GroupeButton;

/**
 * Show the groups
 */
public class RecyclerGroupsAdapter extends RecyclerView.Adapter<RecyclerGroupsAdapter.RGViewHolder> {

    private List<Groupe> groupes;

    //Position -> Button
    private Map<Integer, GroupeButton> buttonMap;

    //Position selected
    private Integer selectedPos;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    /**
     * Adapter containing all the groups
     * @param context The app context
     * @param main The fragment that contain the rv
     */
    public RecyclerGroupsAdapter(Context context, LeftFragment main) {
        this.mInflater = LayoutInflater.from(context);
        this.groupes = new ArrayList<>();
        this.buttonMap = new HashMap<>();
        this.mClickListener = main;
        this.selectedPos = -1;
    }

    @NonNull
    @Override
    public RGViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  mInflater.inflate(R.layout.recycler_groups_row, parent, false);

        return new RGViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull RGViewHolder holder, int position) {
        Groupe groupe = this.groupes.get(position);

        String name = groupe.getNom();

        this.buttonMap.put(position, holder.button);

        //Define values of RGViewHolder
        holder.button.getButton().setText(name);
        holder.button.select(selectedPos == holder.getAdapterPosition());
        holder.groupe = groupe;
        holder.position = holder.getAdapterPosition();

    }

    @Override
    public int getItemCount() {
        return this.groupes.size();
    }

    /**
     * Set color to UI button when selected
     * @param button The button
     */
    public void selectButton(@Nullable GroupeButton button) {
        //Unselect all
        for(GroupeButton b : buttonMap.values()) {
            b.select(false);
        }

        //Select the right one
        if(button != null) {
            button.select(true);
        } else {
            this.selectedPos = -1;
        }

    }

    /**
     * Stores and recycles views as they are scrolled off screen
     */
    public class RGViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public final GroupeButton button;
        Groupe groupe;
        int position;

        /**
         * Constructor
         * @param itemView The itemview to render
         */
        RGViewHolder(View itemView) {
            super(itemView);
            button = new GroupeButton((Button) itemView.findViewById(R.id.tv_groups_nom));
            button.getButton().setOnClickListener(this);
            button.getButton().setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //Prevent fire when same groupe is selected
            if(position != selectedPos) {
                selectedPos = position;
                //Select the button
                selectButton(button);

                //Notify
                if (mClickListener != null && this.groupe != null) {
                    mClickListener.onGroupSelected(this.groupe);
                }
            }
        }

        @Override
        public boolean onLongClick(View v) {
            //Notify
            if (mClickListener != null && this.groupe != null) {
                mClickListener.onGroupLongClick(this.groupe);
                return true;
            }
            return false;
        }
    }

    /**
     * Set the groupes
     * @param groupes groups
     */
    public void setGroupes(List<Groupe> groupes) {
        this.groupes = groupes;
    }

    /**
     * Interface containing events for the fragment
     */
    public interface ItemClickListener {

        /**
         * Fired when a group is selected
         *
         * @param groupe The selected group
         */
        void onGroupSelected(Groupe groupe);

        /**
         * Fired when a groupe is long clicked
         * @param groupe The group clicked
         */
        void onGroupLongClick(Groupe groupe);
    }


}