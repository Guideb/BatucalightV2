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
import fr.eseo.dis.tristan.batucadacommander.database.entities.GroupeEtEffet;
import fr.eseo.dis.tristan.batucadacommander.database.entities.GroupeEtEffetNom;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.RightFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.ui.button.GroupeEtEffetButton;

/**
 * Montre le groupe et l'effet
 */
public class RecyclerGroupeEtEffetAdapter extends RecyclerView.Adapter<RecyclerGroupeEtEffetAdapter.RGViewHolder> {

    private List<GroupeEtEffet> groupeEtEffets;
    private List<GroupeEtEffetNom> groupeEtEffetNames;

    //Position - > Button
    private Map<Integer, GroupeEtEffetButton> buttonMap;

    //Position selected
    private Integer selectedPos;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;


    /**
     * Adapter containing all the groupsEtEffets
     * @param context The app context
     * @param rightFragment The fragment that contain the rv
     */
    public RecyclerGroupeEtEffetAdapter(Context context, RightFragment rightFragment){
        this.mInflater = LayoutInflater.from(context);
        this.groupeEtEffets = new ArrayList<>();
        this.buttonMap = new HashMap<>();
        this.mClickListener = rightFragment;
        this.selectedPos = -1;
    }

    @NonNull
    @Override
    public RGViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.recycler_groupeeteffet_row,viewGroup,false);

        return new RGViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RGViewHolder rgViewHolder, int position) {
        GroupeEtEffet groupeEtEffet = this.groupeEtEffets.get(position);
        GroupeEtEffetNom groupeEtEffetNom = this.groupeEtEffetNames.get(position);

        //On donne le nom du groupe et de l'effet pour le button
        String nameGroup = groupeEtEffetNom.getNomGroupe();
        String nameEffet = groupeEtEffetNom.getNomEffet();

        String name = nameGroup + " | " + nameEffet;


        this.buttonMap.put(position,rgViewHolder.button);

        //Defines values of RGVViewHolder
        rgViewHolder.button.getButton().setText(name);
        rgViewHolder.button.select(selectedPos == rgViewHolder.getAdapterPosition());
        rgViewHolder.groupeEtEffet = groupeEtEffet;
        rgViewHolder.position = rgViewHolder.getAdapterPosition();

    }

    @Override
    public int getItemCount() {
        return this.groupeEtEffets.size();
    }

    /**
     * Set color to UI button when selected
     * @param button The button
     */
    public void selectButton(@Nullable GroupeEtEffetButton button) {
        //Unselect all
        for(GroupeEtEffetButton b : buttonMap.values()) {
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
    public class RGViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public final GroupeEtEffetButton button;
        GroupeEtEffet groupeEtEffet;
        int position;

        RGViewHolder(View itemView){
            super(itemView);
            button = new GroupeEtEffetButton((Button) itemView.findViewById(R.id.tv_groupEtEffet_nom));
            button.getButton().setOnClickListener(this);
            button.getButton().setOnLongClickListener(this);
        }
        @Override
        public void onClick(View v) {
            //Prevent fire when same bloc is selected
            if(position != selectedPos){
                selectedPos = position;
                //Select the button
                selectButton(button);

                //Notify
                if (mClickListener != null && this.groupeEtEffet != null){
                    mClickListener.onGroupeEtEffetSelected(this.groupeEtEffet);
                }
            }

        }

        @Override
        public boolean onLongClick(View v) {
            //Notify
            if (mClickListener != null && this.groupeEtEffet != null) {
                mClickListener.onGroupeEtEffetLongClick(this.groupeEtEffet);
                return true;
            }
            return false;
        }
    }
    /**
     * Set the groupeEtEffets
     * @param groupeEtEffets The groupeEtEffets
     */
    public void setGroupeEtEffet(List<GroupeEtEffet> groupeEtEffets){this.groupeEtEffets = groupeEtEffets;}

    /**
     * Set the groupeEtEffets Name
     * @param groupeEtEffetName The groupeEtEffets Name
     */
    public void setGroupeEtEffetName(List<GroupeEtEffetNom> groupeEtEffetName){this.groupeEtEffetNames = groupeEtEffetName;}


    /**
     * Interface containing events for the fragment
     */
    public interface ItemClickListener{

        /**
         * Fired when a groupeEtEffet is selected
         *
         * @param groupeEtEffet The selected chore
         */
        void onGroupeEtEffetSelected(GroupeEtEffet groupeEtEffet);

        /**
         * Fired when a groupeEtEffet is long clicked
         * @param groupeEtEffet The groupeEtEffet cliked
         */
        void onGroupeEtEffetLongClick(GroupeEtEffet groupeEtEffet);

    }
}
