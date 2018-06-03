package test;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import implementation.Graph;
import implementation.GraphAPIInterface;
import implementation.GraphAPIInterfaceImpl;

public class GraphAPITest {
	
	private final String GRAPH_SAMPLE_PATH = "src/graph/samples/graph-sample.txt";
	private final String GRAPH_SAMPLE_WITH_WEIGHT_PATH = "src/graph/samples/graph-sample-with-weight.txt";

	private GraphAPIInterface graphManipulator;
	
	@Before
	public void setUp() {
		this.graphManipulator = new GraphAPIInterfaceImpl();		
	}
	
	@Test
	public void testReadGraph() {
		try {
			Graph graph = graphManipulator.readGraph(GRAPH_SAMPLE_PATH);
								
			double[][] realAdjacencyMatrix = graph.getAdjacencyMatrix();
			double[][] mockAdjacencyMatrix = mockGraph().getAdjacencyMatrix();
			float delta = 0;

			for (int i = 0; i < mockAdjacencyMatrix.length; i++) {
				for (int j = 0; j < mockAdjacencyMatrix[i].length; j++) {
					Assert.assertEquals(mockAdjacencyMatrix[i][j], realAdjacencyMatrix[i][j], delta);
				}
			}			
		} catch (IOException e) {
			fail("Error in reading file");
		}
	}
	
	@Test
	public void testReadWeightedGraph() {
		try {
			Graph graph = graphManipulator.readWeightedGraph(GRAPH_SAMPLE_WITH_WEIGHT_PATH);
			
			double[][] realAdjacencyMatrix = graph.getAdjacencyMatrix();
			double[][] mockAdjacencyMatrix = mockGraphWithWeight().getAdjacencyMatrix();
			float delta = 0;
			
			for (int i = 0; i < mockAdjacencyMatrix.length; i++) {
				for (int j = 0; j < mockAdjacencyMatrix[i].length; j++) {
					Assert.assertEquals(mockAdjacencyMatrix[i][j], realAdjacencyMatrix[i][j], delta);
				}
			}			
		} catch (IOException e) {
			fail("Error in reading file");
		}		
	}
	
	@Test
	public void getVertexNumber() {
		Graph graphOne = mockGraph();
		int expectedNumberVertex;
		int numberAddedVertex;
		
		expectedNumberVertex = this.graphManipulator.getVertexNumber(graphOne);
		numberAddedVertex = 5;
		
		Assert.assertEquals(expectedNumberVertex, numberAddedVertex);
	}
	
	@Test
	public void testBFS() {
		Graph graph = mockGraphFigureOne();
		int firstVertex = 1;
		
		String realBFS = this.graphManipulator.BFS(graph, firstVertex);
		String expectedBFS =  "1-0 -\n" + 
							  "2-1 1\n" + 
							  "3-2 5\n" + 
							  "4-2 5\n" + 
							  "5-1 1\n";
		
		Assert.assertEquals(expectedBFS, realBFS);
	}
	
	@Test
	public void testDFS() {
		Graph graphOne = mockGraphFigureOne();
		int firstVertex = 1;
		
		/*
		 * There is a possible bug in method DFS, the initial vertex (passed as parameter)
		 * is not parent of any other vertex.
		 */
		String realDFS = this.graphManipulator.DFS(graphOne, firstVertex);
		String expectedDFS =  "1-0 -\n" +
				"2-2 5\n" +
				"3-2 5\n" +
				"4-2 5\n" +
				"5-1 1\n";

		Assert.assertEquals(expectedDFS, realDFS);
	}
	
	@Test
	public void testShortestPath() {
		Graph graphOne = mockGraphFigureOne();
		int firstVertex = 1;
		int thirdVertex = 3;

		String result = this.graphManipulator.shortestPath(graphOne, firstVertex, thirdVertex);
		String expectedResult = "[1, 5, 3]";

		Assert.assertEquals(result, expectedResult);
	}
	
	@Test
	public void testConnected() {
		Graph graph = mockGraphDisconnected();
		boolean expected = false;
		boolean real = this.graphManipulator.connected(graph);
		
		Assert.assertEquals(expected, real);
	}
		
	private Graph mockGraph() {
		Graph graph = new Graph(5);
		
		graph.addEdge(1, 2);
		graph.addEdge(2, 1);
		graph.addEdge(2, 3);
		graph.addEdge(3, 2);
		graph.addEdge(3, 4);
		graph.addEdge(4, 3);
		graph.addEdge(4, 5);
		graph.addEdge(5, 4);
				
		return graph;
	}
	
	private Graph mockGraphWithWeight() {
		Graph graph = new Graph(5);
		
		graph.addEdge(1, 2, 0.1);
		graph.addEdge(2, 1, 0.1);
		graph.addEdge(2, 5, 0.2);
		graph.addEdge(5, 2, 0.2);
		graph.addEdge(5, 3, 5.0);
		graph.addEdge(3, 5, 5.0);
		graph.addEdge(3, 4, -9.5);
		graph.addEdge(4, 3, -9.5);
		graph.addEdge(4, 5, 2.3);
		graph.addEdge(5, 4, 2.3);
		graph.addEdge(1, 5, 1.0);
		graph.addEdge(5, 1, 1.0);
		
		return graph;
	}
	
	
	private Graph mockGraphFigureOne() {
		Graph graph = new Graph(5);

		graph.addEdge(1, 2);
		graph.addEdge(2, 1);
		graph.addEdge(1, 5);
		graph.addEdge(5, 1);
		graph.addEdge(2, 5);
		graph.addEdge(5, 2);
		graph.addEdge(5, 3);
		graph.addEdge(3, 5);
		graph.addEdge(4, 5);
		graph.addEdge(5, 4);
		
		return graph;
	}
	
	private Graph mockGraphDisconnected() {
		Graph graph = new Graph(5);
		
		graph.addEdge(1, 2);
		graph.addEdge(2, 1);
		graph.addEdge(3, 4);
		graph.addEdge(4, 3);
		
		return graph;
	}
}
