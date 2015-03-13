package com.kshilovskiy.actionbuffer.sample;

import com.kshilovskiy.actionbuffer.Action;
import com.kshilovskiy.actionbuffer.ActionBuffer;
import com.kshilovskiy.actionbuffer.ActionDelegate;

/**
 * Encapsulates some of {@link ActionBuffer} routines for collecting and applying actions.
 */
public class GenericViewController<T> {
    private T mView;
    private final ActionDelegate<T> mActionDelegate;

    public GenericViewController(ActionBuffer<T> actionBuffer) {
        mActionDelegate = new ActionDelegate<>(actionBuffer);
    }

    /**
     * Triggers all the collected actions for the provided {@code view.}
     */
    public void start(T view) {
        mView = view;
        mActionDelegate.applyAllActions(view);
    }

    /**
     * Unbinds the view provided in {@link #start(Object)} method. Also notifies the view
     * controller to start buffering actions.
     */
    public void stop() {
        mActionDelegate.startBuffering();
        mView = null;
    }


    /**
     * Either applies an {@code action} immediately if view is bound to the current controller
     * or buffers it for later emission.
     */
    protected void handleAction(Action<T> action) {
        mActionDelegate.handle(mView, action);
    }
}
