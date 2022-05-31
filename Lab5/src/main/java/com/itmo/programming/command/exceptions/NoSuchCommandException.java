package com.itmo.programming.command.exceptions;

/**
 * Исключение, обозначающее не существующую команду
 */
public class NoSuchCommandException extends RuntimeException {
    public NoSuchCommandException(String message) {
        super(message);
    }
}
