package exceptions;

/**
 * A class to convert a long String representation of a month
 * into a number representation
 * E.g. "March" -> 3
 *
 */
class MonthLong2Number implements IMonth2Number {
	
	public int getMonthNumber(String month) {
        if (month.equals("January")) {
            return 1;
        } else if (month.equals("February")) {
            return 2;
        } else if (month.equals("March")) {
            return 3;
        } else if (month.equals("April")) {
            return 4;
        } else if (month.equals("May")) {
            return 5;
        } else if (month.equals("June")) {
            return 6;
        } else if (month.equals("July")) {
            return 7;
        } else if (month.equals("August")) {
            return 8;
        } else if (month.equals("September")) {
            return 9;
        } else if (month.equals("October")) {
            return 10;
        } else if (month.equals("November")) {
            return 11;
        } else if (month.equals("December")) {
            return 12;
        } else {
            return 13;
        }
    }
}
