package com.kshilovskiy.actionbuffer;

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * Represents a buffer with FIFO order.
 */
public class ActionQueueBuffer<T> extends ActionBuffer<T> {

    private Queue<Action<T>> mActionQueue;

    public ActionQueueBuffer() {
        mActionQueue = new ConcurrentLinkedQueue<Action<T>>();
    }

    /**
     * Returns true if the buffer doesn't contain any actions
     */
    public boolean isEmpty() {
        return mActionQueue.isEmpty();
    }

    /**
     * Adds the specified action to the end of the internal action queue
     *
     * @param action Action to add
     */
    @Override
    public void add(Action<T> action) {
        if (!isOpen()) throw new UnsupportedOperationException("Can not push to the closed buffer");
        mActionQueue.add(action);
    }

    /**
     * Removes the the first element from the internal action queue
     *
     * @return the removed action
     * @throws java.util.NoSuchElementException if the buffer is empty
     */
    @Override
    public Action<T> popNext() {
        if (mActionQueue.isEmpty())
            throw new NoSuchElementException("Can not pop from an empty buffer");

        return mActionQueue.remove();
    }

    /**
     * Removes all actions from the buffer
     */
    public void clear() {
        mActionQueue.clear();
    }

    /**
     * Removes all elements from the internal queue starting from the first one. Calls
     * {@link com.kshilovskiy.actionbuffer.Action#apply(Object)} (Object)} with {@code object}
     * for each removed action
     *
     * @param object Object to which an action should be applied
     * @return false if the buffer is initially empty.
     */
    public boolean popAll(T object) {
        if (mActionQueue.isEmpty()) return false;

        while (!mActionQueue.isEmpty()) {
            mActionQueue.remove().apply(object);
        }

        return true;
    }

}
