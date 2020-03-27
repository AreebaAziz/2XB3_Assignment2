/**
 * @author Areeba Aziz
 */
package cas2xb3_A2_aziz_aa;

import java.io.FileWriter;

public class Main {
	
	private static String startCity = "Boston";
	private static String endCity = "Minneapolis";
	private static String connectedCities = "data/connectedCities.txt";
	private static String outputFile = "a2_out.txt";
	private static String menuFile = "data/menu.csv";
	private static String citiesFile = "data/USCities.csv";
	private static String restaurantsFiles = 
			"McDonald's|data/mcdonalds.csv,"
			+ "Burger King|data/burgerking.csv,"
			+ "Wendy's|data/wendys.csv";

	public static void main(String[] args) {
		// possible args: startCity endCity
		if (args.length == 1) startCity = args[0];
		if (args.length == 2) {
			startCity = args[0];
			endCity = args[1];
		}
		
		// Section 3.2 - find path from city A to B
		S32Graph s32Graph = new S32Graph(connectedCities);
		s32Graph.build();
		String bfsPath = s32Graph.findPathBFS(startCity, endCity);
		String dfsPath = s32Graph.findPathDFS(startCity, endCity);
		writeToOutput("BFS: " + bfsPath, false);
		writeToOutput("DFS: " + dfsPath, true);
		
		// Section 3.4 - find shortest, cost-efficient path from city A to B
		S34Graph s34Graph = new S34Graph(connectedCities, citiesFile, menuFile, restaurantsFiles);
		s34Graph.build();
		String costEfficientRouteTable = s34Graph.getMinCostRouteTable(startCity, endCity);
		writeToOutput(costEfficientRouteTable, true);
	}
	
	private static void writeToOutput(String str, boolean append) {
		try {
			FileWriter f = new FileWriter(outputFile, append);
			f.write(str + "\n");
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
