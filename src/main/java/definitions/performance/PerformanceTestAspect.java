package definitions.performance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
	private String testDirectory = "src/test/resources/tests";
	String s = new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date());
	String fileName = testDirectory + "/log-" + s.split(".txt")[0] + ".csv";
	String delimiter = ",";

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
			new File(testDirectory).mkdirs();
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
		bufferedWriter.write(jp + delimiter + time);
		bufferedWriter.newLine();
		bufferedWriter.flush();
	}

}
