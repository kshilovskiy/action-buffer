package com.kshilovskiy.actionbuffer;

/**
 * Represents an action buffer. Provides methods to add and extract actions from the buffer.
 */
 public abstract class ActionBuffer<T> {
    private boolean mIsOpen = false;
    /**
     * Sets the buffer to open state. So that new actions can be pushed into it.
     * Note that the buffer is closed by default.
     */
    public void open() {
        mIsOpen = true;
    }

    /**
     * Sets the buffer to the closed state. Any subsequent call to
     * {@link #add(Action)} (com.kshilovskiy.actionbuffer.Action)}
     * will result into {@link UnsupportedOperationException}
     */
    public void close() {
        mIsOpen = false;
    }

    /**
     * Returns true if the buffer is capable of receiving new actions
     */
    public boolean isOpen() {
        return mIsOpen;
    }
    /**
     * Returns true if the buffer doesn't contain any actions
     */
     public abstract boolean isEmpty();

    /**
     * Adds the specified action to the end of the internal action queue
     *
     * @param action Action to add
     */
    public abstract void add(Action<T> action);

    /**
     * Returns the popNext removed {@link Action} from the buffer.
     *
     * @return the removed action
     * @throws java.util.NoSuchElementException if the buffer is empty
     */
    public abstract Action<T> popNext();

    /**
     * Removes all actions from the buffer
     */
    public abstract void clear();

}
