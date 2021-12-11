package application;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class UndirectedGraph implements UndirectedGraphADT {
	
	Set<Vertex> vertices;
	Set<String> verticesList;
	int numEdges;
	
	private class Vertex {
		String vertex;
		Set<Vertex> linkedVertices;
		
		public Vertex(String vertex) {
			this.vertex = vertex;
			//List of successors:
			this.linkedVertices = new HashSet<Vertex>();
			vertices.add(this);
			verticesList.add(vertex);
		}

		public void remove() {

			for (Vertex v : vertices) {
				v.linkedVertices.remove(this);
			}
			
			this.linkedVertices = null; //remove successor pointers
			
			vertices.remove(this);
			verticesList.remove(this.vertex);
		}
		
		public void addEdge(Vertex v) {
			//add an edge to this vertex with the specified one
			
			if (this.linkedVertices.contains(v)) {
				return;
			}
			else {
				this.linkedVertices.add(v);
				v.linkedVertices.add(this);
				numEdges++;
			}
			
		}

		
		public void removeEdge(Vertex v) {
			
			if (this.linkedVertices.contains(v)) {
				this.linkedVertices.remove(v);
				v.linkedVertices.remove(this);
				numEdges--;
			}

		}
		
		public List<String> getLinkedVertices() {
			List<String> l = new LinkedList<String>();
			
			Iterator<Vertex> i = this.linkedVertices.iterator();
			
			while (i.hasNext()) {
				Vertex s = i.next();
				l.add(s.vertex);
			}
			
			
			return l;
		}
		
	}
	
	

	/*
	 * Default no-argument constructor
	 */ 
	public UndirectedGraph() {
		this.vertices = new HashSet<Vertex>();
		this.verticesList = new HashSet<String>();
		this.numEdges = 0;
	}

	
	@Override
	public void addVertex(String vertex) {
		
		if (vertex == null) return;
		
		if (this.containVertex(vertex)==true) return;
		
		//Add a new vertex:
		
		this.vertices.add(new Vertex(vertex));
		
	}
	
	public boolean containVertex(String vertex) {
		Set<String> set = this.getAllVertices();
		if (set == null) {
			return false;
		}
		
		if (vertex == null) {
			return false;
		}
		
		if (set.contains(vertex)) {
			return true;
		}
		
		return false;
	}
	
	private Vertex getVertex(String vertex) {
		Set<Vertex> set = this.getAllVerticeObjects();
		Iterator<Vertex> i = this.getAllVerticeObjects().iterator();
		
		if (set == null) {
			return null;
		}
		
		if (vertex == null) {
			return null;
		}
		
		while (i.hasNext()) {
			Vertex v = i.next();
			if (v.vertex.equals(vertex)) {
				return v;
			}
		}
		
		return null;
	}

	@Override
	public void removeVertex(String vertex) {
		
		if (vertex == null) return;
		
		if (this.containVertex(vertex)==false) return;
		
		//Remove the vertex:
		
		Vertex v = this.getVertex(vertex);
		
		v.remove();
		
		
	}

	@Override
	public void addEdge(String vertex1, String vertex2) {
		if (vertex1 == null) return;
		if (vertex2 == null) return;
		
		Set<String> set = this.getAllVertices();
		if ((set == null)||
				((set !=null)&&(set.contains(vertex1) == false))) {
				this.addVertex(vertex1);
			}
					
		
		if ((set == null)||
				((set !=null)&&(set.contains(vertex2) == false))) {
				this.addVertex(vertex2);
			}
		
		Vertex v1 = this.getVertex(vertex1);
		Vertex v2 = this.getVertex(vertex2);
				
		v1.addEdge(v2);
		
	}

	@Override
	public void removeEdge(String vertex1, String vertex2) {
		if (vertex1 == null) return;
		if (vertex2 == null) return;
		
		Set<String> set = this.getAllVertices();
		if ((set == null)||
				((set !=null)&&((set.contains(vertex1) == false)||
						(set.contains(vertex2) == false)))) 
					return;
		
		Vertex v1 = this.getVertex(vertex1);
		Vertex v2 = this.getVertex(vertex2);
		
		v1.removeEdge(v2);
		
		
	}

	@Override
	public Set<String> getAllVertices() {
		return this.verticesList;
	}
	
	/**
     * Returns a Set that contains all the Vertices
     * 
	 */
	private Set<Vertex> getAllVerticeObjects() {
		return this.vertices;
	}

	@Override
	public List<String> getAdjacentVerticesOf(String vertex) {
		
		if (vertex == null) return null;
		if (this.containVertex(vertex)==false) return null;
		
		Vertex v = this.getVertex(vertex);
		
		return v.getLinkedVertices();
	}

	@Override
	public int size() {
		return this.numEdges;
	}

	@Override
	public int order() {
		  return this.getAllVerticeObjects().size();
	}
	

}
