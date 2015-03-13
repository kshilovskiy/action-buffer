package com.kshilovskiy.actionbuffer;

/**
 * Task that takes an argument in order to execute action on it.
 */
public interface Action<T> {
    void apply(T t);
}
