package implementation;

import java.util.LinkedList;

/**
 * This code was taken from the repository: https://github.com/ArthurSampaio/Graphs
 * 
 * This graph's representation take into account the follows rules:
 * 1- A[i][j] = 1, means that has an edge between i and j,
 * 2- A[i][i] = 1, means that has a loop in vertice i
 * 3 - To representate the weight of edges => A[i][j] = weight
 */
public class Graph {
	
	private double[][] adjacencyMatrix;
	private int numVertices;
	private boolean weighted;
	private static final double DEFAULT_WEIGHT = 1.0;
	private static final double NO_EDGE = 0.0;
	
	public Graph(int v) {
		this.weighted = false;
		this.numVertices = v;
		this.adjacencyMatrix = new double[numVertices][numVertices];
	}

	public boolean isWeighted() {
		return weighted;
	}

	public void setWeighted(boolean weighted) {
		this.weighted = weighted;
	}

	public double[][] getAdjacencyMatrix() {
		return this.adjacencyMatrix;
	}

	public void setAdjacencyMatrix(double[][] adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
	}

	public int getNumVertices() {
		return numVertices;
	}

	public void setNumVertices(int numVertices) {
		this.numVertices = numVertices;
	}

	public void addEdge(int i, int j){
	    this.adjacencyMatrix[i-1][j-1] = DEFAULT_WEIGHT;
    }
	
	public void addEdge(int i, int j, Double weight) {
		this.adjacencyMatrix[i-1][j-1] = weight;
	}

    public void removeEdge(int i, int j) {
        this.adjacencyMatrix[i-1][j-1] = 0.0;
    }

    public boolean hasEdge(int i, int j) {
	    return this.adjacencyMatrix[i][j] != NO_EDGE;
    }

    public String toString(){
		String out = "";

		for(int i = 0; i < adjacencyMatrix.length; i++){
			out += lineToString(adjacencyMatrix[i]);
			out += "\n";
		}
		return out;
	}

	private String lineToString(double[] line) {

		String out = "";
		for(int i = 0; i < line.length; i++){
			out += line[i];
			out += "\t";
		}
		return out;

	}

	public double getEdge(int i, int j){
		return this.adjacencyMatrix[i][j];
	}
	
}
