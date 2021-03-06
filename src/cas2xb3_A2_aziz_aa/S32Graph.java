/**
 * @author Areeba Aziz
 * March 29, 2020
 * SFRWENG 2XB3 Assignment 4
 */

package cas2xb3_A2_aziz_aa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class S32Graph {
	
	private String connectedCitiesFile;	// the file path for connectedCities.txt
	private HashMap<S32Node, ArrayList<S32Node>> graph;	// the actual graph adjancy list
	private HashMap<String, S32Node> cities; 
	
	/**
	 * @param connectedCities String the path to file for connectedCities.txt
	 */
	public S32Graph(String connectedCities) {
		connectedCitiesFile = connectedCities;
		graph = new HashMap<S32Node, ArrayList<S32Node>>();	// initialize empty graph
		cities = new HashMap<String, S32Node>();
	}
	
	/**
	 * @param fromCity
	 * @param toCity
	 * @return boolean whether fromCity has an outgoing edge to toCity
	 */
	public boolean isConnected(String fromCity, String toCity) {
		S32Node from = cities.get(fromCity.toUpperCase());
		S32Node to = cities.get(toCity.toUpperCase());
		return from != null
				&& to != null
				&& graph.get(from).contains(to); 
	}
	
	/**
	 * @return the set of nodes in the graph.
	 */
	public Set<S32Node> nodes() {
		return graph.keySet();
	}
	
	/**
	 * Builds the graph given the data in the connectedCities file.
	 */
	public void build() {
		// read the connectedCities file line by line and add data to graph
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(connectedCitiesFile));
			String line = reader.readLine();
			while (line != null) {
				if (line.trim() != "") {
					addToGraph(line.split(",")[0].trim().toUpperCase(), line.split(",")[1].trim().toUpperCase());
				}
				
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param src The source node
	 * @param dst The destination node
	 * Adds a connection to the graph.
	 */
	private void addToGraph(String src, String dst) {
		// if the src or dst city is not made into an S32Node yet, create it and add it to graph keys.
		S32Node srcNode, dstNode;
		if (!cities.containsKey(src)) {
			srcNode = new S32Node(src);
			cities.put(src, srcNode);
			graph.put(srcNode, new ArrayList<S32Node>());
		} else srcNode = cities.get(src);
		
		if (!cities.containsKey(dst)) {
			dstNode =  new S32Node(dst);
			cities.put(dst, dstNode);
			graph.put(dstNode, new ArrayList<S32Node>());
		} else dstNode = cities.get(dst);
		
		// now add the dst node to the set of the src node as long as the dst node
		// doesn't already exist in the set.
		if (!graph.get(srcNode).contains(dstNode)) graph.get(srcNode).add(dstNode);
	}
	
	@Override
	public String toString() {
		String str = "";
		for (S32Node src : graph.keySet()) {
			str += src + ": ";
			for (S32Node dst : graph.get(src)) {
				str += dst + ", ";
			}
			str += "\n";
		}
		return str;
	}

	/**
	 * @param startCity
	 * @param endCity
	 * @return The first path from startCity to endCity found from BFS.
	 */
	public String findPathBFS(String startCity, String endCity) {
		startCity = startCity.toUpperCase();
		endCity = endCity.toUpperCase();
		
		// if either the start city or end city don't exist in the graph, return nothing
		if (!cities.containsKey(startCity) || !cities.containsKey(endCity)) return "";
		
		HashSet<S32Node> visited = new HashSet<S32Node>();	// track visited nodes
		HashMap<S32Node, S32Node> parents = new HashMap<S32Node, S32Node>(); // track parents of each node
		
		Queue<S32Node> queue = new LinkedList<S32Node>();	// the queue for BFS
		queue.add(cities.get(startCity));
		
		S32Node node = null;
		
		// while queue isn't empty and the destination city isn't found
		while (!queue.isEmpty() && !(node == cities.get(endCity))) {	
			node = queue.remove(); 	// remove node from queue
			visited.add(node);
			for (S32Node neighbour : graph.get(node)) {	// visit node's neighbours
				if (!visited.contains(neighbour)) {
					parents.put(neighbour, node);
					queue.add(neighbour);	// add neighbour to queue if not yes visited
				}
			}
		}
		
		if (node == cities.get(endCity)) {
			// the destination city was found
			ArrayList<S32Node> path = new ArrayList<S32Node>();
			path.add(node);
			while (node != cities.get(startCity)) {
				path.add(parents.get(node));
				node = parents.get(node);
			}
			
			// return the path as string
			String pathStr = "";
			for (int i = path.size() - 1; i >= 1; i--) {
				pathStr += path.get(i) + ", ";
			}
			pathStr += path.get(0);
			return pathStr;
		}
		
		
		return "";	// if no path found, return an empty string
	}

	/**
	 * @param startCity
	 * @param endCity
	 * @return The first path found from startCity to endCity from DFS.
	 */
	public String findPathDFS(String startCity, String endCity) {
		startCity = startCity.toUpperCase();
		endCity = endCity.toUpperCase();
		
		// if either the start city or end city don't exist in the graph, return nothing
		if (!cities.containsKey(startCity) || !cities.containsKey(endCity)) return "";
		
		HashSet<S32Node> visited = new HashSet<S32Node>();	// track visited nodes
		ArrayList<S32Node> path = new ArrayList<S32Node>();
				
		// recursive method for DFS
		findPathDFS(cities.get(startCity), cities.get(endCity), visited, path);

		// return the path as string
		String pathStr = "";
		for (int i = path.size() - 1; i >= 1; i--) {	// reverse the path found 
			pathStr += path.get(i) + ", ";
		}
		
		if (path.size() > 0) pathStr += path.get(0);
		return pathStr;
	}
	
	private boolean findPathDFS(S32Node src, S32Node dst, Set<S32Node> visited, List<S32Node> path) {
		// recursive DFS method
		visited.add(src);	// add this node to the set of visited nodes
		if (src != dst) {	// if this node isn't the destination node
			for (S32Node neighbour : graph.get(src)) {	
				if (!visited.contains(neighbour)) {
					if (findPathDFS(neighbour, dst, visited, path)) {	// recurse on each neighbour
						path.add(src);
						return true;	// if path found, return true
					}
				}
			}
			return false;
		}
		path.add(src);
		return true;
	}
}
