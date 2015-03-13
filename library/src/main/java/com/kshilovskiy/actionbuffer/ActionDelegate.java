package com.kshilovskiy.actionbuffer;

/**
 * A facade to hide some of the common buffer routines.
 */
public class ActionDelegate<T> {
    private final ActionBuffer<T> actionBuffer;

    public ActionDelegate(ActionBuffer<T> actionBuffer) {
        this.actionBuffer = actionBuffer;
    }

    /**
     * Instructs the delegate to start buffering the new actions.
     */
    public void startBuffering() {
        actionBuffer.open();
    }

    /**
     * Clears all previously buffered actions.
     */
    public void clear() {
        actionBuffer.clear();
    }

    /**
     * Sequentially extract actions from buffer and applies it
     * @param object passed to each action
     */
    public void applyAllActions(T object) {
        actionBuffer.close();
        while (!actionBuffer.isEmpty()) {
            actionBuffer.popNext().apply(object);
        }
    }

    /**
     * Applies the action depending on the state of the buffer. If buffer is closed - the action
     * will be applied immediately on the provided {@code subject}. Otherwise will be buffered to
     * be applied later via {@link #applyAllActions(Object)} method.
     */
    public void handle(T subject, Action<T> action) {
        if (actionBuffer.isOpen()) {
            actionBuffer.add(action);
        } else {
            action.apply(subject);
        }
    }
}
