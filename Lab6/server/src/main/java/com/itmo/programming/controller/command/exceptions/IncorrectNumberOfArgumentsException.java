package com.itmo.programming.controller.command.exceptions;

/**
 * Исключение, обозначающее неверное количество аргументов команды
 */
public class IncorrectNumberOfArgumentsException extends Exception {
    public IncorrectNumberOfArgumentsException(String message) {
        super(message);
    }
}
