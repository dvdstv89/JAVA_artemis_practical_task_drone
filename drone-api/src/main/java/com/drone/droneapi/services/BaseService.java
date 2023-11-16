package com.drone.droneapi.services;

import com.drone.droneapi.error.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;

import java.util.List;

public class BaseService {
    protected ApiResponse response;

    public BaseService()
    {
        response = new ApiResponse();
    }

    protected boolean isNullOrEmpty(Object object) {
        if (object == null) {
            return true;
        }

        if (object instanceof String) {
            return ((String) object).isEmpty();
        }

        if (object instanceof List) {
            return ((List<?>) object).isEmpty();
        }
        return false;
    }
}
