package com.example.technicaltask.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CustomExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public ErrorResponse memberExceptionHandler(MemberException e){
        log.error("{} is occurred.", e.getErrorCode());
        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }

    @ExceptionHandler(StoreException.class)
    public ErrorResponse storeExceptionHandler(StoreException e){
        log.error("{} is occurred.", e.getErrorCode());
        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }

}
