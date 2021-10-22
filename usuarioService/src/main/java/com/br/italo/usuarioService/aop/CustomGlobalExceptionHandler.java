package com.br.italo.usuarioService.aop;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.br.italo.usuarioService.domain.ResponseErrorWrapper;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        List<Map<String,String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> Collections.singletonMap(x.getField(), x.getDefaultMessage()))
                .collect(Collectors.toList());
        
        ResponseErrorWrapper response = new ResponseErrorWrapper(status, errors);
        return new ResponseEntity<>(response, headers, status);

    }
	
	@ExceptionHandler({ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleMethodResourceNotFoundException(ResourceNotFoundException ex,HttpServletRequest request) {
        
        ResponseErrorWrapper response = new ResponseErrorWrapper(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }
}  