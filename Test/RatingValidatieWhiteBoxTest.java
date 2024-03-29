package Test;

import TestMethode.RatingValidatie;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RatingValidatieWhiteBoxTest {

    @Test(expected = IllegalArgumentException.class)
    public void testValidateRating_JustBelowMinimumBoundary() {
        RatingValidatie.validateRating(0.999999);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateRating_JustAboveMaximumBoundary() {
        RatingValidatie.validateRating(10.000001);
    }

    @Test
    public void testValidateRating_WellBelowMinimumBoundary() {
        try {
            RatingValidatie.validateRating(-Double.MAX_VALUE);
            fail("Expected IllegalArgumentException for a rating well below the valid range");
        } catch (IllegalArgumentException e) {
            assertEquals("Beoordeling moet tussen de 1 en 10 zijn.", e.getMessage());
        }
    }

    @Test
    public void testValidateRating_WellAboveMaximumBoundary() {
        try {
            RatingValidatie.validateRating(Double.MAX_VALUE);
            fail("Expected IllegalArgumentException for a rating well above the valid range");
        } catch (IllegalArgumentException e) {
            assertEquals("Beoordeling moet tussen de 1 en 10 zijn.", e.getMessage());
        }
    }
}