/**
 * @author Areeba Aziz
 * March 29, 2020
 * SFRWENG 2XB3 Assignment 4
 */

package cas2xb3_A2_aziz_aa;

import java.util.HashSet;
import java.util.Set;

public class Franchise {
	
	private Set<Meal> meals;
	private String name;
	
	/**
	 * @param name The name of this franchise.
	 */
	public Franchise(String name) {
		this.name = name;
		meals = new HashSet<Meal>();
	}
	
	/**
	 * @param meal A meal to add to this franchise. 
	 */
	public void addMeal(Meal meal) {
		meals.add(meal);
	}
	
	public Set<Meal> meals() {
		return meals;
	}
	
	public String name() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
