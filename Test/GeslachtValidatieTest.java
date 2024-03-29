package Test;

import org.junit.Assert;
import org.junit.Test;
import TestMethode.GeslachtValidatie;

public class GeslachtValidatieTest {
        /**
         * @desc Validates if userGeslacht is a valid gender. It should be one of: M, V, X.
         *
         * @subcontract geldig geslacht M {
         *   @requires userGeslacht.equals("M");
         *   @ensures \result = true;
         * }
         */
        @Test
        public void testGeldigGeslachtM() {
            // Arrange
            String userGeslacht = "M";
    
            // Act
            boolean result = GeslachtValidatie.validateGeslacht(userGeslacht);
    
            // Assert
            Assert.assertTrue(result);
        }
    
        /**
         * @desc Validates if userGeslacht is a valid gender. It should be one of: M, V, X.
         *
         * @subcontract geldig geslacht V {
         *   @requires userGeslacht.equals("V");
         *   @ensures \result = true;
         * }
         */
        @Test
        public void testGeldigGeslachtV() {
            // Arrange
            String userGeslacht = "V";
    
            // Act
            boolean result = GeslachtValidatie.validateGeslacht(userGeslacht);
    
            // Assert
            Assert.assertTrue(result);
        }
    
        /**
         * @desc Validates if userGeslacht is a valid gender. It should be one of: M, V, X.
         *
         * @subcontract geldig geslacht X {
         *   @requires userGeslacht.equals("X");
         *   @ensures \result = true;
         * }
         */
        @Test
        public void testGeldigGeslachtX() {
            // Arrange
            String userGeslacht = "X";
    
            // Act
            boolean result = GeslachtValidatie.validateGeslacht(userGeslacht);
    
            // Assert
            Assert.assertTrue(result);
        }
    
        /**
         * @desc Validates if userGeslacht is a valid gender. It should be one of: M, V, X.
         *
         * @subcontract ongeldig geslacht {
         *   @requires !userGeslacht.matches("[MVX]");
         *   @ensures \result = false;
         * }
         */
        @Test
        public void testOngeldigGeslacht() {
            // Arrange
            String userGeslacht = "Y";
    
            // Act
            boolean result = GeslachtValidatie.validateGeslacht(userGeslacht);
    
            // Assert
            Assert.assertFalse(result);
        }  
}
