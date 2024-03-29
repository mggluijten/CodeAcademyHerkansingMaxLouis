package TestMethode;

public class PostalCode {
    
    public static String formatPostalCode(String postalCode) {
        if (postalCode == null) {
            throw new NullPointerException("Vul een geldige postcode in.");
        }

        postalCode = postalCode.trim();

        if (postalCode.length() != 7 || postalCode.charAt(4) != ' ') {
            throw new IllegalArgumentException("Vul een geldige postcode in.");
        }

        int numericPart = Integer.parseInt(postalCode.substring(0, 4));
        String letterPart = postalCode.substring(5).toUpperCase();

        if (numericPart < 1000 || numericPart > 9999 || letterPart.length() != 2
                || letterPart.charAt(0) < 'A' || letterPart.charAt(0) > 'Z'
                || letterPart.charAt(1) < 'A' || letterPart.charAt(1) > 'Z') {
            throw new IllegalArgumentException("Vul een geldige postcode in.");
        }

        return numericPart + " " + letterPart;
    }
}
