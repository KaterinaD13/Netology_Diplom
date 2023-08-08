package ru.netology.netologydiploma.exceprion;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String msg) {

        super(msg);
    }
}