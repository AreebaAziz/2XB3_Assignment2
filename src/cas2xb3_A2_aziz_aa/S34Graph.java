/**
 * @author Areeba Aziz
 */

package cas2xb3_A2_aziz_aa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class S34Graph {
	
	private String connectedCitiesFile;
	private String citiesFile;
	private String menuFile;
	private String restaurantsFiles;
	private Map<String, S34Node> cities;	// this is the graph. Each node has set of directed edges.
	
	public S34Graph(String connectedCities, String citiesFile, String menuFile, String restaurantsFiles) {
		this.connectedCitiesFile = connectedCities;
		this.citiesFile = citiesFile;
		this.menuFile = menuFile;
		this.restaurantsFiles = restaurantsFiles;
		cities = new HashMap<String, S34Node>();
	}
	
	public void build() {
		/*
		 * Algorithm to build the graph:
		 * 1. For each city, store a set of cities that have routes directed TO it.
		 * 2. For each city, store a set of restaurants in its vicinity by iterating through
		 * the list of restaurants and adding each restaurant to vicinity cities. 
		 * 3. For each franchise, store a set of meals by iterating through the list of meals
		 * and adding it to the corresponding franchise. 
		 * 4. Iterate through each city, add directed weighted edges to all cities that have routes
		 * pointed TO it based on the city's restaurants and meals. Add an edge for every meal of every
		 * restaurant in the dst city.
		 */
		
		// first parse the USCities.csv file to get the cities data. 
		parseCitiesData();
		
		// STEP 1: now go through the connectedCitiesFile and map each city to a set of cities that have routes directed TO it.
		Map<S34Node, HashSet<S34Node>> incomingEdges = new HashMap<S34Node, HashSet<S34Node>>();
		findIncomingEdges(incomingEdges);
		
		// STEP 2 
		Map<String, Franchise> franchises = new HashMap<String, Franchise>();
		assignRestaurantsToCities(franchises);
		
		// STEP 3
		assignFranchiseMeals(franchises);
		
		// STEP 4
		assignEdges(incomingEdges);
	}
	
	private void assignEdges(Map<S34Node, HashSet<S34Node>> incomingEdges) {
		// iterate through each city
		for (S34Node city : incomingEdges.keySet()) {
			// gather a list of all possible meals in this city. 
			Set<Meal> meals = new HashSet<Meal>();
			for (Franchise fr : city.franchises()) {
				meals.addAll(fr.meals());
			}
			
			// for each incoming city and for each meal, add an edge
			for (S34Node incomingCity : incomingEdges.get(city)) {
				for (Meal meal : meals) {
					incomingCity.addEdge(new S34Edge(incomingCity, city, meal));
				}
			}
		}
	}
	
	private void assignFranchiseMeals(Map<String, Franchise> franchises) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(menuFile));
			String line = reader.readLine();
			if (line != null) line = reader.readLine(); // skip first heading line
			while (line != null) {
				if (line.trim() != "") {
					// parse meal data
					String[] vals = line.split(",");
					Franchise fr = franchises.get(vals[0].replaceAll("’", "'"));
					String mealName = vals[1];
					double mealCost = Double.parseDouble(vals[2].replace("$", ""));
					
					// create new Meal object and add the meal to the franchise.
					Meal meal = new Meal(mealName, fr, mealCost);
					fr.addMeal(meal);
				}
				
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void assignRestaurantsToCities(Map<String, Franchise> franchises) {
		for (String franchise : restaurantsFiles.split(",")) {
			String fName = franchise.split("\\|")[0];
			Franchise fr = new Franchise(fName);
			franchises.put(fName, fr);
			
			String frFile = franchise.split("\\|")[1];	// eg. burgerking.csv
			
			// now read the franchise file, parse restaurant data, and assign restaurant to nearby city
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(frFile));
				String line = reader.readLine();
				if (line != null) line = reader.readLine(); // skip first heading line
				while (line != null) {
					if (line.trim() != "") {
						// parse restaurant data
						double lon = Double.parseDouble(line.split(",")[0]);
						double lat = Double.parseDouble(line.split(",")[1]);
						Restaurant r = new Restaurant(fr, lat, lon);
						
						// find nearby city and add restaurant to the city. 
						// assume many:1 or many:0 mapping of restaurant:city
						S34Node city = findNearbyCity(r);
						if (city != null) city.addRestaurant(r);
					}
					
					line = reader.readLine();
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private S34Node findNearbyCity(Restaurant r) {
		// find the first city that's within the required degrees of lon/lat
		double lonPrecision = 0.5, latPrecision = 0.5;
		
		for (String cityName : cities.keySet()) {
			S34Node city = cities.get(cityName);
			double deltaLon = Math.abs(city.lon() - r.lon());
			double deltaLat = Math.abs(city.lat() - r.lat());
			if (deltaLon <= lonPrecision && deltaLat <= latPrecision) return city;
		}
		
		return null;
	}
	
	private void findIncomingEdges(Map<S34Node, HashSet<S34Node>> incomingEdges) {
		// first create map of cities to empty set
		for (S34Node city : cities.values()) {
			incomingEdges.put(city, new HashSet<S34Node>());
		}
		
		// read the connectedCities file line by line and add data to incomingEdges
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(connectedCitiesFile));
			String line = reader.readLine();
			while (line != null) {
				if (line.trim() != "") {
					// parse src and dst cities
					S34Node src = cities.get(line.split(",")[0].trim().toUpperCase());
					S34Node dst = cities.get(line.split(",")[1].trim().toUpperCase());
					
					// add src city to dst city's set of incoming edges
					incomingEdges.get(dst).add(src);
				}
				
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void parseCitiesData() {
		// read the citiesFile file line by line and add data to graph
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(citiesFile));
			String line = reader.readLine();
			if (line != null) line = reader.readLine(); // skip the first heading line
			while (line != null) {
				if (line.trim() != "") {
					// parse the cities data and store it in a node
					String[] vals = line.split(",");
					S34Node city = new S34Node(
							vals[3].toUpperCase(),
							Double.parseDouble(vals[4]),
							Double.parseDouble(vals[5])
					);
					
					// add the node to the graph
					cities.put(vals[3].toUpperCase(), city);
				}
				
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getMinCostRouteTable(String startCity, String endCity) {
		startCity = startCity.toUpperCase();
		endCity = endCity.toUpperCase();
		
		// the actual shortest path algorithm is in computeMinCostRoute(..)
		List<S34Edge> minCostRoute = computeMinCostRoute(cities.get(startCity), cities.get(endCity));
		
		// now convert the result into a string that represents a csv table
		String table = "CITY,MEAL CHOICE,COST OF MEAL\n";
		table += cities.get(startCity).name() + ",,\n"; // add src city. No meal in src city.
		for (int i = minCostRoute.size() - 1; i >= 0; i--) {
			// for each edge, get the edge's dst node's city, edge's meal's name, edge's meal's cost
			table += minCostRoute.get(i).dst().name() + ",";
			table += minCostRoute.get(i).meal().name() + ",";
			table += "$" + minCostRoute.get(i).meal().cost() + "\n";
		}
		return table;
	}
	private List<S34Edge> computeMinCostRoute(S34Node start, S34Node end) {
		List<S34Edge> minCostRoute = new ArrayList<S34Edge>();
		/*
		 * Algorithm - implements Dijkstra's shortest path algorithm:
		 * 
		 * 1. Store set of visited nodes. Store Map<node, int shortestDistance> that stores the 
		 * value of the shortest distance (=lowest cost path) from start node to each other node in the graph.
		 * Store a Map<node, node> that keeps track of the immediate parent for each node.
		 * Initialize values so that distance from src->src is 0, and every other distance is set to infinity.
		 * 
		 * 2. Starting from the src node: 
		 * 
		 * For each outgoing connected city k from the src node, compare k's shortestDistance value with current node's
		 * shortestDistance value + k's cost value. If the new distance value is less than the old one AND
		 * the edge's meal != previousMeal, set k's node's shortestDistance value to the new one, 
		 * and add current node as k's dst node's immediate parent.
		 * 
		 * Find the minimum cost edge whose dst node is not in the 
		 * set of visited nodes. In the above edge iteration loop, keep updating a
		 * minimumEdge variable accordingly.
		 * 
		 * Once the appropriate min edge is found, set the new "previous edge's meal" to the min edge's meal.
		 * Also add the edge's dst node to set of visited nodes. 
		 * Set current node to the new node.
		 * 
		 * If there is no next min edge, we are done. 
		 * 
		 */
		
		// variables
		Set<S34Node> visitedNodes = new HashSet<S34Node>();
		Map<S34Node, Double> minCostTable = new HashMap<S34Node, Double>();
		Map<S34Node, S34Edge> immediateParents = new HashMap<S34Node, S34Edge>();
		Meal previousMeal = null;
		
		// add all cities as keys to the minCostTable
		// set all immediateParents to null
		for (S34Node city : cities.values()) {
			minCostTable.put(city, Double.POSITIVE_INFINITY);
			immediateParents.put(city, null);
		}
		
		// set the cost to start city as 0
		minCostTable.put(start, Double.valueOf(0));
		
		S34Node currentNode = start;
		S34Edge minPathFromA;	
		
		do {
			minPathFromA = null;
			visitedNodes.add(currentNode);
			for (S34Node connectedCity : currentNode.outgoingConnectedNodes()) {
				// for each connected city from the current node, find the min path from current node to 
				// connected node. 
				S34Edge minEdge = currentNode.peekMinEdge(connectedCity);
				
				// if this minEdge's meal is the same as previous meal, get the next minEdge.
				if (minEdge.meal() == previousMeal) {
					S34Edge oldMinEdge = currentNode.removeMinEdge(connectedCity); // O(logn) to remove from minheap
					minEdge = currentNode.peekMinEdge(connectedCity); // O(1) to get min from minheap
					// add back the meal 
					currentNode.addEdge(oldMinEdge);
				}
				
				// calculate a new cost from start node to connectedCity node given the minEdge.
				// the cost should be the cost of the current node + the new edge cost
				double newCost = minCostTable.get(currentNode).doubleValue() + minEdge.cost();
				
				// if this new cost is less than the existing cost of the connectedCity, 
				// update the min cost for the connectedCity.
				if (newCost < minCostTable.get(connectedCity)) {
					minCostTable.put(connectedCity, newCost);
					immediateParents.put(connectedCity, minEdge);
				}
			}
			
			// find the minPathFromA 
			for (S34Node node : minCostTable.keySet()) {
				if (!visitedNodes.contains(node)) {
					if (minPathFromA == null) minPathFromA = immediateParents.get(node);
					else if (minCostTable.get(node).doubleValue() < minCostTable.get(minPathFromA.dst()).doubleValue()) {
						minPathFromA = immediateParents.get(node);
					}
				}
			}
			
			// if there's a minPathFromA found, then continue iterating through the graph.
			if (minPathFromA != null) {		
				previousMeal = minPathFromA.meal();
				immediateParents.put(minPathFromA.dst(), minPathFromA);
				currentNode = minPathFromA.dst();
			}
			// if there is no minPathFromA found, then we are done.
		} while (minPathFromA != null);
		
		/*
		 * Now we should have found the shortest path. 
		 * To get the path, start with the destination node, and append immediate parents to 
		 * a list until we reach the source node.
		 */
		currentNode = end;
		S34Edge parent;
		while ((parent = immediateParents.get(currentNode)) != null) {
			minCostRoute.add(parent);
			currentNode = parent.src();
		}
		
		return minCostRoute;
	}
}
