package TestMethode;

public class RatingValidatie {

    public static double validateRating(double rating) {
        if (rating < 1 || rating > 10) {
            throw new IllegalArgumentException("Beordeling moet tussen de 1 en 10 zijn.");
        }
        return rating;
    }
}
