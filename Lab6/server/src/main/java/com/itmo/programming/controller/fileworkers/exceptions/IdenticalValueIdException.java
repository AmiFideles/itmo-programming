package com.itmo.programming.controller.fileworkers.exceptions;

/**
 * Исключение, обозначающее не уникальные id людей
 */
public class IdenticalValueIdException extends Exception {
    public IdenticalValueIdException(String message) {
        super(message);
    }
}
