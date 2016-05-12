package com.estsoft.jblog.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class MeasureDaoExecutionTimeAspect {
	private static final Log LOG = LogFactory.getLog( MeasureDaoExecutionTimeAspect.class );
	
	@Around( "execution( * *..dao.*.*(..)) || execution( * *..service.*.*(..)) || execution( * *..controller.*.*(..))" )	
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Object result = pjp.proceed();
		stopWatch.stop();
		String taskName = pjp.getTarget().getClass() + "." + pjp.getSignature().getName();
		LOG.info( result + " - [ExecutionTime][" + taskName + "] : " + stopWatch.getTotalTimeMillis()  + "millis" );
		return result;
	}
}