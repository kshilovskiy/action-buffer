package com.kshilovskiy.actionbuffer.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

/**
 * This activity demonstrates how to retain actions during configuration changes.
 */
public class RetainedActivity extends FragmentActivity implements ReactiveView {
    private static final String DATA_RETAINER_TAG = "data_holder";

    private SimpleController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retained);

        //Instead of creating it here the controller may be injected
        SimpleController defaultController = new SimpleController();

        //Data holder keeps the first controller instance in order to retain it during
        // configuration changes.
        GenericDataHolder<SimpleController> dataRetainer =
                GenericDataHolder.getDataRetainer(this, DATA_RETAINER_TAG, defaultController);

        mController = dataRetainer.getValue();

        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.startBackgroundTask();
            }
        });
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        //All the pending actions will be applied at this point
        mController.start(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //This notifies the controller that the view is not capable of processing any further UI
        // actions. Controller must buffer the actions, so they can be applied later.
        mController.stop();
    }

    @Override
    public void showResult(final String result) {
        FragmentManager manager = getSupportFragmentManager();
        NotificationDialogFragment.newInstance(result).show(manager, null);
    }
}
