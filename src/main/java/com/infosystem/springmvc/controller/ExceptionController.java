package com.infosystem.springmvc.controller;

import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.exception.MyBusinessException;
import com.infosystem.springmvc.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;


@RestControllerAdvice
public class ExceptionController {

    /**
     * Sends bad request to the page with selected message.
     *
     * @param e Logic exception
     * @return message
     */
    @ExceptionHandler({LogicException.class, DatabaseException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    String businessException(MyBusinessException e) {
        return e.getMessage();
    }

    /**
     * Sends unsupported media type request with selected message.
     *
     * @param e Validation exception
     * @return message
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public @ResponseBody
    String validationException(ValidationException e) {
        return e.getMessage();
    }

}
