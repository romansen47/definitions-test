package definitions.performance;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class PerformanceTestAspect {

	public static final Logger logger = LogManager.getLogger(PerformanceTestAspect.class);

	private BufferedWriter bufferedWriter;
	private FileWriter fileWriter;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
	private String fileName = "src/test/resources/" + now.toString();

	@Around("@annotation(org.junit.Test)")
	public Object measure(ProceedingJoinPoint pjp) throws Throwable {
		long time = System.nanoTime();
		Object answer = pjp.proceed();
		time = System.nanoTime() - time;
		String jp = pjp.toShortString().split(Pattern.quote("execution("))[1].split(Pattern.quote("("))[0];
		log(jp, time);
		return answer;
	}

	public BufferedWriter getBufferedWriter() throws IOException {
		if (bufferedWriter == null) {
			bufferedWriter = new BufferedWriter(getFileWriter());
		}
		return bufferedWriter;
	}

	public void setBufferedWriter(BufferedWriter bufferedWriter) {
		this.bufferedWriter = bufferedWriter;
	}

	public FileWriter getFileWriter() throws IOException {
		if (fileWriter == null) {
			fileWriter = new FileWriter(fileName);
		}
		return fileWriter;
	}

	public void setFileWriter(FileWriter fileWriter) {
		this.fileWriter = fileWriter;
	}

	private void log(String jp, long time) throws IOException {
		logger.info("execution time for {}: {}", jp, time);

		getBufferedWriter();
		bufferedWriter.write("execution time for " + jp + ": " + time);
		bufferedWriter.flush();
	}

}
