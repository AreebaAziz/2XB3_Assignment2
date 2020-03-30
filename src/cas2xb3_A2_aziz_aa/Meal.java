/**
 * @author Areeba Aziz
 * March 29, 2020
 * SFRWENG 2XB3 Assignment 4
 */

package cas2xb3_A2_aziz_aa;

public class Meal {
	
	private String name;
	private Franchise franchise;
	private double cost;
	
	/**
	 * @param name The name of this meal.
	 * @param franchise The franchise that this meal belongs to.
	 * @param cost The cost of this meal.
	 */
	public Meal(String name, Franchise franchise, double cost) {
		this.name = name;
		this.franchise = franchise;
		this.cost = cost;
	}
	
	public String name() {
		return name;
	}
	
	public Franchise franchise() {
		return franchise;
	}
	
	public double cost() {
		return cost;
	}
	
	@Override
	public String toString() {
		return franchise + " " + name + "[$" + cost + "]";
	}

}
