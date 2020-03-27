package cas2xb3_A2_aziz_aa;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class S34Node extends S32Node {
	
	private Set<S34Edge> edges;
	private double lon, lat;
	private Set<Restaurant> restaurants;
	private Set<Franchise> franchises;

	public S34Node(String city, double lat, double lon) {
		super(city);
		this.lon = lon;
		this.lat = lat;
		edges = new HashSet<S34Edge>();
		restaurants = new HashSet<Restaurant>();
		franchises = new HashSet<Franchise>();
	}
	
	public void addEdge(S34Edge edge) {
		edges.add(edge);
	}
	
	public void removeEdge(S34Edge edge) {
		edges.remove(edge);
	}
	
	public Set<S34Edge> edges() {
		return edges;
	}
	
	public void addRestaurant(Restaurant r) {
		restaurants.add(r);
		franchises.add(r.franchise());
	}
	
	public Set<Franchise> franchises() {
		return franchises;
	}
	
	public Set<Restaurant> restaurants() {
		return restaurants;
	}
	
	public double lon() {
		return lon;
	}
	
	public double lat() {
		return lat;
	}
	
	public String name() {
		return city;
	}
	
	@Override
	public String toString() {
		return city + " [" + lat + ", " + lon + "]";
	}

}
