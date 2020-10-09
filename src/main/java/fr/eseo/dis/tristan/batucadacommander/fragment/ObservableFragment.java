package fr.eseo.dis.tristan.batucadacommander.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.lang.reflect.ParameterizedType;

/**
 * @author Tristan LE GACQUE
 * Created 19/10/2018
 */
public abstract class ObservableFragment<T> extends Fragment {

    private T listener;

    /**
     * COnstructor for observable fragment, ones that can be observed
     */
    public ObservableFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.d("BATUCADA_COMMANDER" , "Configuration de l'observable : " + this.getClass().getSimpleName());

        Class<T> type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        // Cancel if void passed as generic
        if(type.equals(Void.class)) {
            return;
        }

        // check if parent Fragment implements listener
        if (type.isInstance(getParentFragment())) {
            this.listener = (T) getParentFragment();
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
