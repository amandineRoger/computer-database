package com.excilys.cdb.mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception to throw all exceptions met in *Mapper classes.
 *
 * @author Amandine Roger
 *
 */
public class MapperException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static final String TAG = "/!\\ MapperException /!\\ _ ";
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MapperException.class);

    /**
     * Construct a new MapperException with a message.
     *
     * @param message
     *            text to log as error message
     */
    public MapperException(String message) {
        super(message);
        LOGGER.error(TAG + "message = " + message);
    }

    /**
     * Construct a new MapperException which wrap a throwable and contains a
     * message.
     *
     * @param message
     *            text to log as error message
     * @param cause
     *            throwable object to wrap in MapperException
     */
    public MapperException(String message, Throwable cause) {
        super(message, cause);
        LOGGER.error(TAG + "message = " + message + "\n\tcause = "
                + cause.toString());
    }

    /**
     * Construct a new MapperException which wrap a throwable.
     *
     * @param cause
     *            throwable object to wrap in MapperException
     */
    public MapperException(Throwable cause) {
        super(cause);
        LOGGER.error(TAG + "cause = " + cause.toString());
    }
}
