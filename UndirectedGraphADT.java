import java.util.List;
import java.util.Set;

public interface UndirectedGraphADT {
	/**
     * Add new vertex to the graph.
     *
     * If vertex is null or already exists,
     * method ends without adding a vertex or 
     * throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     * 
     * @param vertex the vertex to be added
     * 
     *	
     */
    public void addVertex(String vertex);
    //unchanged from GraphADT
    
    /**
     * Remove a vertex and all associated 
     * edges from the graph.
     * 
     * If vertex is null or does not exist,
     * method ends without removing a vertex, edges, 
     * or throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     *  
     * @param vertex the vertex to be removed
     * 
     */
    public void removeVertex(String vertex);
    //Method must remove vertex from each node vertex has a relationship with
    
    /**
     * Add edge between vertex1 and vertex2
     * to this graph.  (edge is undirected and shared)
     * 
     * If either vertex does not exist,
     * VERTEX IS ADDED and then edge is created.
     * No exception is thrown.
     *
     * If the edge exists in the graph,
     * no edge is added and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge is not in the graph
     *  
     * @param vertex1 the first vertex (src)
     * @param vertex2 the second vertex (dst)
     */
    public void addEdge(String vertex1, String vertex2);
    //edges must be undirected -> each vertex must "know" it's in the relationship
    
    /**
     * Remove the edge between vertex1 and vertex2
     * from this graph.  (edges are undirected)
     * If either vertex does not exist,
     * or if an edge between vertex1 to vertex2 does not exist,
     * no edge is removed and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex argument is null
     * 2. both vertices are in the graph 
     * 3. the edge between vertex1 and vertex2 is in the graph
     *  
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    public void removeEdge(String vertex1, String vertex2);
    //edges are undirected -> edge needs to be removed from both vertices
    
    /**
     * Returns a Set that contains all the vertices
     * 
     * @return a Set<String> which contains all the vertices in the graph
     */
    public Set<String> getAllVertices();
    //unchanged from GraphADT
    
    /**
     * Get all the neighbor (adjacent-dependencies) of a vertex
     * 
     * For the example graph, A->[B, C], D->[A, B] 
     *     getAdjacentVerticesOf(A) should return [B, C]. 
     * 
     * In terms of packages, this list contains the immediate 
     * dependencies of A and depending on your graph structure, 
     * this could be either the predecessors or successors of A.
     * 
     * @param vertex the specified vertex
     * @return an List<String> of all the adjacent vertices for specified vertex
     */
    public List<String> getAdjacentVerticesOf(String vertex);
    //unchanged from GraphADT
    
    /**
     * Returns the number of edges in this graph.
     * @return number of edges in the graph.
     */
    public int size();
    //unchanged from GraphADT
    
    /**
     * Returns the number of vertices in this graph.
     * @return number of vertices in graph.
     */
    public int order();
    //unchanged from GraphADT
}
