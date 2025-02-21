package org.sanketika.springbootproject1.controller;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLNonTransientConnectionException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorHandling {

    Map<String, Object> response = new HashMap<>();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handlenotfoundexception(MethodArgumentNotValidException m)  {
        response.put("Stuats", HttpStatus.BAD_REQUEST.value());
        response.put("message", "the requested parameter is not exits");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> notHandlerException(NoHandlerFoundException n) {
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("message", "requested dataset id is not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({SQLNonTransientConnectionException.class, DataAccessResourceFailureException.class,CannotCreateTransactionException.class})
    public ResponseEntity<Map<String, Object>> handledbException(SQLNonTransientConnectionException s) {
        response.put("Status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", "database connect id down");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handlegenericerror(Exception e){
        response.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message","An unexcepted error occur");
        return new ResponseEntity<>(response ,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
