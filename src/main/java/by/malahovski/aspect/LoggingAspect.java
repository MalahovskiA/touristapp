package by.malahovski.aspect;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


/**
 * Aspect for logging method entries, exits, and exceptions in the service and controller layers.
 * This aspect provides logging capabilities using Log4j2 framework.
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    /**
     * Logs the entry of service methods.
     *
     * @param joinPoint the join point providing reflective access to both the state available at a join point
     */
    @Before("execution(* by.malahovski.service.impl.*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Entering method: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    /**
     * Logs the exit of service methods.
     *
     * @param joinPoint the join point providing reflective access to both the state available at a join point
     * @param result    the result of the method execution
     */
    @AfterReturning(pointcut = "execution(* by.malahovski.service.impl.*.*(..))", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        logger.info("Exiting method: {} with result: {}", joinPoint.getSignature(), result);
    }


    /**
     * Logs the entry of controller methods.
     *
     * @param joinPoint the join point providing reflective access to both the state available at a join point
     */
    @Before("execution(* by.malahovski.controller.*.*(..))")
    public void logBeforeControllerMethod(JoinPoint joinPoint) {
        logger.info("Entering controller method: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    /**
     * Logs the exit of controller methods.
     *
     * @param joinPoint the join point providing reflective access to both the state available at a join point
     * @param result    the result of the method execution
     */
    @AfterReturning(pointcut = "execution(* by.malahovski.controller.*.*(..))", returning = "result")
    public void logAfterControllerMethod(JoinPoint joinPoint, Object result) {
        logger.info("Exiting controller method: {} with result: {}", joinPoint.getSignature(), result);
    }

    /**
     * Logs exceptions thrown by service methods.
     *
     * @param joinPoint the join point providing reflective access to both the state available at a join point
     * @param exception the exception that was thrown
     */
    @AfterThrowing(pointcut = "execution(* by.malahovski.service.impl.*.*(..))", throwing = "exception")
    public void logServiceException(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in service method: {} with message: {}", joinPoint.getSignature(), exception.getMessage(), exception);
    }

    /**
     * Logs exceptions thrown by controller methods.
     *
     * @param joinPoint the join point providing reflective access to both the state available at a join point
     * @param exception the exception that was thrown
     */
    @AfterThrowing(pointcut = "execution(* by.malahovski.controller.*.*(..))", throwing = "exception")
    public void logControllerException(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in controller method: {} with message: {}", joinPoint.getSignature(), exception.getMessage(), exception);
    }
}