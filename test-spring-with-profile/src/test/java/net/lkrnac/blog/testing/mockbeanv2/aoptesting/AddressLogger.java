package net.lkrnac.blog.testing.mockbeanv2.aoptesting;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
	
@Aspect
@Component
@Slf4j
@Profile("aop")
public class AddressLogger {
	@Before("execution(* net.lkrnac.blog.testing.mockbeanv2.beans.*.*(..))")
	public void logAddressCall(JoinPoint jp){
		log.info("Executing method {}", jp.getSignature());
	}
} 
