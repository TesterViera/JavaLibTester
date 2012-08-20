package basicTypes;

/**
 * A class to represent a Cartesian point with 
 * <code>Float</code> coordinates
 * 
 * @author Virag Shah
 * @since 16 February 2011
 */
public class CartPtF {
	/** the x coordinate */
	public Float x;
    /** the y coordinate */
    public Float y;
    
    /** Constructor
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public CartPtF(Float x, Float y) {
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