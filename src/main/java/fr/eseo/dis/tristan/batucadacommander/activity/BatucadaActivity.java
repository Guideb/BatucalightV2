package fr.eseo.dis.tristan.batucadacommander.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import fr.eseo.dis.tristan.batucadacommander.R;
import fr.eseo.dis.tristan.batucadacommander.communication.CommunicationManager;
import fr.eseo.dis.tristan.batucadacommander.communication.enums.SerialInitResult;
import fr.eseo.dis.tristan.batucadacommander.dialog.ChangeModeDialog;
import fr.eseo.dis.tristan.batucadacommander.dialog.SerialInitErrorDialog;

/**
 * @author Tristan LE GACQUE
 */
public abstract class BatucadaActivity extends AppCompatActivity {
    //TODO - Turn to 'TRUE' ONLY for demo purpose
    public static final boolean DISABLE_SERIAL = true;

    private boolean toolbarConfigured = false;
    private Toolbar toolbar;
    private String modeName;
    private boolean warnBeforeLeave;
    private CommunicationManager communicationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.modeName = "INCONNU";
        this.warnBeforeLeave = false;
        this.communicationManager = new CommunicationManager(this);

        this.initCommunicationManager();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        this.configureToolbar();
    }

    /**
     * Configure the toolbar
     */
    private void configureToolbar() {
        if(!this.toolbarConfigured) {
            Log.d("TOOLBAR", "Configuration de la toolbar");
            ViewGroup group = (ViewGroup)
                    ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);

            //Get the toolbar view inside the activity layout
            this.toolbar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.toolbar, null);
            this.toolbar.setTitle("");

            group.addView(toolbar, 0);
            this.setSupportActionBar(toolbar);

            this.toolbarConfigured = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate our menus
        this.getMenuInflater().inflate(R.menu.menu_machine, menu);

        //Change icons color
        if(LiveActivity.class.getSimpleName().equalsIgnoreCase(this.getClass().getSimpleName())) {
            menu.getItem(0).setIcon(getResources()
                    .getDrawable(R.drawable.ic_wb_incandescent_color_24dp));
        } else if(MachineActivity.class.getSimpleName().equalsIgnoreCase(this.getClass().getSimpleName())) {
            menu.getItem(1).setIcon(getResources()
                    .getDrawable(R.drawable.ic_settings_cell_color_24dp));
        } else if(ColorPreferenceActivity.class.getSimpleName().equalsIgnoreCase(this.getClass().getSimpleName())) {
            menu.getItem(2).setIcon(getResources()
                    .getDrawable(R.drawable.ic_palette_color_24dp));
        } else if(ChoreCreationActivity.class.getSimpleName().equalsIgnoreCase(this.getClass().getSimpleName())) {
            menu.getItem(3).setIcon(getResources()
                    .getDrawable(R.drawable.ic_create_24px));
        } else if(ChoreLiveActivity.class.getSimpleName().equalsIgnoreCase(this.getClass().getSimpleName())){
            menu.getItem(4).setIcon(getResources()
                    .getDrawable(R.drawable.ic_audio));
        }
        return true;
    }

    /**
     * Change activity to MACHINE ACTIVITY
     * @param item The menu
     */
    public void onMenuMachine(MenuItem item) {
        this.goToActivity(MachineActivity.class);
    }

    /**
     * Change activity to LIVE ACTIVITY
     * @param item The menu
     */
    public void onMenuLive(MenuItem item) {
        this.goToActivity(LiveActivity.class);
    }

    /**
     * Change activity to LIVE ACTIVITY
     * @param item The menu
     */
    public void onMenuColor(MenuItem item) {
        this.goToActivity(ColorPreferenceActivity.class);
    }

    /**
     * Change the activity to CREATION CHORE ACTIVITY
     * @param item The menu
     */
    public void onMenuChoreCreation(MenuItem item) {
        this.goToActivity(ChoreCreationActivity.class);
    }

    /**
     * Change the activity to LIVE CHORE ACTIVITY
     * @param item The menu
     */
    public void onMenuChoreLive(MenuItem item) {
        this.goToActivity(ChoreLiveActivity.class);
    }

    /**
     * Change activity to the one passed in parameter
     * @param activity The activity to go to
     */
    private void goToActivity(Class<? extends Activity> activity) {
        if(!activity.getSimpleName().equalsIgnoreCase(this.getClass().getSimpleName())) {
            if(this.isWarnBeforeLeave()) {
                new ChangeModeDialog(this, activity).show();
            } else {
                Intent intent = new Intent(this, activity);
                startActivity(intent);
            }
        }
    }

    /**
     * Set the resID for toolbar Title
     * @param resID The string res id
     */
    public void setToolbarTitle(int resID) {
        this.toolbar.setTitle(resID);
    }

    /**
     * Set the mode name
     * @param modeName The mode name
     */
    void setModeName(String modeName) {
        this.modeName = modeName;
    }

    /**
     * Set if warning is show before leaving
     * @param warnBeforeLeave true if need, false otherwise
     */
    void setWarnBeforeLeave(boolean warnBeforeLeave) {
        this.warnBeforeLeave = warnBeforeLeave;
    }

    /**
     * Get the name of the mode
     * @return The mode name
     */
    public String getModeName() {
        return modeName;
    }

    /**
     * Check if require user to confirm leaving
     * @return true if need it, else otherwise
     */
    public boolean isWarnBeforeLeave() {
        return warnBeforeLeave;
    }

    /**
     * Get the communication manager
     * @return The communication manager
     */
    public CommunicationManager getCommunicationManager() {
        return communicationManager;
    }

    /**
     * Start initialization of communication manager
     */
    public void initCommunicationManager() {
        SerialInitResult result = this.getCommunicationManager().initSerial(this);

        if(SerialInitResult.ERROR_NO_DEVICE.equals(result)) {
            new SerialInitErrorDialog(this).show();
        }
    }

}
