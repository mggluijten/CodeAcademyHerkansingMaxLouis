package TestMethode;

public class MailTools {

    /**
     * Validates if mailAddress is valid. It should be in the form of:
     * <at least 1 character>@<at least 1 character>.<at least 1 character>
     *
     * @param mailAddress The email address to validate.
     * @return true if the email address is valid, false otherwise.
     */
    public static boolean validateMailAddress(String mailAddress) {
        if (mailAddress == null) {
            return false;
        }

        if (!mailAddress.contains("@") || mailAddress.split("@")[0].length() < 1) {
            return false; 
        }

        if (!mailAddress.contains("@") || mailAddress.split("@")[1].split("\\.").length > 2) {
            return false; 
        }

        if (!mailAddress.contains("@") || mailAddress.split("@")[1].split("\\.")[0].length() < 1) {
            return false; 
        }

        if (!mailAddress.contains("@")) {
            return false; 
        }
        
        String[] partsAfterAt = mailAddress.split("@")[1].split("\\.");
        if (partsAfterAt.length < 2 || partsAfterAt[1].length() < 1) {
            return false; 
        }
        

        return true; 
    }
}
