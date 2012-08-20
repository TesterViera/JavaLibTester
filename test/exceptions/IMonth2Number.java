package exceptions;

/**
 * A interface to convert a String representation of a month
 * into a number representation
 * E.g. "Feb" -> 2
 * or "March" -> 3
 *
 * The exact String representation relies on the
 * particular implementation of this interface
 */
interface IMonth2Number {
    /**
     * Convert String representation of a month into a number
     *
     * @param month String representation of a month
     */
    int getMonthNumber(String month);
}
