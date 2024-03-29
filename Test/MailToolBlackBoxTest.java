package Test;

import TestMethode.MailTools;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MailToolBlackBoxTest {

    @Test
    public void testEmptyString() {
        assertFalse(MailTools.validateMailAddress(""));
    }

    @Test
    public void testNullInput() {
        assertFalse(MailTools.validateMailAddress(null));
    }

    @Test
    public void testMissingAtCharacter() {
        assertFalse(MailTools.validateMailAddress("userexample.com"));
    }

    @Test
    public void testMultipleAtCharacters() {
        assertFalse(MailTools.validateMailAddress("user@@example.com"));
    }

    @Test
    public void testLeadingAtCharacter() {
        assertFalse(MailTools.validateMailAddress("@example.com"));
    }

    @Test
    public void testTrailingAtCharacter() {
        assertFalse(MailTools.validateMailAddress("user@"));
    }

    @Test
    public void testMissingTopLevelDomain() {
        assertFalse(MailTools.validateMailAddress("user@example"));
    }

    @Test
    public void testSingleCharacterInEachSection() {
        assertTrue(MailTools.validateMailAddress("u@e.c"));
    }

    @Test
    public void testValidEmail() {
        assertTrue(MailTools.validateMailAddress("user@example.com"));
    }

    @Test
    public void testEmailWithSubdomain() {
        assertTrue(MailTools.validateMailAddress("user@sub.example.com"));
    }

    @Test
    public void testEmailWithDigits() {
        assertTrue(MailTools.validateMailAddress("user123@example.com"));
    }

    @Test
    public void testEmailWithSpecialCharacters() {
        assertTrue(MailTools.validateMailAddress("user.name+123@example.com"));
    }

    @Test
    public void testEmailWithInvalidCharacters() {
        assertFalse(MailTools.validateMailAddress("user?name@example.com"));
    }

    @Test
    public void testEmailWithUpperCase() {
        assertTrue(MailTools.validateMailAddress("User@Example.Com"));
    }

    @Test
    public void testLongDomain() {
        assertTrue(MailTools.validateMailAddress("user@example.solutions"));
    }
}
