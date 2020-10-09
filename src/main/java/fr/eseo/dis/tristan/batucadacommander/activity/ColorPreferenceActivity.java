package fr.eseo.dis.tristan.batucadacommander.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.concurrent.ExecutionException;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.fragment.color.task.GetAllColorsTask;
import fr.eseo.dis.tristan.batucadacommander.fragment.color.ui.adapter.RecyclerColorAdapter;

public class ColorPreferenceActivity extends BatucadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);
        setToolbarTitle(R.string.param_colors);
        setModeName("COLORS");

        //Populate the recycler view
        RecyclerView recyclerView = this.findViewById(R.id.recycler_colors);
        RecyclerColorAdapter adapter = new RecyclerColorAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayout.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //Populate the adapter
        try {
            adapter.setColors(new GetAllColorsTask(this).execute().get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
