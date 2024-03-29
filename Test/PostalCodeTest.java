package Test;

import TestMethode.PostalCode;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class PostalCodeTest {

    @Test
    public void testFormatPostalCode_ValidInput() {
        assertEquals("1234 AB", PostalCode.formatPostalCode("1234 AB"));
    }

    @Test(expected = NullPointerException.class)
    public void testFormatPostalCode_NullInput() {
        PostalCode.formatPostalCode(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatPostalCode_InvalidLength() {
        PostalCode.formatPostalCode("123 AB");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatPostalCode_InvalidSpacePosition() {
        PostalCode.formatPostalCode("1234AB");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatPostalCode_InvalidNumericPart() {
        PostalCode.formatPostalCode("999 AB");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatPostalCode_InvalidLetterPartTooShort() {
        PostalCode.formatPostalCode("1234 A");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatPostalCode_InvalidLetterPartNonAlphabetic() {
        PostalCode.formatPostalCode("1234 A1");
    }

    @Test
    public void testFormatPostalCode_ValidLowercaseLetters() {
        assertEquals("1234 AB", PostalCode.formatPostalCode("1234 ab"));
    }

    // Boundary conditions
    @Test
    public void testFormatPostalCode_ValidLowestNumeric() {
        assertEquals("1000 AA", PostalCode.formatPostalCode("1000 aa"));
    }

    @Test
    public void testFormatPostalCode_ValidHighestNumeric() {
        assertEquals("9999 ZZ", PostalCode.formatPostalCode("9999 zz"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatPostalCode_InvalidNumericTooLow() {
        PostalCode.formatPostalCode("0999 AA");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatPostalCode_InvalidNumericTooHigh() {
        PostalCode.formatPostalCode("10000 ZZ");
    }


}
