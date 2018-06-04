package implementation;

import java.io.IOException;

/**
 * This code was taken from the repository: https://github.com/ArthurSampaio/Graphs
 */
public class GraphAPIInterfaceImpl implements GraphAPIInterface{
	
	
	public Graph readGraph(String path) throws IOException {
		return GraphUtils.readGraph(path);
	}
	
	public Graph readWeightedGraph(String path) throws IOException {
		return GraphUtils.readWeightedGraph(path);
	}
	
	public int getVertexNumber(Graph graph) {
		return GraphUtils.getVertexNumber(graph);
	}
	

	public String graphRepresentation(Graph graph, String type) {
		return GraphUtils.graphRepresentation(graph, type);
	}
	

	public String BFS(Graph graph, int v) {
		return GraphUtils.BFS(graph, v);
	}

	
	public String DFS(Graph graph, int v) {
		return GraphUtils.DFS(graph, v);
	}
	

	public boolean connected(Graph graph) {
		return GraphUtils.connected(graph);
	}
	
	
	public String shortestPath(Graph graph, int v1, int v2) {
		return GraphUtils.shortestPath(graph, v1, v2);
	}
	
	
	public String mst(Graph graph) {
		return GraphUtils.mst(graph);
	}
}
