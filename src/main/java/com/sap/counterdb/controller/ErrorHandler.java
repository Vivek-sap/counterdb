package com.sap.counterdb.controller;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sap.counterdb.data.CustomErrorResponse;
import com.sap.counterdb.exceptions.HandledServiceException;
import com.sap.counterdb.exceptions.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class ErrorHandler extends ResponseEntityExceptionHandler {
   

    @ExceptionHandler(ResourceNotFoundException.class) // 404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<CustomErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return exceptionToHttpError(ex, HttpStatus.NOT_FOUND);
    }


   


    private ResponseEntity<CustomErrorResponse> exceptionToHttpError(HandledServiceException ex, HttpStatus httpStatus) {
        log.warn(ex.toString(), ex);
        CustomErrorResponse exceptionResponse = new CustomErrorResponse(ex.toString(), ex.getErrorSource().name());
        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }
}
