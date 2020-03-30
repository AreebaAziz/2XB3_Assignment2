/**
 * @author Areeba Aziz
 * March 29, 2020
 * SFRWENG 2XB3 Assignment 4
 */

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

	/**
	 * @param city The String city this node represents.
	 * @param lat The double latitude of this city.
	 * @param lon The double longitude of this city.
	 */
	public S34Node(String city, double lat, double lon) {
		super(city);
		this.lon = lon;
		this.lat = lat;
		edges = new HashMap<S34Node, PriorityQueue<S34Edge>>();
		restaurants = new HashSet<Restaurant>();
		franchises = new HashSet<Franchise>();
	}
	
	/**
	 * @param edge Add a connected edge to this node.
	 */
	public void addEdge(S34Edge edge) {
		// check if this edge's dst node already exists in this node's edges map
		if (!edges.containsKey(edge.dst())) {
			edges.put(edge.dst(), new PriorityQueue<S34Edge>());
		}
		edges.get(edge.dst()).add(edge); // O(logn) operation
	}
	
	/**
	 * @param dst Given a destination node, return the minimum edge that connects
	 * this node and the given node.
	 * @return S34Edge the minimum edge.
	 */
	public S34Edge peekMinEdge(S34Node dst) {
		// O(1) operation
		return edges.get(dst).peek();
	}
	
	/**
	 * @param dst Remove the minimum edge that connects this node and the given node.
	 * @return S34Edge the edge that is removed.
	 */
	public S34Edge removeMinEdge(S34Node dst) {
		// O(logn) operation
		return edges.get(dst).remove();
	}
	
	/**
	 * @return The set of outgoing edges from this node.
	 */
	public Set<S34Node> outgoingConnectedNodes() {
		return edges.keySet();
	}
	
	/**
	 * @param Restaurant r A restaurant object to add to this node.
	 */
	public void addRestaurant(Restaurant r) {
		restaurants.add(r);
		franchises.add(r.franchise());
	}
	
	/**
	 * @return The set of franchises available in this city.
	 */
	public Set<Franchise> franchises() {
		return franchises;
	}
	
	/**
	 * @return The set of individual restaurants available in this city.
	 */
	public Set<Restaurant> restaurants() {
		return restaurants;
	}
	
	/**
	 * @return This city's longitude.
	 */
	public double lon() {
		return lon;
	}
	
	/**
	 * @return This city's latitude.
	 */
	public double lat() {
		return lat;
	}
	
	/**
	 * @return This city's name.
	 */
	public String name() {
		return city;
	}
	
	@Override
	public String toString() {
		return city;
	}

}
