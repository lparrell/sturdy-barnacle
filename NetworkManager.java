package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
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
        focusUser = "";
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
	public void importGraph(String filepath) throws FileNotFoundException, IOException, ImportFormatException {
		try {
			File file = new File(filepath);
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String data = scanner.nextLine();
				String input[] = data.split(" ");
				if (input[0].equals("a")) {
					for (int i = 1; i < input.length; i++) {
						addUser(input[i]);
					}
					if (input.length > 2) {
						addFriendship(input[1], input[2]);
					}
				}
				if (input[0].equals("r")) {
					if (input.length == 3) {
						removeFriendship(input[1], input[2]);
					} else {
						for (int i = 1; i < input.length; i++) {
							removeUser(input[i]);
						}
					}
				}
				if (input[0].equals("s")) {
					setFocus(input[1]);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// What do we want to do here? is it easy to trigger a popup from here to say
			// "invalid file, please try again?"
		}
	}
	
	/**
	 * Creates a file and writes the current state of the graph to this file. The
	 * current focusUser is added to the last line of the file ex: s focusUser
	 * 
	 * @param filename
	 */
	public void exportGraph(String filename) {
		for (int i = 0; i < graph.size(); i++) {

		}
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
	public boolean addUser(String name) {
		if(!validString(name)) {
			return false;
		}
		graph.addVertex(name);
		if(focusUser.equals("")) {//if initial user entered, set focus
			setFocus(name);
		}
		return true;
	}

	public boolean addFriendship(String name1, String name2) {
		if(!validString(name1) || !validString(name2)) {
			return false;
		}
		graph.addEdge(name1, name2);
		if(focusUser.equals("")) {//if initial users entered, set focus
			setFocus(name1);
		}
		return true;
	}

	public boolean removeUser(String name) {
		if(!validString(name)) {
			return false;
		}
		graph.removeVertex(name);
		if(name.equals(focusUser)) {
			//TODO: figure out how to handle this
		}
		if(this.getNumUsers() == 0) {
			setFocus("");
		}
		return true;
	}

	public boolean removeFriendship(String name1, String name2) {
		if(!validString(name1) || !validString(name2)) {
			return false;
		}
		graph.removeEdge(name1, name2);
		if(name1.equals(focusUser)) {
			//TODO: figure out how to handle this
		}
		if(name2.equals(focusUser)) {
			//TODO: figure out how to handle this
		}
		if(this.getNumUsers() == 0) {
			setFocus("");
		}
		return true;
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

	/**
	 * Confirms whether the string passed into add/remove methods is
	 * <= 30 characters and has only 1 word
	 */
	private boolean validString(String input) {
		if(input.length() > 30) {
			return false;
		}
		String sentence = input;
		String[] words = sentence.split(" ");
		if(words.length > 1) {
			return false;
		}
		return true;
	}
	
}
