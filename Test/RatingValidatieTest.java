package Test;

import TestMethode.RatingValidatie;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class RatingValidatieTest {

    @Test
    public void testValidateRating_ValidMinimumRating() {
        double rating = 1.0;
        assertEquals(rating, RatingValidatie.validateRating(rating), 0.0);
    }

    @Test
    public void testValidateRating_ValidMaximumRating() {
        double rating = 10.0;
        assertEquals(rating, RatingValidatie.validateRating(rating), 0.0);
    }

    @Test
    public void testValidateRating_ValidMidRating() {
        double rating = 5.5;
        assertEquals(rating, RatingValidatie.validateRating(rating), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateRating_InvalidBelowMinimumRating() {
        double rating = 0.9;
        RatingValidatie.validateRating(rating);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateRating_InvalidAboveMaximumRating() {
        double rating = 10.1;
        RatingValidatie.validateRating(rating);
    }

    // Edge cases
    @Test
    public void testValidateRating_ValidJustAboveMinimumRating() {
        double rating = 1.001;
        assertEquals(rating, RatingValidatie.validateRating(rating), 0.0);
    }

    @Test
    public void testValidateRating_ValidJustBelowMaximumRating() {
        double rating = 9.999;
        assertEquals(rating, RatingValidatie.validateRating(rating), 0.0);
    }
    
    
}
