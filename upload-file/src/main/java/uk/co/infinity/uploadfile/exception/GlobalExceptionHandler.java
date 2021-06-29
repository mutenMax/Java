package uk.co.infinity.uploadfile.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Component
@Slf4j
public class GlobalExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "home";

    @ExceptionHandler
    public ModelAndView handleException(
            Exception e) {
        log.error("Error = {}, message = {}", e, e.getMessage());
        ModelAndView modelAndView = new ModelAndView(DEFAULT_ERROR_VIEW);
        modelAndView.getModel().put("message", "Error : " + e.getMessage());
        return modelAndView;
    }
}
