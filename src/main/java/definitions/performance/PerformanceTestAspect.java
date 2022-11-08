package definitions.performance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class PerformanceTestAspect {

	public static final Logger logger = LogManager.getLogger(PerformanceTestAspect.class);

	@Around("@annotation(org.junit.Test)")
	public Object measure(ProceedingJoinPoint pjp) throws Throwable {
		long time = System.nanoTime();
		Object answer = pjp.proceed();
		time = System.nanoTime() - time;
		logger.info("time for test method {}: {}", pjp.toShortString(), time);
		return answer;
	}

}
