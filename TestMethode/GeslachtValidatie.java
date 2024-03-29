package TestMethode;

public class GeslachtValidatie{

    public static boolean validateGeslacht(String userGeslacht) {
        return userGeslacht != null && userGeslacht.trim().matches("[MVX]");
    }
    
}