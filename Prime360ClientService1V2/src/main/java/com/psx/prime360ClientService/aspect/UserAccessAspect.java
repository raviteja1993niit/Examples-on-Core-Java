package com.psx.prime360ClientService.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
public class UserAccessAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// What kind of method calls I would intercept
	// execution(* PACKAGE.*.*(..))
	// Weaving & Weaver
	@Before("execution(* com.psx.prime360ClientService.repository.*.*(..))")
	public void before(JoinPoint joinPoint) {
		// Advice
		logger.info(" Allowed execution for {}", joinPoint);
	}
}
