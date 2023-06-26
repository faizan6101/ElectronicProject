package com.lcwd.electronic.store.exception;

import com.lcwd.electronic.store.payloads.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalException  {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandle (ResourceNotFoundException e) {
        log.info("Exception Handler Invoked");

        ApiResponse response = ApiResponse.builder().message(e.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, Objects>> handleMethodArgument (ResourceNotFoundException e) {
//        log.info("Exception Handler Invoked");
//
//        ApiResponse response = ApiResponse.builder().message(e.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponse> handleBadApiRequest (ResourceNotFoundException e) {
log.info("bad Api request");
        ApiResponse response = ApiResponse.builder().message(e.getMessage()).status(HttpStatus.NOT_FOUND).success(false).build();
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
}}