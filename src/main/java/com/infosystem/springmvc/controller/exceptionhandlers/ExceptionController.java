package com.infosystem.springmvc.controller.exceptionhandlers;

import com.infosystem.springmvc.controller.ControllerTemplate;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.exception.MyBusinessException;
import com.infosystem.springmvc.exception.ValidationException;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice(annotations = Controller.class)
@Order(2)
public class ExceptionController extends ControllerTemplate {

    private static final Logger logger = Logger.getLogger(ExceptionController.class);

    /**
     * Sends bad request to the page with selected message.
     * @param e Logic exception
     * @return message
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView exc(Exception e) {
        logger.error("Unexpected exception.",e);
        return prepareErrorPage("Whoops, smth went wrong.");
    }

    /**
     * Sends bad request to the page with selected message.
     * @param e Logic exception
     * @return message
     */
    @ExceptionHandler({LogicException.class, DatabaseException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView businessException(MyBusinessException e) {
        if(e instanceof LogicException){
            logger.warn(e.getMessage());
        }else if(e instanceof DatabaseException){
            logger.error(e.getMessage());
        }
        return prepareErrorPage(e.getMessage());
    }

    /**
     * Sends unsupported media type request with selected message.
     * @param e Validation exception
     * @return message
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ModelAndView validationException(ValidationException e) {
        logger.warn(e.getMessage());
        return prepareErrorPage(e.getMessage());
    }

    private ModelAndView prepareErrorPage(String errorText){
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("error", errorText);
        String path = "";
        if (!isCurrentAuthenticationAnonymous()) {
            path = "admin/";
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.getAuthorities().stream()
                    .noneMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
                path = "customer/";
            }
        }
        return new ModelAndView( path + "errorPage",modelMap);
    }
}
