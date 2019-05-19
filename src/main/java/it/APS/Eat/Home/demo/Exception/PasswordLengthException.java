package it.APS.Eat.Home.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordLengthException extends RuntimeException {
    public PasswordLengthException(String s) {
        super(s);
    }
}
