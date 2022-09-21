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
		
		int endCases[] = {
				6, 00, 8, 	//	end case start - hour, minutes, fee,
				6, 29, 8,	//	end case finish - hour, minutes, fee,
				6, 30, 13, 
				6, 59, 13,
				7, 00, 18, 
				7, 59, 18,
				8, 00, 13,  
				8, 29, 13,
				8, 30, 8, 
				14, 59, 8,
				15, 00, 13, 
				15, 29, 13,
				15, 30, 18, 
				16, 59, 18,
				17, 00, 13, 
				17, 59, 13,
				18, 00, 8, 
				18, 29, 8,
				18, 30, 0, 
				18, 59, 0
		};
		
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