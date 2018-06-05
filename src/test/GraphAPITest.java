package test;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import implementation.Graph;
import implementation.GraphAPIInterface;
import implementation.GraphAPIInterfaceImpl;
import implementation.GraphUtils;

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
	public void getVertexNumberWithBigGraph() {
		int numberVertex = 1000;
		Graph graphOne = mockGraphDisconnected(numberVertex);
		int expectedNumberVertex;
		
		expectedNumberVertex = this.graphManipulator.getVertexNumber(graphOne);
				
		Assert.assertEquals(expectedNumberVertex, numberVertex);
	}
	
	@Test
	public void testGetEdgeNumber() {
		Graph graphOne = mockGraph();
		int expectedNumberEdges;
		int numberAddedEdges;
		
		expectedNumberEdges = GraphUtils.getEdgeNumber(graphOne);
		numberAddedEdges = 4;
		
		Assert.assertEquals(expectedNumberEdges, numberAddedEdges);
	}
	
	@Test
	public void testGetEdgeNumberTwo() {
		Graph graphOne = mockGraphDisconnected();
		int expectedNumberEdges;
		int numberAddedEdges;
		
		expectedNumberEdges = GraphUtils.getEdgeNumber(graphOne);
		numberAddedEdges = 2;
		
		Assert.assertEquals(expectedNumberEdges, numberAddedEdges);
	}
	
	@Test
	public void testGetEdgeNumberWithBigGraph() {
		int numberVertex = 1000;
		Graph graphOne = mockGraphDisconnected(numberVertex);
		int expectedNumberEdges;
		int numberAddedEdges;
		
		expectedNumberEdges = GraphUtils.getEdgeNumber(graphOne);
		numberAddedEdges = 0;
		
		Assert.assertEquals(expectedNumberEdges, numberAddedEdges);
	}
	
	@Test
	public void testGetMeanEdge() {
		Graph graphOne = mockGraph();
		float expectedMeanEdge;
		float numberMeanEdge;
		float delta = 0;
		
		/* An integer division is being executed inside the method, since there
		 * is no conversion of one of the division values ​​to float or double,
		 * so the mean degree will always be an integer value.
		 */
		expectedMeanEdge = GraphUtils.getMeanEdge(graphOne);
		numberMeanEdge =  1.6f;
		
		Assert.assertEquals(expectedMeanEdge, numberMeanEdge, delta);
	}
	
	@Test
	public void testGetMeanEdgeTwo() {
		Graph graphOne = mockGraphFigureOne();
		float expectedMeanEdge;
		float numberMeanEdge;
		float delta = 0;
		
		expectedMeanEdge = GraphUtils.getMeanEdge(graphOne);
		numberMeanEdge =  2.0f;
		
		Assert.assertEquals(expectedMeanEdge, numberMeanEdge, delta);
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
		 * 
		 * The initial vertex index is not being accessed correctly because it is not
		 * considering that the initial vertex value is equal to the index + 1, so
		 * the parent is not being correctly stored for this reason.
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
	public void testShortestPathWithWeight() {
		Graph graphOne = mockGraphWithWeightTwo();
		int firstVertex = 1;
		int thirdVertex = 5;

		String result = this.graphManipulator.shortestPath(graphOne, firstVertex, thirdVertex);
		String expectedResult = "[1, 2, 5]";

		Assert.assertEquals(result, expectedResult);
	}
	
	@Test
	public void testShortestPathWithNegativeWeight() {
		Graph graphOne = mockGraphWithWeight();
		int firstVertex = 1;
		int thirdVertex = 5;

		/*
		 * The shortestPath method does not work with negative weights
		 */
		String result = this.graphManipulator.shortestPath(graphOne, firstVertex, thirdVertex);
		String expectedResult = "[1, 2, 5]";

		Assert.assertEquals(result, expectedResult);
	}
	
	@Test
	public void testShortestPathNonexistent() {
		Graph graphOne = mockGraphDisconnected();
		int firstVertex = 1;
		int thirdVertex = 3;
		
		try {
			this.graphManipulator.shortestPath(graphOne, firstVertex, thirdVertex);
			
			fail("Should throw an exception");
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), "There is no path between v1 and v2");
		}
	}
		
	@Test
	public void testDisconnectedGraph() {
		Graph graph = mockGraphDisconnected();
		boolean expected = false;
		boolean real = this.graphManipulator.connected(graph);
		
		Assert.assertEquals(expected, real);
	}
	
	@Test
	public void testDisconnectedBigGraph() {
		int numberVertex = 1000;
		Graph graph = mockGraphDisconnected(numberVertex);
		boolean expected = false;
		boolean real = this.graphManipulator.connected(graph);
		
		Assert.assertEquals(expected, real);
	}
	
	@Test
	public void testConnectedGraph() {
		Graph graph = mockGraphFigureOne();
		boolean expected = true;
		boolean real = this.graphManipulator.connected(graph);
		
		Assert.assertEquals(expected, real);
	}
	
	@Test
	public void testConnectedBigGraph() {
		int numberVertex = 1000;
		Graph graph = mockGraphConnected(numberVertex);
		boolean expected = true;
		boolean real = this.graphManipulator.connected(graph);
		
		Assert.assertEquals(expected, real);
	}
	
	@Test
	public void testGraphRepresentationAL() {
		try {
			Graph graph = graphManipulator.readGraph(GRAPH_SAMPLE_PATH);
			
			String answer = this.graphManipulator.graphRepresentation(graph, "AL");
			
			String expectedBFS =  "1-2 \n"  +
								  "2-1 3 \n" +
								  "3-2 4 \n" +
								  "4-3 5 \n" +
								  "5-4 \n";
			
			Assert.assertEquals(expectedBFS, answer);			
		} catch (IOException e) {
			fail("Error in reading file");
		}
	}
	
	@Test
	public void testGraphRepresentationWeightAM() {
		try {
			Graph graph = graphManipulator.readWeightedGraph(GRAPH_SAMPLE_WITH_WEIGHT_PATH);
			
			String answer = this.graphManipulator.graphRepresentation(graph, "AM");
			
			String expectedBFS =  "  1 2 3 4 5\n" + 
					  "1 0 0.1 0 0 1 \n" +
					  "2 0.1 0 0 0 0.2 \n" +
					  "3 0 0 0 -9.5 5 \n" +
					  "4 0 0 -9.5 0 2.3 \n" +
					  "5 1 0.2 5 2.3 0 \n";
			
			Assert.assertEquals(expectedBFS, answer);			
		} catch (IOException e) {
			fail("Error in reading file");
		}
	}
	
	@Test
	public void testGraphRepresentationWeightAL() {
		try {
			Graph graph = graphManipulator.readWeightedGraph(GRAPH_SAMPLE_WITH_WEIGHT_PATH);
			
			String answer = this.graphManipulator.graphRepresentation(graph, "AL");
			
			String expectedBFS =  "1-2(0.1) 5(1) \n" +
								  "2-1(0.1) 5(0.2) \n" +
								  "3-4(-9.5) 5(5) \n" +
								  "4-3(-9.5) 5(2.3) \n" +
								  "5-1(1) 2(0.2) 3(5) 4(2.3) \n";
			
			Assert.assertEquals(expectedBFS, answer);
		} catch (IOException e) {
			fail("Error in reading file");
		}
	}
	
	@Test
	public void testGraphRepresentationAM() {
		try {
			Graph graph = graphManipulator.readGraph(GRAPH_SAMPLE_PATH);
			
			String answer = this.graphManipulator.graphRepresentation(graph, "AM");
			
			String expectedBFS =  "  1 2 3 4 5\n" +
								  "1 0 1 0 0 0 \n" +
								  "2 1 0 1 0 0 \n" +
								  "3 0 1 0 1 0 \n" +
								  "4 0 0 1 0 1 \n" +
								  "5 0 0 0 1 0 \n";
	
			Assert.assertEquals(expectedBFS, answer);
		} catch (IOException e) {
			fail("Error in reading file");
		}
	}
	
	@Test
	public void mst() {
			Graph graph =  mockGraphWithWeightTwo();
			
			String answer = this.graphManipulator.mst(graph);
			
			String expected = "1 - - 0\n" + 
					"2 - 1 1\n" + 
					"3 - 4 4\n" + 
					"4 - 5 3\n" + 
					"5 - 2 2";
			Assert.assertEquals(expected, answer);
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
	
	private Graph mockGraphWithWeightTwo() {
		Graph graph = new Graph(5);
		
		graph.addEdge(1, 2, 0.1);
		graph.addEdge(2, 1, 0.1);
		graph.addEdge(2, 5, 0.2);
		graph.addEdge(5, 2, 0.2);
		graph.addEdge(5, 3, 5.0);
		graph.addEdge(3, 5, 5.0);
		graph.addEdge(3, 4, 1.0);
		graph.addEdge(4, 3, 1.0);
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
	
	private Graph mockGraphConnected(int numVertex) {
		Graph graph = new Graph(numVertex);
		
		for (int i = 1; i <= numVertex; i++) {
			for (int j = 1; j <= numVertex; j++) {
				graph.addEdge(i, j);
			}
		}
		
		return graph;
	}
	
	private Graph mockGraphDisconnected(int numVertex) {
		Graph graph = new Graph(numVertex);
	
		return graph;
	}
}