package com.drone.droneapi.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;

@Getter
@Setter
public class BaseException extends Exception {
    private ApiResponse apiResponse;
  /*  @Autowired
    private HttpServletRequest request;
    @Autowired
    private HandlerMethod handlerMethod;*/

    public BaseException(String message, HttpStatus httpStatus) {
        super(message);
        apiResponse = new ApiResponse();
        apiResponse.getErrors().add(message);
        apiResponse.setStatusCode(httpStatus);
    }
}
