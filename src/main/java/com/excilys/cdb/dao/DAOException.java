package com.excilys.cdb.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final String TAG = "/!\\ DAOException /!\\ _ ";
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DAOException.class);

    public DAOException(String message) {
        super(message);
        LOGGER.error(TAG + "message = " + message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
        LOGGER.error(TAG + "message = " + message + "\n\tcause = "
                + cause.toString());
    }

    public DAOException(Throwable cause) {
        super(cause);
        LOGGER.error(TAG + "cause = " + cause.toString());
    }
}
