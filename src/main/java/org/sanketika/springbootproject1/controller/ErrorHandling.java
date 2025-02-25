package org.sanketika.springbootproject1.controller;

import org.hibernate.exception.JDBCConnectionException;
import org.sanketika.springbootproject1.response.DatasetResponse;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLNonTransientConnectionException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorHandling {

    Map<String, Object> response = new HashMap<>();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleNotfoundexception(MethodArgumentNotValidException m)  {
        response.put("Status", HttpStatus.BAD_REQUEST.toString());
        response.put("message", "the requested parameter is not exits");
        response.put("timestamp",LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({ChangeSetPersister.NotFoundException.class, NullPointerException.class }) //this is used in post method
    public ResponseEntity<Map<String, Object>> notHandlerException(NullPointerException n) {
        response.put("status", HttpStatus.NOT_FOUND.toString());
        response.put("message", "requested dataset id is not found");
        response.put("timestamp",LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler({JDBCConnectionException.class,SQLNonTransientConnectionException.class, DataAccessResourceFailureException.class,CannotCreateTransactionException.class})
    public ResponseEntity<Map<String, Object>> handleDbException(Exception e) {
//        response.put("Status", HttpStatus.INTERNAL_SERVER_ERROR.toString());
//        response.put("message", "database connection is down");
//        response.put("timestamp",LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DatasetResponse.createResponse("Failure",HttpStatus.INTERNAL_SERVER_ERROR,"database connection is down",null));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleGenericError(Exception e){
        response.put("status",HttpStatus.INTERNAL_SERVER_ERROR.toString());
        response.put("message","An unexcepted error occured");
        response.put("timestamp",LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
