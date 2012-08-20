package inexactValues;

/**
 * A sample class that contains fields of the type <code>double</code>.
 * Represents a point in 2D Euclidean space.
 *
 */
class CartPtPrimitiveDouble {
    // x-coordinate of the point
    double x;
    // y-coordinate of the point
    double y;


    /**
     * Construct the instance of
     * <CODE>{@link testertester.inexact.CartPtPrimitiveDouble CartPtPrimitiveDouble}</CODE>
     * class.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    CartPtPrimitiveDouble(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Compute the distance to the origin of the coordinate system.
     *
     * @return distance to the origin
     */
    double distTo0() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    /**
     * Compute the distance from this point to the given point.
     *
     * @param that the <CODE>{@link testertester.inexact.CartPtDouble point}</CODE> to compute distance to
     * @return distance to that point
     */
    double distTo(CartPtDouble that) {
        return Math.sqrt((this.x - that.x) * (this.x - that.x) +
                (this.y - that.y) * (this.y - that.y));
    }
}
