package com.leandroinacio.picmeapi.scheduled;

//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
  
  fixedRate = ms
  
  cron:
   second, minute, hour, day of month, month, day(s) of week
   (*) means match any
   * /X means "every X"
   ? ("no specific value") - useful when you need to specify something in 
 	one of the two fields in which the character is allowed, but not the other. For example, if I want my trigger to fire on a particular day of the month (say, the 10th), but I don't care what day of the week that happens to be, I would put "10" in the day-of-month field and "?" in the day-of-week field.
 
  	"0 0 * * * *" = the top of every hour of every day.
 	"* /10 * * * * *" = every ten seconds.
 	"0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
 	"0 0 8,10 * * *" = 8 and 10 o'clock of every day.
 	"0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
 	"0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
 	"0 0 0 25 12 ?" = every Christmas Day at midnight
 
 */

@Component
public class ScheduledTasks {

//	@Scheduled(fixedRate = 100)
//	public void scheduledTest() {
//		System.out.print("123");
//	}
	
	
}
