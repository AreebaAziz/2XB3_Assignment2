package cas2xb3_A2_aziz_aa;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestSearch {

	private static S32Graph graph;
	private static String connectedCitiesFile;
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		connectedCitiesFile = "data/connectedCities.txt";
		// build a S32Graph that we can use for our tests
		graph = new S32Graph(connectedCitiesFile);
		graph.build();
	}
	
	@Test
	public void testIsConnectedCity() {
		// test the S34Graph.isConnected function on all the connected cities
		// based on the connectedCities.txt file.
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(connectedCitiesFile));
			String line = reader.readLine();
			while (line != null) {
				if (line.trim() != "") {
					String fromCity = line.split(",")[0].trim();
					String toCity = line.split(",")[1].trim();
					
					// now assert that these cities are connected in the graph
					assertTrue(graph.isConnected(fromCity, toCity));
				}
				
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testIsNotConnectedCity() {
		// test that cities that are not connected return false as expected
		assertFalse(graph.isConnected("Atlanta", "New York City"));
		assertFalse(graph.isConnected("Oklahoma City", "Phoenix"));
		assertFalse(graph.isConnected("Salt Lake City", "New York City"));
		assertFalse(graph.isConnected("Washington", "Seattle"));
		assertFalse(graph.isConnected("Columbus", "Las Vegas"));
	}
	
	@Test
	public void testS32NodeEquality() {
		// tests the equality method of S32Node to make sure two nodes
		// are considered equal iff they have the same city String.
		S32Node c1 = new S32Node("Toronto");
		S32Node c2 = new S32Node("Hamilton");
		
		assertNotEquals(c1, c2);	// Toronto is not equal to Hamilton
		assertEquals(c2, c2); 	// a node should be equal to itself
		assertNotEquals(c1, null); 	// node should not be equal to null
	}

	@Test
	public void testAllPossibilitiesOfRoutes() {
		Set<S32Node> nodes = graph.nodes();
		for (S32Node fromCity : nodes) {
			for (S32Node toCity : nodes) {
				// this for loop generates all possible pairs of nodes, where
				// the src node is fromCity and dst node is toCity.
				
				// get the BFS route for this pair
				String bfsRoute = graph.findPathBFS(fromCity.city, toCity.city);
				
				// get the DFS route for this pair
				String dfsRoute = graph.findPathDFS(fromCity.city, toCity.city);
				
				// now analyze the results to make sure 
				// the resulting BFS and DFS routes are valid. 
				// A route is valid if all consecutive cities are connected.
				assertTrue(isValidRoute(bfsRoute));
				assertTrue(isValidRoute(dfsRoute));
			}
		}	
	}
	
	private boolean isValidRoute(String route) {
		// check if a given route, with cities separated by commas, is valid.
		String[] cities = route.split(",|, | ,");
		for (int i = 0; i < cities.length - 1; i++) {
			boolean isConnected = graph.isConnected(cities[i].trim(), cities[i+1].trim());
			if (!isConnected) return false;
		}
		
		return true;
	}

}
