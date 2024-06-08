package codes.wink.parkwink.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Interceptor for handling HTTP requests in the application.
 * Implements the HandlerInterceptor interface to provide pre-processing, post-processing,
 * and after-completion processing of HTTP requests.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Component - Indicates that this class is a Spring component.</li>
 * </ul>
 * </p>
 */
@Component
public class ServiceInterceptor implements HandlerInterceptor {

    /**
     * Pre-processes the request before the controller method is called.
     *
     * @param request  the HttpServletRequest object.
     * @param response the HttpServletResponse object.
     * @param handler  the handler (or controller) that will handle the request.
     * @return true to continue the request handling, false to stop the request handling.
     * @throws Exception if an error occurs during request processing.
     */
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        System.out.println("PREHandle called - " + request.getMethod() + " " + response.getStatus());
        return true;
    }

    /**
     * Post-processes the request after the controller method has been called.
     *
     * @param request      the HttpServletRequest object.
     * @param response     the HttpServletResponse object.
     * @param handler      the handler (or controller) that handled the request.
     * @param modelAndView the ModelAndView object that the handler returned (can be null).
     * @throws Exception if an error occurs during request processing.
     */
    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("POSTHandle called - " + request.getMethod() + " " + response.getStatus());
    }

    /**
     * Processes the request after the complete request has finished.
     *
     * @param request  the HttpServletRequest object.
     * @param response the HttpServletResponse object.
     * @param handler  the handler (or controller) that handled the request.
     * @param ex       any exception thrown during request processing (can be null).
     */
    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            @Nullable Exception ex) {
        System.out.println("afterCompletion called - " + request.getMethod() + " " + response.getStatus());
    }

}
