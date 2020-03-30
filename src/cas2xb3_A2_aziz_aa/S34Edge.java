/**
 * @author Areeba Aziz
 * March 29, 2020
 * SFRWENG 2XB3 Assignment 4
 */

package cas2xb3_A2_aziz_aa;

public class S34Edge implements Comparable<S34Edge>{
	
	private S34Node src, dst;
	private Meal meal;
	
	/**
	 * @param src The source node that this edge is connected from.
	 * @param dst The destination node that this edge connects to.
	 * @param meal The meal that is represented by this edge. 
	 */
	public S34Edge(S34Node src, S34Node dst, Meal meal) {
		this.src = src;
		this.dst = dst;
		this.meal = meal;
	}
	
	/**
	 * @return the source node of this edge.
	 */
	public S34Node src() {
		return src;
	}
	
	/**
	 * @return the destination node of this edge.
	 */
	public S34Node dst() {
		return dst;
	}
	
	/**
	 * @return the meal represented by this edge.
	 */
	public Meal meal() {
		return meal;
	}
	
	/**
	 * @return the cost of this edge, which equals the cost of this edge's meal.
	 */
	public double cost() {
		return meal.cost();
	}
	
	/**
	 * Returns -1 if this edge is < otherEdge
	 * Returns 0 if this edge is = otherEdge
	 * Returns 1 if this edge is > otherEdge
	 * Comparison is based on edge's meal costs.
	 */
	public int compareTo(S34Edge otherEdge) {
		if (meal.cost() > otherEdge.meal.cost()) return 1;
		else if (meal.cost() == otherEdge.meal.cost()) return 0;
		else return -1;
	}
	
	@Override
	public String toString() {
		return "" + src + " -> " + dst + " " + meal;
	}
}
