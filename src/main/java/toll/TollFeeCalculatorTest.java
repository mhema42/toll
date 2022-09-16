package toll;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Random;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** @author mhema **/
class TollFeeCalculatorTest {
	
	java.time.Month testMonth = Month.SEPTEMBER;
	int testDay = 16;
	
	//  random number for date/time stamp in test
	Random rand = new Random();
	private int rand_int1 = rand.nextInt(29);		// random 0 to 29 (minutes)
	private int rand_int2 = rand.nextInt(29) + 30;	// random 30 to 59 (minutes)
	private int rand_int3 = rand.nextInt(59);		// random 0 to 59 (minutes)
	private int rand_int4 = rand.nextInt(5) + 9;	// random 9 to 14 (hours)
	private int rand_int5 = rand.nextInt(5) + 19;	// random 19 to 24 (hours)
	private int rand_int6 = rand.nextInt(4) + 1;	// random 1 to 5 (hours)
	private int rand_int7 = rand.nextInt(24);		// random 1 to 24 (hours)
	private int rand_int8 = rand.nextInt(31);		// random 1 to 31 (days)
	
	// fee for different time stamp
	private int fee1 = 8; 	// between 06.00 to 06.29
	private int fee2 = 13; 	// between 06.30 to 06.59
	private int fee3 = 18; 	// between 07.00 to 06.59
	private int fee4 = 13; 	// between 08.00 to 08.29
	private int fee5 = 8; 	// between 08.30 to 14.59
	private int fee6 = 13; 	// between 15.00 to 15.29
	private int fee7 = 18;	// between 15.30 to 16.59
	private int fee8 = 13; 	// between 17.00 to 17.59
	private int fee9 = 8;	// between 18.00 to 18.29
	private int fee10 = 0;	// between 18.30 to 05.59
	
	
	
	
	
	@Test
	void testTollFeeCalculator() {
		TollFeeCalculator tf = new TollFeeCalculator("src/test/resources/Lab4.txt");
		assertEquals(10, tf.dates.length);
		//System.out.println(tf.dates.length);
		
		//fail("Not yet implemented");
	}

	@Test 
	@DisplayName("Test between 06.00 to 06.29")
	void testGetTotalFeeCost01() {
//		int hour = 6;
//		int minute = 0;
//		
//		int hours[] = new int[];
//		hour
				
		
		// for(i = 0, i >= )
		
		LocalDateTime[] date = new LocalDateTime[3];
		date[0] = LocalDateTime.of(2022, testMonth, testDay, 6, 0); 	// test endcase #1
		date[1] = LocalDateTime.of(2022, testMonth, testDay, 6, 29);		// test endcase #2
        date[2] = LocalDateTime.of(2022, testMonth, testDay, 6, rand_int1);	// test random case within parameters
        assertEquals(fee1*3, TollFeeCalculator.getTotalFeeCost(date));
	}
	
	@Test 
	@DisplayName("Test between 06.30 to 06.59")
	void testGetTotalFeeCost02() {
		LocalDateTime[] date = new LocalDateTime[3];
		date[0] = LocalDateTime.of(2022, testMonth, testDay, 6, 30);
		date[1] = LocalDateTime.of(2022, testMonth, testDay, 6, 59);
		date[2] = LocalDateTime.of(2022, testMonth, testDay, 6, rand_int2);
        assertEquals(fee2*3, TollFeeCalculator.getTotalFeeCost(date));
	}
        
	@Test 
	@DisplayName("Test between 07.00 to 06.59")
	void testGetTotalFeeCost03() {
		LocalDateTime[] date = new LocalDateTime[3];
		date[0] = LocalDateTime.of(2022, testMonth, testDay, 7, 00); 
		date[1] = LocalDateTime.of(2022, testMonth, testDay, 7, 59);
		date[2] = LocalDateTime.of(2022, testMonth, testDay, 7, rand_int3);
        assertEquals(fee3*3, TollFeeCalculator.getTotalFeeCost(date));
	}
        
	@Test 
	@DisplayName("Test between 08.00 to 08.29")
	void testGetTotalFeeCost04() {
		LocalDateTime[] date = new LocalDateTime[3];
		date[0] = LocalDateTime.of(2022, testMonth, testDay, 8, 0);
		date[1] = LocalDateTime.of(2022, testMonth, testDay, 8, 29);
		date[2] = LocalDateTime.of(2022, testMonth, testDay, 8, rand_int1);
        assertEquals(fee4*3, TollFeeCalculator.getTotalFeeCost(date));
	}
	
	@Test // This was previous a bug in program
	@DisplayName("Test between 08.30 to 14.59")
	void testGetTotalFeeCost05() {
		LocalDateTime[] date = new LocalDateTime[3];
		date[0] = LocalDateTime.of(2022, testMonth, testDay, 8, 30);
		date[1] = LocalDateTime.of(2022, testMonth, testDay, 14, 59);
		date[2] = LocalDateTime.of(2022, testMonth, testDay, rand_int4, rand_int3);
        assertEquals(fee5*3, TollFeeCalculator.getTotalFeeCost(date));
	}

	@Test
	@DisplayName("Test between 15.00 to 15.29")
	void testGetTotalFeeCost06() {
		LocalDateTime[] date = new LocalDateTime[3];
		date[0] = LocalDateTime.of(2022, testMonth, testDay, 15, 00);
		date[1] = LocalDateTime.of(2022, testMonth, testDay, 15, 29);
		date[2] = LocalDateTime.of(2022, testMonth, testDay, 15, rand_int1);
        assertEquals(fee6*3, TollFeeCalculator.getTotalFeeCost(date));
	}
	
	@Test
	@DisplayName("Test between 15.30 to 16.59")
	void testGetTotalFeeCost07() {
		LocalDateTime[] date = new LocalDateTime[3];
		date[0] = LocalDateTime.of(2022, testMonth, testDay, 15, 30);
		date[1] = LocalDateTime.of(2022, testMonth, testDay, 16, 59);
		date[2] = LocalDateTime.of(2022, testMonth, testDay, 16, rand_int3);
        assertEquals(fee7*3, TollFeeCalculator.getTotalFeeCost(date));
	}
	
	@Test
	@DisplayName("Test between 17.00 to 17.59")
	void testGetTotalFeeCost08() {
		LocalDateTime[] date = new LocalDateTime[3];
		date[0] = LocalDateTime.of(2022, testMonth, testDay, 17, 00);
		date[1] = LocalDateTime.of(2022, testMonth, testDay, 17, 59);
		date[2] = LocalDateTime.of(2022, testMonth, testDay, 17, rand_int3);
        assertEquals(fee8*3, TollFeeCalculator.getTotalFeeCost(date));
	}
	
	@Test
	@DisplayName("Test between 18.00 to 18.29")
	void testGetTotalFeeCost09() {
		LocalDateTime[] date = new LocalDateTime[3];
		date[0] = LocalDateTime.of(2022, testMonth, testDay, 18, 00);
		date[1] = LocalDateTime.of(2022, testMonth, testDay, 18, 29);
		date[2] = LocalDateTime.of(2022, testMonth, testDay, 18, rand_int1);
        assertEquals(fee9*3, TollFeeCalculator.getTotalFeeCost(date));
	}
	
	@Test
	@DisplayName("Test between 18.30 to 05.59")
	void testGetTotalFeeCost10() {
		LocalDateTime[] date = new LocalDateTime[4];
		date[0] = LocalDateTime.of(2022, testMonth, testDay, 18, 30);
		date[1] = LocalDateTime.of(2022, testMonth, testDay, 5, 59);
		date[2] = LocalDateTime.of(2022, testMonth, testDay, rand_int5, rand_int3);
		date[3] = LocalDateTime.of(2022, testMonth, testDay, rand_int6, rand_int3);
        assertEquals(fee10*4, TollFeeCalculator.getTotalFeeCost(date));
	}
      
	
	/** Test method for {@link toll.TollFeeCalculator#getTollFeePerPassing(java.time.LocalDateTime)}. */
	@Test
	void testGetTollFeePerPassing() {
		//fail("Not yet implemented");
	}

	/** Test method for {@link toll.TollFeeCalculator#isTollFreeDate(java.time.LocalDateTime)}.	 */
	@Test
	void testIsTollFreeDate1() {
		LocalDateTime[] date = new LocalDateTime[1];
		date[0] = LocalDateTime.of(2022, Month.JULY, rand_int8, rand_int7, rand_int3);
        assertEquals(0, TollFeeCalculator.getTotalFeeCost(date));
		//System.out.println(date[0].getMonth());
	}

	/** Test method for {@link toll.TollFeeCalculator#main(java.lang.String[])}. */
	@Test
	void testMain() {
		//fail("Not yet implemented");
	}
}