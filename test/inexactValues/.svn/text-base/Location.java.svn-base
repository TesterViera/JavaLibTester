package inexactValues;

/**
 * A sample class that defines a method that produces a <code>double</code>
 * value.
 *
 */
class Location {
    // the street ordinal number
    int street;
    //the avenue ordinal number
    int avenue;

    /**
     * Construct the instance of <CODE>{@link testertester.inexact.Location Location}</CODE> class.
     *
     * @param street the street ordinal number
     * @param avenue the avenue ordinal number
     */
    Location(int street, int avenue) {
        this.street = street;
        this.avenue = avenue;
    }

    /**
     * Compute the walking distance from this to the given location.
     *
     * @param that the <CODE>{@link testertester.inexact.Location Location}</CODE> to compute distance to
     */
    int walkDistTo(Location that) {
        return Math.abs(this.street - that.street) +
               Math.abs(this.avenue - that.avenue);
    }

   /**
     * Compute the 'as the crow flies' distance from this to the given location.
     *
     * @param that the  <CODE>{@link testertester.inexact.Location Location}</CODE> to compute distance to
     */
    double flyDistTo(Location that) {
        return Math.sqrt((this.street - that.street) * (this.street - that.street) +
                (this.avenue - that.avenue) * (this.avenue - that.avenue));
    }
}
