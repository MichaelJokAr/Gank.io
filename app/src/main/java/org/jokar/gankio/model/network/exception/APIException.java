package org.jokar.gankio.model.network.exception;

/**
 * Created by JokAr on 16/9/17.
 */
public class APIException extends RuntimeException {

    public APIException() {
        super("Error Request");
    }

    public APIException(String message) {
        super(message);
    }
}
