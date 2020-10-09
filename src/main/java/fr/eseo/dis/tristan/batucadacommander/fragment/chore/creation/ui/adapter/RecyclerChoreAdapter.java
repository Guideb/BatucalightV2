package fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.ui.adapter;

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
import fr.eseo.dis.tristan.batucadacommander.database.entities.Chore;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.LeftFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.ui.button.ChoreButton;

/**
 *  Show the chores
 */
public class RecyclerChoreAdapter extends RecyclerView.Adapter<RecyclerChoreAdapter.RGViewHolder> {

    private List<Chore> chores;

    //Position -> Button
    private Map<Integer, ChoreButton> buttonMap;

    //Position selected
    private Integer selectedPos;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    /**
     * Adapter containing all the chores
     * @param context The app context
     * @param leftFragment The fragment that contain the rv
     */
    public RecyclerChoreAdapter(Context context, LeftFragment leftFragment){
        this.mInflater = LayoutInflater.from(context);
        this.chores = new ArrayList<>();
        this.buttonMap = new HashMap<>();
        this.mClickListener = leftFragment;
        this.selectedPos = -1;
    }

    @NonNull
    @Override
    public RGViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = mInflater.inflate(R.layout.recycler_chore_row,parent, false);

        return new RGViewHolder(view);
    }

    // binds the date of the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull RGViewHolder holder, int position) {
        Chore chore = this.chores.get(position);

        String name = chore.getNomChore();

        this.buttonMap.put(position,holder.button);

        //Defines values of RGVViewHolder
        holder.button.getButton().setText(name);
        holder.button.select(selectedPos == holder.getAdapterPosition());
        holder.chore = chore;
        holder.position = holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return this.chores.size();
    }

    /**
     * Set color to UI button when selected
     * @param button The button
     */
    public void selectButton(@Nullable ChoreButton button) {
        //Unselect all
        for(ChoreButton b : buttonMap.values()) {
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
        public final ChoreButton button;
        Chore chore;
        int position;

        /**
         * Constructor
         * @param itemView The itemview to render
         */
        RGViewHolder(View itemView) {
            super(itemView);
            button = new ChoreButton((Button) itemView.findViewById(R.id.tv_chore_nom));
            button.getButton().setOnClickListener(this);
            button.getButton().setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //Prevent fire when same chore is selected
            if(position != selectedPos) {
                selectedPos = position;
                //Select the button
                selectButton(button);

                //Notify
                if (mClickListener != null && this.chore != null) {
                    mClickListener.onChoreSelected(this.chore);
                }
            }
        }

        @Override
        public boolean onLongClick(View v) {
            //Notify
            if (mClickListener != null && this.chore != null) {
                mClickListener.onChoreLongClick(this.chore);
                return true;
            }
            return false;
        }
    }

    /**
     * Set the chores
     * @param chores the chores
     */
    public void setChores(List<Chore> chores){this.chores = chores;}

    /**
     * Interface containing events for the fragment
     */
    public interface ItemClickListener {

        /**
         * Fired when a chore is selected
         *
         * @param chore The selected chore
         */
        void onChoreSelected(Chore chore);

        /**
         * Fired when a chore is long clicked
         * @param chore The chore clicked
         */
        void onChoreLongClick(Chore chore);
    }
}
