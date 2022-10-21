package com.jin.utils.exceptions;

public class InvalidInputException extends RuntimeException{

    public InvalidInputException(String message, int bookId){}

    public InvalidInputException(String message) { super(message); }

    public InvalidInputException(Throwable cause) { super(cause); }

    public InvalidInputException(String message, Throwable cause) { super(message, cause); }

}
