package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class UndirectedGraph implements UndirectedGraphADT{

	private int size;
	private int order;
	private HashMap<String,ArrayList<String>> vertices;
	
	public UndirectedGraph() {
		vertices = new HashMap<String,ArrayList<String>>();
		vertices.putIfAbsent("Ross", new ArrayList<String>());
		vertices.putIfAbsent("McKenna", new ArrayList<String>());
		vertices.putIfAbsent("Laura", new ArrayList<String>());
		vertices.putIfAbsent("Luke", new ArrayList<String>());
		vertices.putIfAbsent("Megan", new ArrayList<String>());
		vertices.putIfAbsent("Nick", new ArrayList<String>());
		vertices.putIfAbsent("John", new ArrayList<String>());
		vertices.putIfAbsent("Bob", new ArrayList<String>());
		vertices.putIfAbsent("Mitch", new ArrayList<String>());
		
		vertices.get("Nick").add("Luke");
		
		vertices.get("Luke").add("Nick");
		vertices.get("Luke").add("Megan");
		vertices.get("Luke").add("Ross");
		
		vertices.get("Ross").add("Laura");
		vertices.get("Ross").add("Luke");
		vertices.get("Ross").add("Megan");
		vertices.get("Ross").add("McKenna");
		
		vertices.get("Megan").add("Luke");
		vertices.get("Megan").add("Ross");
		
		vertices.get("Laura").add("Ross");
		vertices.get("Laura").add("McKenna");
		vertices.get("Laura").add("John");
		
		vertices.get("McKenna").add("Ross");
		vertices.get("McKenna").add("Laura");
		vertices.get("McKenna").add("Bob");
		
		vertices.get("John").add("Laura");
		vertices.get("John").add("Mitch");
		
		vertices.get("Bob").add("McKenna");
		vertices.get("Bob").add("Mitch");
		
		vertices.get("Mitch").add("Bob");
		vertices.get("Mitch").add("John");
		
	}
	
	public void printGraph() {
		for(String key : vertices.keySet()) {
			System.out.print(key+ ": ");
			for(String p : vertices.get(key)) {
				System.out.print(p+ " ");
			}
			System.out.println();
		}
	}
	
	
	@Override
	public void addVertex(String vertex) {
		size = 0;
		order = 0;
		vertices = new HashMap<String,ArrayList<String>>();
	}

	@Override
	public void removeVertex(String vertex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEdge(String vertex1, String vertex2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeEdge(String vertex1, String vertex2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<String> getAllVertices() {
		// TODO Auto-generated method stub
		return vertices.keySet();
	}

	@Override
	public List<String> getAdjacentVerticesOf(String vertex) {
		return vertices.get(vertex);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public int order() {
		// TODO Auto-generated method stub
		return order;
	}

}
