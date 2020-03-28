package cas2xb3_A2_aziz_aa;

public class S34Edge implements Comparable<S34Edge>{
	
	private S34Node src, dst;
	private Meal meal;
	
	public S34Edge(S34Node src, S34Node dst, Meal meal) {
		this.src = src;
		this.dst = dst;
		this.meal = meal;
	}
	
	public S34Node src() {
		return src;
	}
	
	public S34Node dst() {
		return dst;
	}
	
	public Meal meal() {
		return meal;
	}
	
	public double cost() {
		return meal.cost();
	}
	
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
