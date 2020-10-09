package fr.eseo.dis.tristan.batucadacommander.fragment.color.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.database.entities.BColor;
import fr.eseo.dis.tristan.batucadacommander.fragment.color.dialog.ChangeColorDialog;
import fr.eseo.dis.tristan.batucadacommander.fragment.color.task.UpdateColorTask;

/**
 * Show the groups
 */
public class RecyclerColorAdapter extends RecyclerView.Adapter<RecyclerColorAdapter.RCViewHolder> {

    private List<BColor> colors;
    private LayoutInflater mInflater;
    private Context context;

    /**
     * Adapter containing all the colors
     * @param context The app context
     */
    public RecyclerColorAdapter(Context context) {
        this.colors = new ArrayList<>();
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public RCViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  mInflater.inflate(R.layout.recycler_color_row, parent, false);

        return new RCViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull RCViewHolder holder, int position) {
        BColor color = this.colors.get(position);

        //Define values of RCViewHolder
        holder.bcolor = color;
        holder.nameView.setText(context.getString(R.string.color_name_label, holder.getAdapterPosition() + 1));
        holder.colorPreview.setColorFilter(color.getColor());
    }

    @Override
    public int getItemCount() {
        return this.colors.size();
    }


    /**
     * Stores and recycles views as they are scrolled off screen
     */
    public class RCViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        BColor bcolor;
        TextView nameView;
        ImageView colorPreview;

        /**
         * Constructor
         * @param itemView The itemview to render
         */
        RCViewHolder(View itemView) {
            super(itemView);
            this.nameView = itemView.findViewById(R.id.color_name);
            this.colorPreview = itemView.findViewById(R.id.color_preview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            new ChangeColorDialog(context, bcolor,  new ChangeColorDialog.OnColorChangeListener() {
                @Override
                public void onColorPicked(BColor color) {
                    //Update the color
                    bcolor.setRed(color.getRed());
                    bcolor.setGreen(color.getGreen());
                    bcolor.setBlue(color.getBlue());
                    new UpdateColorTask(context).execute(bcolor);

                    //Update visual preview
                    colorPreview.setColorFilter(bcolor.getColor());
                }
            }).show();
        }

    }

    /**
     * Set the colors
     * @param colors colors
     */
    public void setColors(List<BColor> colors) {
        this.colors = colors;
    }


}