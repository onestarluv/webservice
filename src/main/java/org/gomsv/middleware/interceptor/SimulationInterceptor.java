package org.gomsv.middleware.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.gomsv.common.ErrorMessages;
import org.gomsv.exception.ServiceUnavailableException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SimulationInterceptor implements HandlerInterceptor {

    private static final String SIMULATE_ERROR_HEADER = "X-Simulate-Error";
    private static final String ERROR_500 = "500";
    private static final String ERROR_503 = "503";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String errorCode = request.getHeader(SIMULATE_ERROR_HEADER);

        if (ERROR_500.equals(errorCode)) {
            throw new RuntimeException(ErrorMessages.SIMULATED_INTERNAL_ERROR);
        }

        if (ERROR_503.equals(errorCode)) {
            throw new ServiceUnavailableException(ErrorMessages.SERVICE_UNAVAILABLE);
        }

        return true;
    }
}
