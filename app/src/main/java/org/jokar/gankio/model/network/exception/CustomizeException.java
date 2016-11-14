package org.jokar.gankio.model.network.exception;

/**
 * Created by JokAr on 2016/11/14.
 */

public class CustomizeException extends RuntimeException{

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public CustomizeException(String message) {
        super(message);
    }
}
