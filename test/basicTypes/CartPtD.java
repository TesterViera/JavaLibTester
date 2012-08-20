package basicTypes;

/**
 * A class to represent a Cartesian point with 
 * <code>Double</code> coordinates
 * 
 * @author Virag Shah
 * @since 16 February 2011
 */
public class CartPtD {
	/** the x coordinate */
	public Double x;
    /** the y coordinate */
    public Double y;
    
    /** Constructor
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public CartPtD(Double x, Double y) {
    	this.x = x;
    	this.y = y;
    }
    
    /**
     * Compute the distance of this point to the origin
     * @return the desired distance
     */
    public double distTo0() {
    	return Math.sqrt(this.x * this.x + this.y * this.y);
    }
}