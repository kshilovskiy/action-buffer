package com.kshilovskiy.actionbuffer.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Allows to retain the state during the configuration changes.
 */
public class GenericDataHolder<T> {
    T mValue;

    private GenericDataHolder(T defaultValue) {
        this.mValue = defaultValue;
    }

    public T getValue() {
        return mValue;
    }

    /**
     * Returns either the {@code defaultValue} if there was no state retained previously or the
     * retained state.
     */
    public static <T> GenericDataHolder<T> getDataRetainer(FragmentActivity activity,
                                                             String tag,
                                                             T defaultValue) {
        DataFragment<T> dataFragment = addOrGetDataFragment(activity, tag, defaultValue);
        T data = dataFragment.getData();

        return new GenericDataHolder<T>(data);
    }

    private static <T> DataFragment<T> addOrGetDataFragment(FragmentActivity activity,
                                                            String tag,
                                                            T defaultData) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        DataFragment<T> fragment = (DataFragment<T>) fragmentManager.findFragmentByTag(tag);

        if (fragment == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            fragment = new DataFragment<T>();
            fragment.setData(defaultData);
            transaction.add(fragment, tag);
            transaction.commit();
        }

        return fragment;
    }

    /**
     * Used to retain state in the activity. Should not be called directly.
     * @param <T>
     */
    public static final class DataFragment<T> extends Fragment {
        T mData;

        private T getData() {
            return mData;
        }

        private void setData(T data) {
            mData = data;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }
    }
}
