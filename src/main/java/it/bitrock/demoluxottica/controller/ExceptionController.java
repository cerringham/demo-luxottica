package it.bitrock.demoluxottica.controller;

import it.bitrock.demoluxottica.exception.InvalidPatientException;
import it.bitrock.demoluxottica.exception.NotFoundException;
import it.bitrock.demoluxottica.exception.PatientCreationException;
import it.bitrock.demoluxottica.exception.PatientUpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFoundException() {
        // empty method to map the exception into a 404 code
    }

    @ExceptionHandler({InvalidPatientException.class, PatientCreationException.class, PatientUpdateException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handlePatientException() {
        // empty method to map the exception into a 404 code
    }
}
