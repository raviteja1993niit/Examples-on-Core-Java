package com.psx.prime360ClientService.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author jayantronald
 *
 */

@Aspect
@Configuration
public class AfterAopAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@AfterReturning(value = "execution(* com.psx.prime360ClientService.serviceImpl.*.*(..))", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		logger.info("{} returned with value {}", joinPoint, result);
	}

	@After(value = "execution(* com.psx.prime360ClientService.serviceImpl.*.*(..))")
	public void after(JoinPoint joinPoint) {
		logger.info("After execution of {}", joinPoint);
	}

	@AfterThrowing(pointcut = "execution(* com.psx.prime360ClientService.serviceImpl.*.*(..))", throwing = "ex")
	public void logAfterThrowingAllMethods(Exception ex) throws Throwable {
		logger.info("AfterAopAspect.logAfterThrowingAllMethods() " + ex);
	}
}
