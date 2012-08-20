package basicTypes;

/**
 * A class to represent a Cartesian point with 
 * <code>float</code> coordinates
 * 
 * @author Virag Shah
 * @since 16 February 2011
 *
 */
public class CartPtfloat{
  
  /** the x coordinate */
  public float x;
  /** the y coordinate */
  public float y;
  
  public CartPtfloat(float x, float y){
    this.x = x;
    this.y = y;
  }
   
  /**
   * Compute the distance of this point to the origin
   * @return the desired distance
   */
  public double distTo0(){
    return Math.sqrt(this.x * this.x + this.y * this.y);
  }
}