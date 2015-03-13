package com.kshilovskiy.actionbuffer.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

/**
 * This Activity demonstrates how {@link com.kshilovskiy.actionbuffer.ActionDelegate} may be used
 * to avoid "java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState"
 * exception when performing a fragment transition. Note that the state of the activity is not
 * retained during the configuration changes.
 */
public class NonRetainedActivity extends FragmentActivity implements ReactiveView {
    private SimpleController mController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_retained);
        mController = new SimpleController();

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
        mController.start(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mController.stop();
    }


    /**
     * Is called by the controller to notify the background task has finished.
     */
    @Override
    public void showResult(final String result) {
        FragmentManager manager = getSupportFragmentManager();
        NotificationDialogFragment.newInstance(result).show(manager, null);
    }
}
