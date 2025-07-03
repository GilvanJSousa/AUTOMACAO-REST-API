package org.br.com.testes.utils.logger;

import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.log4j.Log4j2;
import org.br.com.testes.utils.LogFormatter;

import java.time.LocalDateTime;

@CommonsLog
public class ContextTime {

	private static ThreadLocal<LocalDateTime> timeSuiteInit = new ThreadLocal<LocalDateTime>();
	private static ThreadLocal<LocalDateTime> timeTestInit = new ThreadLocal<LocalDateTime>();

	public static void printTimeInitial() {
		timeTestInit.set(LocalDateTime.now());
		printHour("Initial hour of test...........", timeTestInit.get());

		if (timeSuiteInit.get() == null)
			timeSuiteInit.set(timeTestInit.get());
	}

	public static void printTimeFinal() {
		LocalDateTime timeTestFinal = LocalDateTime.now();
		printHour("Final hour of test.............", timeTestFinal);

		printTime("Time execution of test.........", returnDifferenceBetweenTimes(timeTestInit.get(), timeTestFinal));
		printTime("Time execution of tests so far.", returnDifferenceBetweenTimes(timeSuiteInit.get(), timeTestFinal));
	}

	private static void printHour(String previous, LocalDateTime time) {
		LogFormatter.logStep(String.format("%s: %d:%d:%d", previous, time.getHour(), time.getMinute(), time.getSecond()));
	}

	private static void printTime(String previous, LocalDateTime time) {
		LogFormatter.logStep(String.format("%s: %d Hour(s) %d minute(s) %d second(s)", previous, time.getHour(), time.getMinute(), time.getSecond()));
	}

	private static LocalDateTime returnDifferenceBetweenTimes(LocalDateTime timeInit, LocalDateTime timeFinal) {
		return timeFinal //
				.minusHours(timeInit.getHour()) //
				.minusMinutes(timeInit.getMinute()) //
				.minusSeconds(timeInit.getSecond());
	}
}