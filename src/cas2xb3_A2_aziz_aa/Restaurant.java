/**
 * @author Areeba Aziz
 * March 29, 2020
 * SFRWENG 2XB3 Assignment 4
 */

package cas2xb3_A2_aziz_aa;

public class Restaurant {

	private double lon, lat;
	private Franchise franchise;
	
	/**
	 * @param franchise The Franchise that this restaurant belongs to.
	 * @param lat
	 * @param lon
	 */
	public Restaurant(Franchise franchise, double lat, double lon) {
		this.franchise = franchise;
		this.lon = lon;
		this.lat = lat;
	}
	
	public double lon() {
		return lon;
	}
	
	public double lat() {
		return lat;
	}
	
	public Franchise franchise() {
		return franchise;
	}
	
	@Override
	public String toString() {
		return "" + franchise + " [" + lat + ", " + lon + "]";
	}
}
