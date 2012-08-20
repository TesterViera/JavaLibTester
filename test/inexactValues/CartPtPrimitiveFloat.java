package inexactValues;

/**
 * A sample class that contains fields of the type <code>float</code>.
 * Represents a point in 2D Euclidean space.
 *
 */
class CartPtPrimitiveFloat {
    // x-coordinate of the point
    float x;
    // y-coordinate of the point
    float y;

    /**
     * Construct the instance of
     * <CODE>{@link testertester.inexact.CartPtPrimitiveFloat CartPtPrimitiveFloat}</CODE>
     * class.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    CartPtPrimitiveFloat(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Compute the distance to the origin of the coordinate system.
     *
     * @return distance to the origin
     */
    float distTo0() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    /**
     * Compute the distance from this point to the given point.
     *
     * @param that the <CODE>{@link testertester.inexact.CartPtDouble point}</CODE> to compute distance to
     * @return distance to that point
     */
    float distTo(CartPtDouble that) {
        return (float) Math.sqrt((this.x - that.x) * (this.x - that.x) +
                (this.y - that.y) * (this.y - that.y));
    }
}
