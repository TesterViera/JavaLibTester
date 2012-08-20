package exceptions;

/**
 * A class to represent a calendar date
 *
 */
class Date {
	// define inner variables
    int year;
    int month;
    int day;

    /**
     * <P> Define error message for <I>Invalid Year</I> exception </P>
     */
    String MSG_INVALID_YEAR_IN_DATE = "Invalid year in Date";

    /**
     * <P> Define error message for <I>Invalid Month</I> exception </P>
     */
    String MSG_INVALID_MONTH_IN_DATE = "Invalid month in Date";

    /**
     * <P> Define error message for <I>Invalid Day</I> exception </P>
     */
    String MSG_INVALID_DAY_IN_DATE = "Invalid day in Date";

    /**
     * Constructs a date given a year, month and day
     *
     * @param year year, should be between 1200 and 3000
     * @param month a number representation of month (1, 2, ..., 12)
     * @param day day (1, 2, ..., 31)
     *
     * @throws IllegalArgumentException if invalid year, month or day
     * were provided
     */
    Date(int year, int month, int day) {
        // check year
        if (this.validYear(year))
            this.year = year;
        else
            throw new IllegalArgumentException(MSG_INVALID_YEAR_IN_DATE);

        // check month
        if (this.validMonth(month))
            this.month = month;
        else
            throw new IllegalArgumentException(MSG_INVALID_MONTH_IN_DATE);

        // check day
        if (this.validDay(day))
            this.day = day;
        else
            throw new IllegalArgumentException(MSG_INVALID_DAY_IN_DATE);
    }


    /**
     * Constructs a date given month and day. Year is set to 2010.
     *
     * @param month a number representation of month (1, 2, ..., 12)
     * @param day day (1, 2, ..., 31)
     *
     * @throws IllegalArgumentException if invalid month or day
     * were provided
     */
    Date(int month, int day) {
        this(2010, month, day);
    }

    /**
     * Constructs a date given a year, month and day
     *
     * @param year year, should be between 1200 and 3000
     * @param month a String representation of month
     * @param day day (1, 2, ..., 31)
     * @param m2n a converter of type <CODE>{@link IMonth2Number IMonth2Number} </CODE>
     *
     * @throws IllegalArgumentException if invalid year, month or day
     * were provided
     */
    Date(int year, String month, int day, IMonth2Number m2n) {
        if (this.validYear(year))
            this.year = year;
        else
            throw new IllegalArgumentException(MSG_INVALID_YEAR_IN_DATE);

        int monthNo = m2n.getMonthNumber(month);
        if (this.validMonth(monthNo))
            this.month = monthNo;
        else
            throw new IllegalArgumentException(MSG_INVALID_MONTH_IN_DATE);

        if (this.validDay(day))
            this.day = day;
        else
            throw new IllegalArgumentException(MSG_INVALID_DAY_IN_DATE);
    }


    /**
     * Constructs a date given month and day. Year is set to 2010.
     *
     * @param month a String representation of month
     * @param day day (1, 2, ..., 31)
     * @param m2n a converter of type <CODE>{@link IMonth2Number IMonth2Number} </CODE>
     *
     * @throws IllegalArgumentException if invalid month or day
     * were provided
     */
    Date(String month, int day, IMonth2Number m2n) {
        this(2010, month, day, m2n);
    }


    /**
     * Check if the year is in allowed range
     * @param year year, should be between 1200 and 3000
     *
     * @throws IllegalArgumentException if invalid year was provided
     */
    boolean validYear(int year) {
        if (this.within(1200, year, 3000))
            return true;
        else
            throw new IllegalArgumentException(MSG_INVALID_YEAR_IN_DATE);
    }

    /**
     * Check if the month is in allowed range
     * @param month month, should be between 1 and 12
     *
     * @throws IllegalArgumentException if invalid month was provided
     */
    boolean validMonth(int month) {
        if (this.within(1, month, 12))
            return true;
        else
            throw new IllegalArgumentException(MSG_INVALID_MONTH_IN_DATE);
    }

    /**
     * Check if the day is in allowed range
     * @param day day, should be between 1 and 31
     *
     * @throws IllegalArgumentException if invalid day was provided
     */
    boolean validDay(int day) {
        if (this.within(1, day, 31))
            return true;
        else
            throw new IllegalArgumentException(MSG_INVALID_DAY_IN_DATE);
    }

    /**
     * Check if the number is between two other.
     * @param low lower bound
     * @param num mumber to check
     * @param high higher bound
     *
     * @throws IllegalArgumentException if invalid year was provided
     */
    boolean within(int low, int num, int high) {
        return low <= num && num <= high;
    }
}
