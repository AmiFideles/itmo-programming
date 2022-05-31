package com.itmo.programming.command.exceptions;

/**
 * Исключение, обозначающее зацикливание программы
 */
public class EndlessLoopingException extends Exception{
    public EndlessLoopingException(String message) {
        super(message);
    }
}
