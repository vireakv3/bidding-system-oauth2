package com.vireak.bidding.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // General Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(
            Exception exp) {
        log.info("Exception: {} ------------------>>>", exp.getClass());
        log.info("Exception: {}, Trace: {}", exp.getMessage(), exp.getStackTrace());
        Map<String, Object> mapResponse = new HashMap<>();
        mapResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        mapResponse.put("message", exp.getMessage());
        return ResponseEntity
                .internalServerError()
                .body(mapResponse);
    }

    //TODO: ???
}
