package by.tms.controller;

import by.tms.dto.RegUserDto;
import by.tms.service.exception.DuplicateUserException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(value = DuplicateUserException.class)
    public String DuplicateUserHandler(Model model, RuntimeException e) {
        model.addAttribute("regUserDto", new RegUserDto());
        model.addAttribute("errorMassage", e.getMessage());
        return "reg";
    }
}
