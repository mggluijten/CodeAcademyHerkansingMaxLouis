package Test;

import org.junit.Test;

import TestMethode.MailTools;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;

import static org.junit.Assert.assertFalse;


public class MailToolTest {
    
    /**
     * @desc Validates if mailAddress is valid. It should be in the form of:
     *       <at least 1 character>@<at least 1 character>.<at least 1 character>
     * 
     * @subcontract no mailbox part {
     *   @requires !mailAddress.contains("@") ||
     *             mailAddress.split("@")[0].length < 1;
     *   @ensures \result = false;
     * }
     * 
     * @subcontract subdomain-tld delimiter {
     *   @requires !mailAddress.contains("@") ||
     *             mailAddress.split("@")[1].split(".").length > 2;
     *   @ensures \result = false;
     * }
     * 
     * @subcontract no subdomain part {
     *   @requires !mailAddress.contains("@") ||
     *             mailAddress.split("@")[1].split(".")[0].length < 1;
     *   @ensures \result = false;
     * }
     * 
     * @subcontract no tld part {
     *   @requires !mailAddress.contains("@") ||
     *             mailAddress.split("@")[1].split(".")[1].length < 1;
     *   @ensures \result = false;
     * }
     * 
     * @subcontract valid email {
     *   @requires no other precondition
     *   @ensures \result = true;
     * }
     * 
     */
    @Test
        public void testNoMailboxPart() {
            // Arrange
            String mailAddress = "";
            
            // Act
            boolean result = MailTools.validateMailAddress(mailAddress);
            
            // Assert
            Assert.assertFalse(result);
        }



        @Test
        public void testSubdomainTldDelimiter() {
            // Arrange
            String mailAddress = "user@subdomain.example.com";
            
            // Act
            boolean result = MailTools.validateMailAddress(mailAddress);
            
            // Assert
            Assert.assertFalse(result);
        }

        @Test
        public void testNoSubdomainPart() {
            // Arrange
            String mailAddress = "user@.example.com";
            
            // Act
            boolean result = MailTools.validateMailAddress(mailAddress);
            
            // Assert
            Assert.assertFalse(result);
        }

        @Test
        public void testNoTldPart() {
            // Arrange
            String mailAddress = "user@subdomain.";
            
            // Act
            boolean result = MailTools.validateMailAddress(mailAddress);
            
            // Assert
            Assert.assertFalse(result);
        }

        @Test
        public void testValidEmail() {
            // Arrange
            String mailAddress = "user@example.com";
            
            // Act
            boolean result = MailTools.validateMailAddress(mailAddress);
            
            // Assert
            Assert.assertTrue(result);
        }
    }
