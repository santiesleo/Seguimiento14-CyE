package model;
import static org.junit.jupiter.api.Assertions.*;

import model.Vertex;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Arrays;


class GraphTest {

    private Graph<Integer> graph;

    @BeforeEach
    void setUp() {
        graph = new Graph<>();
    }

    @Test
    void testGetIndexExistingVertex() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        int expectedIndex = 1;
        int actualIndex = graph.getIndex(2);
        assertEquals(expectedIndex, actualIndex);
    }

    @Test
    void testGetIndexNonExistingVertex() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        int expectedIndex = -1;
        int actualIndex = graph.getIndex(4);
        assertEquals(expectedIndex, actualIndex);
    }

    @Test
    void testGetIndexEmptyGraph() {
        int expectedIndex = -1;
        int actualIndex = graph.getIndex(1);
        assertEquals(expectedIndex, actualIndex);
    }

    @Test
    public void testAddVertexEmpty() {
        Graph<Integer> graph = new Graph<>();
        graph.addVertex(1);
        assertEquals(1, graph.vertexs.size());
        assertEquals(1, graph.vertexs.get(0).getValue());
    }

    @Test
    void testAddVertex() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        assertEquals(2, graph.getIndex(3));
    }

    @Test
    public void testAddVertexDuplicate() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(1);
        assertEquals(4, graph.vertexs.size());
        assertEquals(1, graph.vertexs.get(0).getValue());
        assertEquals(2, graph.vertexs.get(1).getValue());
        assertEquals(3, graph.vertexs.get(2).getValue());
    }
    @Test
    void testEmptyVertex() {
        ArrayList<Vertex<Integer>> v = graph.getVertexs();
        assertEquals(0, v.size());
    }

    @Test
    void testAddNotDirectedEdge() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addNotDirectedEdge(1, 2);
        graph.addNotDirectedEdge(1, 3);
        assertEquals(2, graph.getAdjList().get(0).size());
        assertEquals(1, graph.getAdjList().get(1).size());
    }

    @Test
    void testAddDirectedEdge() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addDirectedEdge(1, 2);
        graph.addDirectedEdge(2, 3);
        assertEquals(1, graph.getAdjList().get(0).size());
        assertEquals(1, graph.getAdjList().get(1).size());
        assertEquals(0, graph.getAdjList().get(2).size());
    }
    @Test
    void testAddDirectedEdgeWithValidVertices() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addDirectedEdge(1, 2);
        assertEquals(1, graph.vertexs.get(0).getValue());
    }

    @Test
    void testAddDirectedEdgeWithOneInvalidVertex() {
        graph.addVertex(1);
        graph.addDirectedEdge(1, 2);
        boolean test = true;
        try {
            graph.vertexs.get(1).getValue();
        }catch (Exception ex){
            ex.printStackTrace();
            assertFalse(false);
        }
    }
    @Test
    void testBFS() throws GraphException, QueueException {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addNotDirectedEdge(1, 2);
        graph.addNotDirectedEdge(2, 3);
        graph.addNotDirectedEdge(3, 4);
        graph.bfs(1);
        assertEquals(0, graph.getVertexs().get(0).getDistance());
        assertEquals(1, graph.getVertexs().get(1).getDistance());
        assertEquals(2, graph.getVertexs().get(2).getDistance());
        assertEquals(3, graph.getVertexs().get(3).getDistance());
    }
    @Test
    void testBFS2() throws GraphException, QueueException {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addDirectedEdge(1, 2);
        graph.addDirectedEdge(3, 2);
        graph.bfs(1);
        assertEquals(1, graph.getAdjList().get(0).size());
        assertEquals(0, graph.getAdjList().get(1).size());
        assertEquals(1, graph.getAdjList().get(2).size());
    }

    @Test
    void testDFS() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addNotDirectedEdge(1, 2);
        graph.addNotDirectedEdge(2, 3);
        graph.addNotDirectedEdge(3, 4);
        graph.dfs();
        assertEquals(8, graph.getVertexs().get(0).getFinalTime());
        assertEquals(7, graph.getVertexs().get(1).getFinalTime());
        assertEquals(6, graph.getVertexs().get(2).getFinalTime());
        assertEquals(5, graph.getVertexs().get(3).getFinalTime());
    }
    @Test
    void testDFS2() throws GraphException, QueueException {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(7);
        graph.addDirectedEdge(1, 2);
        graph.addDirectedEdge(3, 7);
        graph.addDirectedEdge(2,3);
        graph.bfs(2);
        assertEquals(1, graph.getAdjList().get(0).size());
        assertEquals(1, graph.getAdjList().get(1).size());
        assertEquals(1, graph.getAdjList().get(2).size());
        assertEquals(0, graph.getAdjList().get(3).size());
        assertEquals(0, graph.getAdjList().get(4).size());
    }
}
