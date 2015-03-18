# action-buffer
This repository provides a way of avoiding  _"java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState"_  when dealing with fragment transitoins and background tasks.
  You can read more about the problem and possible solutions in the very definitive
[blog post](http://www.androiddesignpatterns.com/2013/08/fragment-transaction-commit-state-loss.html)
by [Alex Lockwood](https://plus.google.com/+AlexLockwood/posts).

## Idea
In order to avoid the exception the transition should not be made when Activity is in the non visible(active) state.
This can be achieved by simply buffering the actions sent to the UI until the UI is capable of handling them.

##Usage
So the use case implies some simple controller that is instructed to do some work in the background and needs
to report the result once it is completed.
First you will need some interface for the controller to tell the view when the job is done.
```java
public interface ReactiveView {
    void showResult(String result);
}
```

Now the controller itself
```java
public class SimpleController {
    private ReactiveView mView;
    private final ActionDelegate<ReactiveView> mActionDelegate;

    public SimpleController(ActionBuffer<ReactiveView> actionBuffer) {
        mActionDelegate = new ActionDelegate<>(actionBuffer);
    }

    /**
     * Triggers all the collected actions for the provided {@code view.}
     * and stops buffering further actions.
     */
    public void start(ReactiveView view) {
        mView = view;
        mActionDelegate.applyAllActions(view);
    }

    /**
     * Unbinds the view provided in {@link #start(Object)} method. Also instructs
     * the delegegate to start buffering actions.
     */
    public void stop() {
        mActionDelegate.startBuffering();
        mView = null;
    }
    
    /**
    * Starts the background task and reports result on the main thread using
    * handleAction(Action<ReactiveView> action) method.
    */
    public void startBackgroundTask() {
      //... asynchronous background work happens here
    }

    /**
     * Either applies an {@code action} immediately if view is bound to the current controller
     * or buffers it for later emission.
     */
    private void handleAction(Action<ReactiveView> action) {
        mActionDelegate.handle(mView, action);
    }
}
```

And the activity that triggers the controller.
```java
public class MyActivity extends FragmentActivity implements ReactiveView {
    private SimpleController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        //This will retain only the last action in the buffer when activity is not in the 
        //visible state
        mController = new SimpleController(new ActionSingleBuffer<ReactiveView>());

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
```

It is important to call `SimpleController#stop()` method in order to avoid leaking of the activity.

##Contents
The **library** module provides basic classes for gathering anf emitting generic actions.
It doesn't have any Android dependencies.<br />
The **sample** module goes a bit further than the example shown above and also provides the generic view controller
and a class to retain the activity state during configuration changes.

##RxJava
This approach is not targeted to compete with RxJava. The main goal is to show how the UI actions
may be deferred without
bringing in a new large framework.
If you are using RxJava it may make more sense to do it with `cache()` or `replay()` observable operators as described
[here](https://github.com/ReactiveX/RxJava/wiki/The-RxJava-Android-Module#fragment-and-activity-life-cycle).

##Gradle
The *library* module describes the approach of dealing with the deferred UI actions rather then
new Android related functionality.
In my opinion it is to small to be added to the maven central repository. <br />
If you find it useful and would like to add more functionality, send me a note.
