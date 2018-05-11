package com.infosystem.springmvc.controller;

import com.infosystem.springmvc.exception.LogicException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(LogicException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody String handleException(LogicException e, WebRequest request) {
        System.out.println("HELOU BOUNTY HUNTER");
        return e.getMessage();
    }


}
