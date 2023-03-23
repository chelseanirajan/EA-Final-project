package edu.miu.badge.advisors;

import edu.miu.badge.exceptions.TransactionDeclinedException;
import edu.miu.badge.exceptions.LocationNotFoundException;
import edu.miu.badge.exceptions.ResourceNotFoundException;
import edu.miu.badge.exceptions.TimeSlotNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

/**
 *
 * @author Daniel Tsegay Meresie
 */
@RestControllerAdvice
public class GeneralAdvices {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TimeSlotNotFoundException.class)
    public Map<String, String> handleErrorForTimeSlot(TimeSlotNotFoundException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", e.getMessage());
        return errorMap;
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LocationNotFoundException.class)
    public Map<String, String> handleErrorForLocation(LocationNotFoundException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", e.getMessage());
        return errorMap;
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Map<String, String> unableTodelete(SQLIntegrityConstraintViolationException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", e.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TransactionDeclinedException.class)
    public Map<String, String> unableToCreate(TransactionDeclinedException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", e.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String, String> unableToFind(ResourceNotFoundException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", e.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AuthenticationException.class)
    public Map<String, String> unabaleToLogin(AuthenticationException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", e.getMessage());
        return errorMap;
    }
}