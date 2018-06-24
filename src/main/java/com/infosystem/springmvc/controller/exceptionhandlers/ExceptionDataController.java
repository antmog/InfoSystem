package com.infosystem.springmvc.controller.exceptionhandlers;

import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.exception.MyBusinessException;
import com.infosystem.springmvc.exception.ValidationException;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice(annotations = RestController.class)
@Order(1)
public class ExceptionDataController {

    private static final Logger logger = Logger.getLogger(ExceptionDataController.class);

    /**
     * Sends bad request to the page with selected message.
     * @param e Logic exception
     * @return message
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    String exc(Exception e) {
        logger.error("Unexpected exception.",e);
        return "Whoops, something went wrong.";
    }

    /**
     * Sends bad request to the page with selected message.
     * @param e Logic exception
     * @return message
     */
    @ExceptionHandler({LogicException.class, DatabaseException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    String businessException(MyBusinessException e) {
        if(e instanceof LogicException){
            logger.warn(e.getMessage());
        }else if(e instanceof DatabaseException){
            logger.error(e.getMessage());
        }
        return e.getMessage();
    }

    /**
     * Sends unsupported media type request with selected message.
     * @param e Validation exception
     * @return message
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public @ResponseBody
    String validationException(ValidationException e) {
        logger.warn(e.getMessage());
        return e.getMessage();
    }
}
