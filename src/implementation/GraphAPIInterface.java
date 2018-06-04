package implementation;

import java.io.IOException;

/**
 * This code was taken from the repository: https://github.com/ArthurSampaio/Graphs
 */
public interface GraphAPIInterface {
	
	/**
	* Builds a graph from a given .txt file.
	* @param path to the .txt file
	* @return A Graph representation from given file.
	*/
	public Graph readGraph(String path) throws IOException;
	
	/**
	* Builds a weighted graph from a given .txt file.
	* @param path to the .txt file
	* @return A Weighted Graph representation from given file.
	*/
	public Graph readWeightedGraph(String path) throws IOException;
	
	/**
	* Calculates the number of vertices in a given graph.
	* @param Graph object
	* @return An integer representing the number of vertices.
	*/
	public int getVertexNumber(Graph graph);
	
	/**
	* Represents a given graph in two different ways.
	* @param Graph object
	* @param type of representation "AL" or "AM"
	* @return A string following the given representation.
	*/
	public String graphRepresentation(Graph graph, String type);

	/**
	* Breadth-first search (BFS) is an algorithm for traversing or searching 
	* tree or graph data structures.
	* @param Graph object
	* @param vertice to start the algorithm
	* @return A String that shows "vertex -level father" of all vertices from the given graph.
	*/
	public String BFS(Graph graph, int v);
	
	/**
	* Deep-first search (DFS) is an algorithm for traversing or searching
	* tree or graph data structures.
	* @param Graph object
	* @param vertice to start the algorithm
	* @return A String that shows "vertex -level father" of all vertices from the given graph.
	*/
	public String DFS(Graph graph, int v);
	
	/**
	* Algorithm to verify if a given graph is connected using BFS.
	* @param Graph object
	* @return A boolean value which answer if the graph is connected or not .
	*/
	public boolean connected(Graph graph);
	
	/**
	* Find the shortest path between two vertexes.
	* @param v1 source vertex
	* @param v2 target vertex
	* @return String containing the shortest path from v1 to v2.
	*/
	public String shortestPath(Graph graph, int v1, int v2);
	
	/**
	* Find the Minimum Spaning Tree in a given graph.
	* @param graph
	* @return String representing the MST.
	*/
	public String mst(Graph graph) ;
}
