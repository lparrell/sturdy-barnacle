package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

public class NetworkManager {
	private UndirectedGraph graph;
	String focusUser;
	
	/**
	 * default no-argument constructor for NetworkManager
	 * 
	 * initializes an empty UndirectedGraph
	 */
	public NetworkManager() {
        graph = new UndirectedGraph();
    }
	
	/**
	 *  Reads input from a text file to create an UndirectedGraph representing a social network.
	 *  Text commands can can:
	 *  1) call graph.addVertex(), ex: a user
	 *  2) call graph.addEdge(), ex: a user1 user2
	 *  3) call graph.removeVertex(), ex: r user
	 *  4) call graph.removeEdge(), ex: r user1 user2
	 *  5) call this.setFocus(), ex: s user
	 * 
	 *	//TODO: discuss how to handle bad input as a group
	 * 
	 * @param filename - relative filepath to the .txt file the graph is to be constructed from
	 * @throws FileNotFoundException - if file path is incorrect
	 * @throws IOException - IOException if the give file cannot be read
	 * @throws ImportFormatException - is formatted incorrectly
	 */
	public void importGraph(String filepath)throws FileNotFoundException, IOException, ImportFormatException {
	
	}
	
	/**
	 * Creates a file and writes the current state of the graph to this file.  
	 * The current focusUser is added to the last line of the file ex: s focusUser
	 * 
	 * @param filename
	 */
	public void exportGraph(String filename) {
		
	}
	
	/**
	 * Sets graph equal to a new empty graph
	 */
	public void clearGraph() {
		graph = new UndirectedGraph();
	}
	
	/**
	 * Sets new start or 'root' member for the graph.
	 */
	public void setFocus(String name) {
		focusUser = name;
	}
	
	/**
	 * gets the focusUser
	 */
	public String getFocus() {
		return focusUser;
	}
	
    
	////////////////////////////////////////////////////////
	//Wrapper methods for interacting with UndirectedGraph//
	////////////////////////////////////////////////////////
	public void addUser(String name) {
		graph.addVertex(name);
	}

	public void addFriendship(String name1, String name2) {
		graph.addEdge(name1, name2);
	}

	public void removeUser(String name) {
		graph.removeVertex(name);
	}

	public void removeFriendship(String name1, String name2) {
		graph.removeEdge(name1, name2);
	}

	public int getNumUsers() {
		return graph.order();
	}

	public int getNumFriendships() {
		return graph.size();
	}
	
	public Set<String> getAllUsers() {
        return graph.getAllVertices();
    }
	////////////////////////////////////////////////////////
	////////////////End of Wrapper methods//////////////////
	////////////////////////////////////////////////////////

}
