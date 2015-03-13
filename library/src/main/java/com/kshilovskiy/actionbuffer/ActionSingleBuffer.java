package com.kshilovskiy.actionbuffer;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Represents a buffer with a single item.
 */
public class ActionSingleBuffer<T> extends ActionBuffer<T> {
    private final AtomicReference<Action<T>> action = new AtomicReference<Action<T>>();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return action.get() == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Action<T> actionToAdd) {
        action.set(actionToAdd);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Action<T> popNext() {
        Action<T> localAction = action.getAndSet(null);
        if (localAction == null) {
            throw new NoSuchElementException("Can not pop from an empty buffer");
        }

        return localAction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        action.set(null);
    }
}
