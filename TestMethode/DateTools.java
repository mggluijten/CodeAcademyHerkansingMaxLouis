package TestMethode;

import java.time.LocalDate;

public class DateTools {

    public static boolean validateDate(int day, int month, int year) {
        if ((month == 1 || month == 3 || month == 5 || month == 7 || 
             month == 8 || month == 10 || month == 12) && (1 <= day && day <= 31)) {
            return true; //31 dagen in een maand
        } else if ((month == 4 || month == 6 || month == 9 || month == 11) && (1 <= day && day <= 30)) {
            return true; //30 dagen in een maand
        } else if (month == 2 && (1 <= day && day <= 29) && (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))) {
            return true; //29 days in maand met een schrikkeljaar
        } else if (month == 2 && (1 <= day && day <= 28) && (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0))) {
            return true; //28 dagen in een maand zonder schrikkeljaar
        } else {
            return false; //Overige resultaten
        }
    }

    //Methode om te checken of de datum vandaag of in de toekomst is.
    public static boolean validateAndCheckFutureDate(int year, int month, int day) {
        if (!validateDate(day, month, year)) {
            return false; 
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate inputDate = LocalDate.of(year, month, day);

        return !inputDate.isBefore(currentDate);
    }
}




