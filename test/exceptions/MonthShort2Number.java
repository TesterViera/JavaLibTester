package exceptions;

/**
 * A class to convert a short String representation of a month
 * into a number representation
 * E.g. "Feb" -> 2. 
 *
 */

class MonthShort2Number implements IMonth2Number {
    public int getMonthNumber(String month) {
        if (month.equals("Jan")) {
            return 1;
        } else if (month.equals("Feb")) {
            return 2;
        } else if (month.equals("Mar")) {
            return 3;
        } else if (month.equals("Apr")) {
            return 4;
        } else if (month.equals("May")) {
            return 5;
        } else if (month.equals("Jun")) {
            return 6;
        } else if (month.equals("Jul")) {
            return 7;
        } else if (month.equals("Aug")) {
            return 8;
        } else if (month.equals("Sep")) {
            return 9;
        } else if (month.equals("Oct")) {
            return 10;
        } else if (month.equals("Nov")) {
            return 11;
        } else if (month.equals("Dec")) {
            return 12;
        } else {
            return 13;
        }
    }
}