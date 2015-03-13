package com.kshilovskiy.actionbuffer.sample;

import android.os.AsyncTask;

import com.kshilovskiy.actionbuffer.Action;
import com.kshilovskiy.actionbuffer.ActionSingleBuffer;

/**
 * Simple view controller that utilizes {@link com.kshilovskiy.actionbuffer.ActionBuffer} to
 * collect UI actions when the view is not capable of handling them.
 */
public class SimpleController extends GenericViewController<ReactiveView> {

    /**
     * By default the controller will buffer only the last action.
     */
    public SimpleController() {
        super(new ActionSingleBuffer<ReactiveView>());
    }

    /**
     * Starts a background task that results in some result.
     */
    public void startBackgroundTask() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                handleAction(new Action<ReactiveView>() {
                    @Override
                    public void apply(ReactiveView reactiveView) {
                        reactiveView.showResult("The background task has finished");
                    }
                });

            }
        }.execute();
    }
}
