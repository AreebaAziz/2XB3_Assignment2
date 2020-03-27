package cas2xb3_A2_aziz_aa;

public class Meal {
	
	private String name;
	private Franchise franchise;
	private double cost;
	
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
