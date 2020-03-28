package cas2xb3_A2_aziz_aa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class S34Node extends S32Node {
	
	private Map<S34Node, PriorityQueue<S34Edge>> edges;
	private double lon, lat;
	private Set<Restaurant> restaurants;
	private Set<Franchise> franchises;

	public S34Node(String city, double lat, double lon) {
		super(city);
		this.lon = lon;
		this.lat = lat;
		edges = new HashMap<S34Node, PriorityQueue<S34Edge>>();
		restaurants = new HashSet<Restaurant>();
		franchises = new HashSet<Franchise>();
	}
	
	public void addEdge(S34Edge edge) {
		// check if this edge's dst node already exists in this node's edges map
		if (!edges.containsKey(edge.dst())) {
			edges.put(edge.dst(), new PriorityQueue<S34Edge>());
		}
		edges.get(edge.dst()).add(edge); // O(logn) operation
	}
	
	public S34Edge peekMinEdge(S34Node dst) {
		// O(1) operation
		return edges.get(dst).peek();
	}
	
	public S34Edge removeMinEdge(S34Node dst) {
		// O(logn) operation
		return edges.get(dst).remove();
	}
	
	public Set<S34Node> outgoingConnectedNodes() {
		return edges.keySet();
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
		return city;
	}

}
