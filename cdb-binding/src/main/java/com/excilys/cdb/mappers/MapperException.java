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

    public MapperException(String message) {
        super(message);
        LOGGER.error(TAG + "message = " + message);
    }

    public MapperException(String message, Throwable cause) {
        super(message, cause);
        LOGGER.error(TAG + "message = " + message + "\n\tcause = "
                + cause.toString());
    }

    public MapperException(Throwable cause) {
        super(cause);
        LOGGER.error(TAG + "cause = " + cause.toString());
    }
}
