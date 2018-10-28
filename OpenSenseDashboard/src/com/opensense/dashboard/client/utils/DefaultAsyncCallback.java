package com.opensense.dashboard.client.utils;

import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * Default implementation of an <code>AsyncCallback</code>, including default
 * error handling of asynchronous calls, if desired.
 * 
 */
public class DefaultAsyncCallback<T> implements AsyncCallback<T> {

    private static final Logger LOGGER = Logger.getLogger(DefaultAsyncCallback.class.getName());
    
    /**
     * The default behavior for a failed asynchronous call. {@link InvalidGisLicenseException}
     * leads to a license message, other causes just display a generic error message to the user.
     */
    private static final Consumer<Throwable> DEFAULT_FAILURE = caught -> {
//        AppController.showError(ServerLanguages.unexpectedErrorOccurred());
        LOGGER.log(Level.WARNING, "Unexpected error during a server call", caught);
    };

    /**
     * Specifies what happens when the asynchronous call succeeds.
     * 
     * @see #onSuccess(Object)
     */
    protected final Consumer<T> onSuccess;
    
    /**
     * Specifies what happens when the asynchronous call fails
     * (for example, because there was an exception on the server).
     */
    protected final Consumer<Throwable> onFailure;
    
    /**
     * Returns a new instance of {@link DefaultAsyncCallback} with the given
     * onSuccess and default onFailure behavior.
     * 
     * @param onSuccess
     *            - If the call succeeds, this object's
     *            {@link Consumer#accept(Object)} will be called on the result.
     * 
     * @see #DEFAULT_FAILURE
     */
    public DefaultAsyncCallback(Consumer<T> onSuccess) {
        this.onSuccess = onSuccess;
        this.onFailure = DEFAULT_FAILURE;
    }
    
    /**
     * Returns a new instance of {@link DefaultAsyncCallback} with the given
     * success and failure behavior.
     * 
     * @param onSuccess
     *            - If the call succeeds, this object's
     *            {@link Consumer#accept(Object)} will be called on the result.
     * @param onFailure
     *            - If the call fails, this object's
     *            {@link Consumer#accept(Object)} will be called on the thrown
     *            exception.
     * @param defaultFailure
     *            - If this is true, this callback will also execute default
     *            failure behavior before calling onFailure.
     *            
     * @see #DEFAULT_FAILURE
     */
    public DefaultAsyncCallback(Consumer<T> onSuccess, Consumer<Throwable> onFailure, boolean defaultFailure) {
        this.onSuccess = onSuccess;
        this.onFailure = defaultFailure ? DEFAULT_FAILURE.andThen(onFailure) : onFailure;
    }
    
    @Override
    public void onFailure(Throwable caught) {
        onFailure.accept(caught);
    }

    @Override
    public void onSuccess(T result) {
        onSuccess.accept(result);
    }
}
