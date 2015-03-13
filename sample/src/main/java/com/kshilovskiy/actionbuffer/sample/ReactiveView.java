package com.kshilovskiy.actionbuffer.sample;

/**
 * Sample interface describing a view(e.g. Activity or Fragment) that should react on some result
 * of the background task.
 */
public interface ReactiveView {
    void showResult(String result);
}
