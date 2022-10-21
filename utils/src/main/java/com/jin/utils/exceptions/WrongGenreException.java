package com.jin.utils.exceptions;

public class WrongGenreException extends RuntimeException{

    public WrongGenreException(){}

    public WrongGenreException(String message) { super(message); }

    public WrongGenreException(Throwable cause) { super(cause); }

    public WrongGenreException(String message, Throwable cause) { super(message, cause); }
}
