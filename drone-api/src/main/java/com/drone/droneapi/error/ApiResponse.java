package com.drone.droneapi.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.method.HandlerMethod;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonPropertyOrder({"statusCode", "isValid", "errors", "result"})
public class ApiResponse {
    private HttpStatus statusCode;
    @JsonProperty("isValid")
    private boolean isValid;
    private List<String> errors;
    private Object result;
    private long date;

    @Value("${application.name}")
    @ReadOnlyProperty
    private String applicationName;
    private String controllerName;
    private String servicePathName;


  //  public ApiResponse(HttpServletRequest request, HandlerMethod handlerMethod) {
     //   errors = new ArrayList<>();
   //     date = System.currentTimeMillis();
        //controllerName = handlerMethod.getMethod().getDeclaringClass().toString();
       // servicePathName = request.getRequestURL().toString();
    //}

    public ApiResponse() {
        errors = new ArrayList<>();
        date = System.currentTimeMillis();
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
