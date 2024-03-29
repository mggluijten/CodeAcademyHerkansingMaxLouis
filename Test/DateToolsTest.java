package Test;

import TestMethode.DateTools;
import org.junit.Test;
import java.time.LocalDate;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateToolsTest {

    @Test
    public void testValidateDate_ValidLeapYear() {
        assertTrue(DateTools.validateDate(29, 2, 2020)); // 2020 is a leap year
    }

    @Test
    public void testValidateDate_InvalidLeapYear() {
        assertFalse(DateTools.validateDate(29, 2, 2019)); // 2019 is not a leap year
    }

    @Test
    public void testValidateDate_ValidNonLeapYear() {
        assertTrue(DateTools.validateDate(28, 2, 2019)); // 2019 is not a leap year
    }

    @Test
    public void testValidateDate_InvalidDate() {
        assertFalse(DateTools.validateDate(31, 4, 2020)); // April has 30 days
    }

    @Test
    public void testValidateDate_ValidEndOfMonth() {
        assertTrue(DateTools.validateDate(30, 4, 2020)); // April has 30 days
    }

    @Test
    public void testValidateDate_InvalidMonth() {
        assertFalse(DateTools.validateDate(10, 13, 2020)); // Month 13 does not exist
    }

    @Test
    public void testValidateDate_InvalidNegativeDay() {
        assertFalse(DateTools.validateDate(-1, 1, 2020)); // Negative day is invalid
    }

    @Test
    public void testValidateDate_InvalidNegativeMonth() {
        assertFalse(DateTools.validateDate(10, -1, 2020)); // Negative month is invalid
    }

    @Test
    public void testValidateDate_InvalidNegativeYear() {
        assertFalse(DateTools.validateDate(10, 1, -1)); // Negative year is invalid
    }

    // Test for validateAndCheckFutureDate
    @Test
    public void testValidateAndCheckFutureDate_TodayDate() {
        LocalDate today = LocalDate.now();
        assertTrue(DateTools.validateAndCheckFutureDate(today.getYear(), today.getMonthValue(), today.getDayOfMonth())); // Today is valid and not before today
    }

    @Test
    public void testValidateAndCheckFutureDate_FutureDate() {
        LocalDate future = LocalDate.now().plusDays(1);
        assertTrue(DateTools.validateAndCheckFutureDate(future.getYear(), future.getMonthValue(), future.getDayOfMonth())); // Future date is valid and not before today
    }

    @Test
    public void testValidateAndCheckFutureDate_PastDate() {
        LocalDate past = LocalDate.now().minusDays(1);
        assertFalse(DateTools.validateAndCheckFutureDate(past.getYear(), past.getMonthValue(), past.getDayOfMonth())); // Past date is invalid because it's before today
    }

    @Test
    public void testValidateAndCheckFutureDate_InvalidDate() {
        assertFalse(DateTools.validateAndCheckFutureDate(2020, 4, 31)); // April 31st is invalid
    }

   
}