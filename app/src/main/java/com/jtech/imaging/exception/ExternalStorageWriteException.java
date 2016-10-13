package com.jtech.imaging.exception;

public class ExternalStorageWriteException extends Exception {

    private String message;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ExternalStorageWriteException() {
        super();
    }

    public ExternalStorageWriteException(String message) {
        super(message);
        this.message = message;
    }

    public ExternalStorageWriteException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}