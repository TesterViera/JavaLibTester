package iterable;

/**
 * A sample class that contains fields of the type <code>Double</code>.
 * Represents a point in 2D Euclidean space.
 *
 */
class CartPtDouble {
	/**
	 * x-coordinate of the point
	 */
	Double x;
	/**
	 * y-coordinate of the point
	 */
	Double y;

	/**
	 * Construct the instance of <CODE>{@link testertester.inexact.CartPtDouble CartPtDouble}</CODE> 
	 * class.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	public CartPtDouble(Double x, Double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Compute the distance to the origin of the coordinate system.
	 *
	 * @return distance to the origin
	 */
	public Double distTo0() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}

	/**
	 * Compute the distance from this point to the given point.
	 *
	 * @param that the <CODE>{@link testertester.inexact.CartPtDouble point}</CODE> to compute distance to
	 * @return distance to that point
	 */
	public Double distTo(CartPtDouble that) {
		return Math.sqrt((this.x - that.x) * (this.x - that.x) +
				(this.y - that.y) * (this.y - that.y));
	}
}
