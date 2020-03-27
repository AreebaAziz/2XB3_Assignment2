package cas2xb3_A2_aziz_aa;

import java.util.HashSet;
import java.util.Set;

public class Franchise {
	
	private Set<Meal> meals;
	private String name;
	
	public Franchise(String name) {
		this.name = name;
		meals = new HashSet<Meal>();
	}
	
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
