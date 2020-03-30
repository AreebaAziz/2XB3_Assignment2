/**
 * @author Areeba Aziz
 * March 29, 2020
 * SFRWENG 2XB3 Assignment 4
 */

package cas2xb3_A2_aziz_aa;

public class S32Node {
	
	protected String city;
	
	/**
	 * @param city String the city this node represents.
	 */
	public S32Node(String city) {
		this.city = city;
	}
	
	@Override
	public boolean equals(Object other) {
	    return other != null && other instanceof S32Node && this.city == ((S32Node) other).city;
	}
	
	@Override
	public String toString() {
		return city;
	}
	
}
