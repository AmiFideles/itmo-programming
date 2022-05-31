package com.itmo.programming.exceptions;

/**
 * Исключение, обозначающее неправильный ввод данных
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}
