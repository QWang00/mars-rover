package org.northcoders.logiclayer;

public class InvalidPositionException extends RuntimeException{
    public InvalidPositionException() {
    }

    public InvalidPositionException(String message) {
        super(message);
    }
}
