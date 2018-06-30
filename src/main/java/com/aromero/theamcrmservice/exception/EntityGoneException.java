package com.aromero.theamcrmservice.exception;

import javax.persistence.PersistenceException;

public class EntityGoneException extends PersistenceException {
    /**
     * Constructs a new <code>EntityGoneException</code> exception with
     * <code>null</code> as its detail message.
     */
    public EntityGoneException() {
        super();
    }

    /**
     * Constructs a new <code>EntityGoneException</code> exception with the
     * specified detail message.
     *
     * @param message
     *            the detail message.
     */
    public EntityGoneException(String message) {
        super(message);
    }
}
