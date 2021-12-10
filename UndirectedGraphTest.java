package application;



import org.junit.After;
import java.io.FileNotFoundException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class UndirectedGraphTest {
	static UndirectedGraph graph;
	@BeforeAll
    public static void beforeClass() throws Exception{
		
    }
    

    @BeforeEach
    public void setUp() throws Exception {
    	graph = new UndirectedGraph();
    }


    @AfterEach
    public void tearDown() throws Exception {
    	graph=null;
    }
    
    //Add Tests below..
    
    @Test
    public void test001_simpleAddVertex() {
    	graph.addVertex("Cat");
    	
    	HashSet<String> set = (HashSet<String>) graph.getAllVertices();
    	    	
    	assert(set.contains("Cat"));
    	
    }
    
    
    @Test
    public void test002_addAndRemoveVertices() {
    	graph.addVertex("Chicken");
    	graph.addVertex("Dog");
    	
    	HashSet<String> set = (HashSet<String>) graph.getAllVertices();
    	
    	assert(set.contains("Chicken"));
    	assert(set.contains("Dog"));
    	
    	graph.removeVertex("Chicken");
    	
    	set = (HashSet<String>) graph.getAllVertices();
    	    	
    	assert(!set.contains("Chicken"));
    	
    }
    
    @Test
    public void test003_addEdge() {
    	graph.addVertex("Chicken");
    	graph.addVertex("Dog");
    	
    	graph.addEdge("Chicken", "Dog");
    	
    	List<String> l = graph.getAdjacentVerticesOf("Chicken");
    	assert(l.contains("Dog"));
    	l = graph.getAdjacentVerticesOf("Dog");
    	assert(l.contains("Chicken"));
    }
    
    @Test
    public void test004_addandRemoveEdge() {
    	graph.addVertex("Chicken");
    	graph.addVertex("Dog");
    	
    	graph.addEdge("Chicken", "Dog");
    	
    	List<String> l = graph.getAdjacentVerticesOf("Chicken");
    	List<String> m = graph.getAdjacentVerticesOf("Dog");
    	assert(l.contains("Dog"));
    	assert(m.contains("Chicken"));
    	
    	graph.removeEdge("Chicken", "Dog");
    	l = graph.getAdjacentVerticesOf("Chicken");
    	m = graph.getAdjacentVerticesOf("Dog");
    	assert(!l.contains("Dog")); 
    	assert(!m.contains("Chicken")); 	
    	
    }
    
    @Test
    public void test005_addManyEdges() {
    	graph.addVertex("A");
    	graph.addVertex("B");
    	graph.addVertex("C");
    	
    	graph.addEdge("A", "B");
    	graph.addEdge("A", "C");
    	graph.addEdge("B", "C");
    	
    	List<String> l = graph.getAdjacentVerticesOf("A");
    	assert(l.contains("B"));
    	assert(l.contains("C"));
    	
    	l = graph.getAdjacentVerticesOf("B");
    	assert(l.contains("C"));
    	assert(l.contains("A"));
    	
    	l = graph.getAdjacentVerticesOf("C");
    	assert(l.contains("A"));
    	assert(l.contains("B"));
    	
    	
    }
    
    @Test
    public void test006_addManyRemoveManyEdges() {
    	graph.addVertex("A");
    	graph.addVertex("B");
    	graph.addVertex("C");
    	
    	graph.addEdge("A", "B");
    	graph.addEdge("A", "C");
    	graph.addEdge("B", "C");
    	
    	graph.removeEdge("A", "B");
    	graph.removeEdge("A", "C");
    	graph.removeEdge("B", "C");
    	
    	List<String> l = graph.getAdjacentVerticesOf("A");
    	assert(!l.contains("B"));
    	assert(!l.contains("C"));
    	
    	l = graph.getAdjacentVerticesOf("B");
    	assert(!l.contains("C"));
    	assert(!l.contains("A"));
    	
    	l = graph.getAdjacentVerticesOf("C");
    	assert(!l.contains("A"));
    	assert(!l.contains("B"));
    	
    }
    
    @Test
    public void test007_addRemoveEdgesVertices() {
    	graph.addVertex("A");
    	graph.addVertex("B");
    	graph.addVertex("C");
    	
    	graph.addEdge("A", "B");
    	graph.addEdge("A", "C");
    	graph.addEdge("B", "C");
    	
    	graph.removeVertex("A");
    	
    	List<String> l = graph.getAdjacentVerticesOf("A");
    	assert(l == null);
 
    }
    
    @Test
    public void test008_checkNumVertices() {
    	graph.addVertex("A");
    	graph.addVertex("B");
    	graph.addVertex("C");
    	
    	assert(graph.order() == 3);
    	
    }
    
    @Test
    public void test009_checkNumVerticesAfterRemove() {
    	graph.addVertex("A");
    	graph.addVertex("B");
    	graph.addVertex("C");
    	
    	assert(graph.order() == 3);
    	
    	graph.removeVertex("B");
    	
    	assert(graph.order() == 2);
    }
    
    @Test
    public void test010_checkNumEdges() {
    	graph.addVertex("A");
    	graph.addVertex("B");
    	graph.addVertex("C");
    	
    	graph.addEdge("A", "B");
    	graph.addEdge("A", "C");
    	graph.addEdge("B", "C");
    	
    	assert(graph.size() == 3);
    	
    }
    
    @Test
    public void test011_checkNumEdgesAfterRemove() {
    	graph.addVertex("A");
    	graph.addVertex("B");
    	graph.addVertex("C");
    	
    	graph.addEdge("A", "B");
    	graph.addEdge("A", "C");
    	graph.addEdge("B", "C");

    	assert(graph.size() == 3);
    	
    	graph.removeEdge("A", "B");
    	graph.removeEdge("C", "A");
    	
    	assert(graph.size() == 1);
    	
    }
    		
    
}
