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
import fr.eseo.dis.tristan.batucadacommander.database.entities.Bloc;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.CenterFragment;
import fr.eseo.dis.tristan.batucadacommander.fragment.chore.creation.ui.button.BlocButton;

/**
 * Show the bloc
 */
public class RecyclerBlocAdapter extends RecyclerView.Adapter<RecyclerBlocAdapter.RGViewHolder> {

    private List<Bloc> blocs;

    //Position - > Button
    private Map<Integer, BlocButton> buttonMap;

    //Position selected
    private Integer selectedPos;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    /**
     * Adapter containing all the blocs
     * @param context The app context
      * @param centerFragment The fragment that contain the rv
     */
    public RecyclerBlocAdapter(Context context, CenterFragment centerFragment){
        this.mInflater = LayoutInflater.from(context);
        this.blocs = new ArrayList<>();
        this.buttonMap = new HashMap<>();
        this.mClickListener = centerFragment;
        this.selectedPos = -1;
    }

    @NonNull
    @Override
    public RGViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.recycler_bloc_row,viewGroup,false);

        return new RGViewHolder(view);
    }

    // binds the date of the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull RGViewHolder rgViewHolder, int position) {
        Bloc bloc = this.blocs.get(position);

        String name = bloc.getNomBloc();

        this.buttonMap.put(position,rgViewHolder.button);

        //Defines values of RGVViewHolder
        rgViewHolder.button.getButton().setText(name);
        rgViewHolder.button.select(selectedPos == rgViewHolder.getAdapterPosition());
        rgViewHolder.bloc = bloc;
        rgViewHolder.position = rgViewHolder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return this.blocs.size();
    }

    /**
     * Set color to UI button when selected
     * @param button The button
     */
    public void selectButton(@Nullable BlocButton button) {
        //Unselect all
        for(BlocButton b : buttonMap.values()) {
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

        public final BlocButton button;
        Bloc bloc;
        int position;

        RGViewHolder(View itemView){
            super(itemView);
            button = new BlocButton((Button) itemView.findViewById(R.id.tv_bloc_nom));
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
                if (mClickListener != null && this.bloc != null){
                    mClickListener.onBlocSelected(this.bloc);
                }
            }

        }

        @Override
        public boolean onLongClick(View v) {
            //Notify
            if (mClickListener != null && this.bloc != null) {
                mClickListener.onBlocLongClick(this.bloc);
                return true;
            }
            return false;
        }
    }

    /**
     * Set the blocs
     * @param blocs The blocs
     */
    public void setBlocs(List<Bloc> blocs){this.blocs = blocs;}

    /**
     * Interface containing events for the fragment
     */
    public interface ItemClickListener{

        /**
         * Fired when a bloc is selected
         *
         * @param bloc The selected chore
         */
        void onBlocSelected(Bloc bloc);

        /**
         * Fired when a bloc is long clicked
         * @param bloc The bloc cliked
         */
        void onBlocLongClick(Bloc bloc);

    }
}
