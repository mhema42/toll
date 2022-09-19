package toll;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.security.Timestamp;
import java.security.spec.ECField;
import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Random;

import javax.security.auth.kerberos.KerberosCredMessage;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** @author mhema **/
class TollFeeCalculatorTest {
	
	int testYear = 2022;
	java.time.Month testMonth = Month.SEPTEMBER;
	int testDay = 16;
	
	// fee for different time stamp
	private int fee01 = 8; 	// between 06.00 to 06.29
	private int fee02 = 13; // between 06.30 to 06.59
	private int fee03 = 18; // between 07.00 to 07.59
	private int fee04 = 13; // between 08.00 to 08.29
	private int fee05 = 8; 	// between 08.30 to 14.59
	private int fee06 = 13; // between 15.00 to 15.29
	private int fee07 = 18;	// between 15.30 to 16.59
	private int fee08 = 13; // between 17.00 to 17.59
	private int fee09 = 8;	// between 18.00 to 18.29
	private int fee10 = 0;	// between 18.30 to 05.59
	
	int endCases[] = {
		06, 00, fee01, 06, 29, fee01,
		06, 30, fee02, 06, 59, fee02,
		07, 00, fee03, 07, 59, fee03,
		 8, 00, fee04,  8, 29, fee04,
		 8, 30, fee05, 14, 59, fee05,
		15, 00, fee06, 15, 29, fee06,
		15, 30, fee07, 16, 59, fee07,
		17, 00, fee08, 17, 59, fee08,
		18, 00, fee09, 18, 29, fee09,
		18, 30, fee10, 18, 59, fee10,
 	};
	
	@Test
	@DisplayName("Test if dates.length has equal length to number of time stamps in textfile")
	void testTollFeeCalculator() {
		TollFeeCalculator tf = new TollFeeCalculator("src/test/resources/Lab4.txt");
		assertEquals(10, tf.dates.length);
	}
	
	@Test
	@DisplayName("Test if only highest fee is returned, if time between time stamp is within 60min")
	void testGetTotalFeeCost01() {	
		LocalDateTime[] date = new LocalDateTime[3];
		date[0] = LocalDateTime.of(testYear, testMonth, testDay, 6, 29); 	// fee 8, not counted because smaller and within 60min of next time stamp
		date[1] = LocalDateTime.of(testYear, testMonth, testDay, 6, 59); 	// fee 13, not counted because smaller and within 60min of next time stamp
		date[2] = LocalDateTime.of(testYear, testMonth, testDay, 7, 29); 	// fee 18, counted because larger fee than previous
        assertEquals(18, TollFeeCalculator.getTotalFeeCost(date));
	}
	
	@Test
	@DisplayName("Test if fee is 60 even if total fee is > 60")
	void testGetTotalFeeCost02() {	
		LocalDateTime[] date = new LocalDateTime[4];
		date[0] = LocalDateTime.of(testYear, testMonth, testDay, 6, 30); 	// fee 13
		date[1] = LocalDateTime.of(testYear, testMonth, testDay, 7, 59);	// fee 18
		date[2] = LocalDateTime.of(testYear, testMonth, testDay, 15, 00);	// fee 13
		date[3] = LocalDateTime.of(testYear, testMonth, testDay, 16, 01);	// fee 18
        assertEquals(60, TollFeeCalculator.getTotalFeeCost(date)); 			// sum of fee is 62, but returns 60 as parameters says
	}

	@Test
	@DisplayName("Test if correct fee is returned at each end case for time stamps")
	void testGetTotalFeeCost03() {			
		for(int i = 0; i < endCases.length; i += 3) {
			LocalDateTime[] date = new LocalDateTime[1];
			date[0] = LocalDateTime.of(testYear, testMonth, testDay, endCases[i], endCases[i+1]);
			int fee = endCases[i+2];
	        assertEquals(fee, TollFeeCalculator.getTotalFeeCost(date));
		}
	}
	
	@Test
	@DisplayName("Test if isTollFreeDate is true for july wich is a toll free month")
	void isTollFreeDate1() {
		LocalDateTime date = LocalDateTime.of(2022, Month.JULY, 1, 12, 00);
		assertEquals(true, TollFeeCalculator.isTollFreeDate(date));
	}
	
	@Test
	@DisplayName("Test if isTollFreeDate is true for saturday wich is a toll free day")
	void isTollFreeDate2() {
		LocalDateTime date = LocalDateTime.of(2022, Month.SEPTEMBER, 24, 12, 00);
		assertEquals(true, TollFeeCalculator.isTollFreeDate(date));
	}
	
	@Test
	@DisplayName("Test if isTollFreeDate is true for sunday wich is a toll free day")
	void isTollFreeDate3() {
		LocalDateTime date = LocalDateTime.of(2022, Month.SEPTEMBER, 25, 12, 00);
		assertEquals(true, TollFeeCalculator.isTollFreeDate(date));
	}
}