package com.drone.droneapi.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus()
    public ResponseEntity<ApiResponse> handleException(Exception exception) {
        Map<Class<? extends Exception>, Function<Exception, ResponseEntity<ApiResponse>>> exceptionHandlers = new HashMap<>();
        exceptionHandlers.put(LocalNotFoundException.class, this::localNoFoundException);
        exceptionHandlers.put(LocalBadRequestException.class, this::localBadRequestException);
        exceptionHandlers.put(LocalIlegalArgumentRequestException.class, this::localIlegalArgumentRequestException);
        exceptionHandlers.put(MethodArgumentNotValidException.class, this::methodArgumentNotValidException);
        exceptionHandlers.put(HttpMessageNotReadableException.class, this::httpMessageNotReadableException);
        return exceptionHandlers.getOrDefault(exception.getClass(), null).apply(exception);
    }

    public ResponseEntity<ApiResponse> localNoFoundException(Exception ex) {
        LocalNotFoundException exception = (LocalNotFoundException)ex;
        return new ResponseEntity<>(exception.getApiResponse(), exception.getApiResponse().getStatusCode());
    }

    public ResponseEntity<ApiResponse> localBadRequestException(Exception ex) {
        LocalBadRequestException exception = (LocalBadRequestException)ex;
        return new ResponseEntity<>(exception.getApiResponse(), exception.getApiResponse().getStatusCode());
    }

    public ResponseEntity<ApiResponse> localIlegalArgumentRequestException(Exception ex) {
        LocalIlegalArgumentRequestException exception = (LocalIlegalArgumentRequestException)ex;
        return new ResponseEntity<>(exception.getApiResponse(), exception.getApiResponse().getStatusCode());
    }

    public ResponseEntity<ApiResponse> methodArgumentNotValidException(Exception ex) {
        MethodArgumentNotValidException exception = (MethodArgumentNotValidException)ex;
        BindingResult bindingResult = exception.getBindingResult();
        ApiResponse apiResponse = new ApiResponse(bindingResult);
        return new ResponseEntity<>(apiResponse, apiResponse.getStatusCode());
    }

    public ResponseEntity<ApiResponse> httpMessageNotReadableException(Exception ex) {
        HttpMessageNotReadableException exception = (HttpMessageNotReadableException)ex;
      //  BindingResult bindingResult = exception.getBindingResult();
        ApiResponse apiResponse = new ApiResponse();
        return new ResponseEntity<>(apiResponse, apiResponse.getStatusCode());
    }
}
