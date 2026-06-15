package com.blog.blogapp.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public String handlePostNotFound(PostNotFoundException ex,
                                     Model model) {

        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidation(MethodArgumentNotValidException ex,
                                   Model model) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        model.addAttribute("validationErrors", errors);
        model.addAttribute("errorMessage", "Validation Failed");

        return "error/general-error";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntime(RuntimeException ex, Model model) {

        model.addAttribute("errorMessage",
                ex.getMessage() != null ? ex.getMessage() : "Runtime error occurred");

        return "error/general-error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex,
                                         Model model) {

        model.addAttribute("errorMessage",
                ex.getMessage() != null ? ex.getMessage() : "Something went wrong");

        return "error/general-error";
    }
}