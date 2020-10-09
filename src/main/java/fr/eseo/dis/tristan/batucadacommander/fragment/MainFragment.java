package fr.eseo.dis.tristan.batucadacommander.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.lang.reflect.ParameterizedType;

/**
 * @author Tristan LE GACQUE
 * Created 19/10/2018
 */
public abstract class MainFragment<T> extends Fragment {

    private T listener;

    /**
     * The main fragment, such as RIGHT, CENTER, and RIGHT fragments
     */
    public MainFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("BATUCADA_COMMANDER" , "Configuration du fragment principal : " + this.getClass().getSimpleName());

        Class<T> type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        if (type.isInstance(context)) {
            this.listener = (T) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement " + type.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    /**
     * Get the listener of the fragment
     *
     * @return A fragment that listen this effect
     */
    public T getListener() {
        return listener;
    }

}
