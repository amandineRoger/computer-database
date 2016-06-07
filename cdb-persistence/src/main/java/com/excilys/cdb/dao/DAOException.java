package com.excilys.cdb.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final String TAG = "/!\\ DAOException /!\\ _ ";
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DAOException.class);

    /**
     * Construct a new DAOException with a message.
     *
     * @param message
     *            text to log as error message
     */
    public DAOException(String message) {
        super(message);
        LOGGER.error(TAG + "message = " + message);
    }

    /**
     * Construct a new DAOException which wrap a throwable and contains a
     * message.
     *
     * @param message
     *            text to log as error message
     * @param cause
     *            throwable object to wrap in MapperException
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
        LOGGER.error(TAG + "message = " + message + "\n\tcause = "
                + cause.toString());
    }

    /**
     * Construct a new DAOException which wrap a throwable.
     *
     * @param cause
     *            throwable object to wrap in DAOException
     */
    public DAOException(Throwable cause) {
        super(cause);
        LOGGER.error(TAG + "cause = " + cause.toString());
    }
}
