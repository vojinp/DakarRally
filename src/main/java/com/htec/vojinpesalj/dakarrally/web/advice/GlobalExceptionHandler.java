package com.htec.vojinpesalj.dakarrally.web.advice;

import com.htec.vojinpesalj.dakarrally.exception.CantUpdateVehicleException;
import com.htec.vojinpesalj.dakarrally.exception.EntityNotFoundException;
import com.htec.vojinpesalj.dakarrally.exception.RaceAlreadyStartedException;
import com.htec.vojinpesalj.dakarrally.web.vm.ErrorCode;
import com.htec.vojinpesalj.dakarrally.web.vm.ErrorViewModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(
            WebRequest webRequest, EntityNotFoundException ex) {
        return handleExceptionInternal(
                ex,
                new ErrorViewModel(ex.getMessage(), ErrorCode.NOT_FOUND),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                webRequest);
    }

    @Override
    public final ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest webRequest) {
        return handleExceptionInternal(
                ex,
                new ErrorViewModel(
                        ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                        ErrorCode.BAD_REQUEST),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                webRequest);
    }

    @ExceptionHandler(CantUpdateVehicleException.class)
    public ResponseEntity<Object> handleCantUpdateVehicle(
        WebRequest webRequest, EntityNotFoundException ex) {
        return handleExceptionInternal(
            ex,
            new ErrorViewModel(ex.getMessage(), ErrorCode.BAD_REQUEST),
            new HttpHeaders(),
            HttpStatus.BAD_REQUEST,
            webRequest);
    }

    @ExceptionHandler(RaceAlreadyStartedException.class)
    public ResponseEntity<Object> handleRaceAlreadyStarted(
        WebRequest webRequest, EntityNotFoundException ex) {
        return handleExceptionInternal(
            ex,
            new ErrorViewModel(ex.getMessage(), ErrorCode.BAD_REQUEST),
            new HttpHeaders(),
            HttpStatus.BAD_REQUEST,
            webRequest);
    }
}
