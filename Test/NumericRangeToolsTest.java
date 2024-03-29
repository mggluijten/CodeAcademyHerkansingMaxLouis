package Test;

import org.junit.Test;

import TestMethode.NumericRangeTools;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NumericRangeToolsTest {

    /**
     * @desc Validates if the input is within range of 0-100%
     * 
     * @subcontract value within valid range {
     * @requires 0 <= percentage <= 100;
     * @ensures \result = true;
     *          }
     * 
     * @subcontract value out of range low {
     * @requires percentage < 0;
     * @ensures \result = false;
     *          }
     * 
     * @subcontract value out of range high {
     * @requires percentage > 100;
     * @signals \result = false;
     *          }
     * 
     */

    @Test
    public void testValueWithinValidRange() {
        // Arrange
        int validPercentage = 50;

        // Act
        boolean result = NumericRangeTools.isValidPercentage(validPercentage);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testValueOutOfRangeLow() {
        // Arrange
        int outOfRangePercentage = -10;

        // Act
        boolean result = NumericRangeTools.isValidPercentage(outOfRangePercentage);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testValueOutOfRangeHigh() {
        // Arrange
        int outOfRangePercentage = 150;

        // Act
        boolean result = NumericRangeTools.isValidPercentage(outOfRangePercentage);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testBoundaryAtLowerLimit() {
        // Arrange
        int percentage = 0;

        // Act
        boolean result = NumericRangeTools.isValidPercentage(percentage);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testBoundaryAtUpperLimit() {
        // Arrange
        int percentage = 100;

        // Act
        boolean result = NumericRangeTools.isValidPercentage(percentage);

        // Assert
        assertTrue(result);
    }

    // This test assumes that the isValidPercentage method only accepts integers.
    // If it accepts other numeric types, those should be tested as well.
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidType() {
        // Arrange
        String invalidTypePercentage = "50";

        // Act
        boolean result = NumericRangeTools.isValidPercentage(Integer.parseInt(invalidTypePercentage));

        // This line should not be reached, as an exception is expected
        assertFalse(result);
    }

}
