package toll;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class TollFeeCalculator {
	
	// 
	public LocalDateTime[] dates;

    public TollFeeCalculator(String inputFile) {
    	try {
            Scanner sc = new Scanner(new File(inputFile));
            String[] dateStrings = sc.nextLine().split(", ");
            dates = new LocalDateTime[dateStrings.length /* bugfix - removed "-1" */ ];
            for(int i = 0; i < dates.length; i++) {
                dates[i] = LocalDateTime.parse(dateStrings[i], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            }
            System.out.println("The total fee for the inputfile is " + getTotalFeeCost(dates));
        } catch(IOException e) {
            System.err.println("Could not read file " + new File(inputFile).getAbsolutePath());
        }
    }

    public static int getTotalFeeCost(LocalDateTime[] dates) {
        int totalFee = 0;
        LocalDateTime intervalStart = dates[0];
        for(LocalDateTime date: dates) {
        	// my line below
            System.out.println(date.toString() + " " + getTollFeePerPassing(date));
            long diffInMinutes = intervalStart.until(date, ChronoUnit.MINUTES);
            System.out.println(diffInMinutes);
            if(diffInMinutes > 60) {
                totalFee += getTollFeePerPassing(date);
                intervalStart = date;
            } else {
            	if(getTollFeePerPassing(date) >= getTollFeePerPassing(intervalStart) ) {
            		totalFee -= getTollFeePerPassing(intervalStart);
            		totalFee += getTollFeePerPassing(date);
            	}
                // totalFee += Math.max(getTollFeePerPassing(date), getTollFeePerPassing(intervalStart));      
            }
            
        }
        // bugfix - changed Math.max -> Math.min
        return Math.min(totalFee, 60);
    }

    public static int getTollFeePerPassing(LocalDateTime date) {
        if (isTollFreeDate(date)) return 0;
        int hour = date.getHour();
        int minute = date.getMinute();
        if (hour == 6 && minute >= 0 && minute <= 29) return 8;
        else if (hour == 6 && minute >= 30 && minute <= 59) return 13;
        else if (hour == 7 && minute >= 0 && minute <= 59) return 18;
        else if (hour == 8 && minute >= 0 && minute <= 29) return 13;
        // bugfix - changed "else if (hour >= 8 && hour <= 14 && minute >= 30 && minute <= 59) return 8;" to row below
        else if (hour == 8 && minute >= 30 && minute <= 59 || hour >= 9 && minute >= 00 && hour <= 14  && minute <= 59) return 8;
        else if (hour == 15 && minute >= 0 && minute <= 29) return 13;
        else if (hour == 15 && minute >= 0 || hour == 16 && minute <= 59) return 18;
        else if (hour == 17 && minute >= 0 && minute <= 59) return 13;
        else if (hour == 18 && minute >= 0 && minute <= 29) return 8;
        else return 0;
    }

    public static boolean isTollFreeDate(LocalDateTime date) {
    	// changed to getDayOfWeek() and date.getMonth() for more visual ability
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getMonth() == Month.JULY;
        
    }

    public static void main(String[] args) {
        new TollFeeCalculator("src/test/resources/Lab4.txt");
    }
}
