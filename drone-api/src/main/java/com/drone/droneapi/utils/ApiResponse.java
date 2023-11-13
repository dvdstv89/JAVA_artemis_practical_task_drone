package com.drone.droneapi.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonPropertyOrder({ "statusCode", "isValid", "errors", "result" })
public class ApiResponse {
    private HttpStatus statusCode;
    @JsonProperty("isValid")
    private boolean isValid;
    private List<String> errors;
    private Object result;

    public ApiResponse() {
        errors = new ArrayList<>();
    }
    public ApiResponse(BindingResult bindingResult) {
        errors = new ArrayList<>();
        addValidationErrors(bindingResult);
    }

    public void addOkResponse200(Object result) {
        setResult(result);
        setStatusCode(HttpStatus.OK);
        setValid(true);
        setErrors(new ArrayList<>());
    }

    public void addCreateResponse201(Object result) {
        setResult(result);
        setStatusCode(HttpStatus.CREATED);
        setValid(true);
        setErrors(new ArrayList<>());
    }

    public void addBadRequestResponse400(String exception) {
        setStatusCode(HttpStatus.BAD_REQUEST);
        setValid(false);
        getErrors().add(exception);
        setResult(null);
    }

    public void addNotFoundResponse404(String message) {
        setStatusCode(HttpStatus.NOT_FOUND);
        setValid(false);
        getErrors().add(message);
        setResult(null);
    }

    private void addValidationErrors(BindingResult bindingResult) {
        setStatusCode(HttpStatus.BAD_REQUEST);
        setValid(false);

        List<String> validationErrors = new ArrayList<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            validationErrors.add(error.getDefaultMessage());
        }

        setErrors(validationErrors);
        setResult(null);
    }
}
