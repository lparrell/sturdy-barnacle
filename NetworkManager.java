package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class NetworkManager {
	private UndirectedGraph graph; // The underlying graph
	String focusUser; // The user who the network is focused on
	Random rnd = new Random();
	ArrayList<String> logList; //exports to a text log recording all changes to the graph

	// Set of people currently in a network with focusUser
	HashMap<String, Person> currentlyConnectedPeople;
	ArrayList<HashSet<Person>> currentlyConnectedFriends;
	// currently connected variables will contain a subset of the vertices in a
	// disconnected graph

	/**
	 * default no-argument constructor for NetworkManager
	 * 
	 * initializes an empty UndirectedGraph
	 */
	public NetworkManager() {
		graph = new UndirectedGraph();
		focusUser = "";
		currentlyConnectedPeople = new HashMap<String, Person>();
		currentlyConnectedFriends = new ArrayList<HashSet<Person>>();
		logList = new ArrayList<String>();
	}

	/**
	 * Reads input from a text file to create an UndirectedGraph representing a
	 * social network. Text commands can can: 1) call graph.addVertex(), ex: a user
	 * 2) call graph.addEdge(), ex: a user1 user2 3) call graph.removeVertex(), ex:
	 * r user 4) call graph.removeEdge(), ex: r user1 user2 5) call this.setFocus(),
	 * ex: s user
	 * 
	 * //TODO: discuss how to handle bad input as a group
	 * 
	 * @param filename - relative filepath to the .txt file the graph is to be
	 *                 constructed from
	 * @throws FileNotFoundException - if file path is incorrect
	 * @throws IOException           - IOException if the give file cannot be read
	 * @throws ImportFormatException - is formatted incorrectly
	 */
	public void importGraph(String filepath) throws FileNotFoundException, IOException, ImportFormatException {

		File file = new File(filepath);
		Scanner scanner = new Scanner(file);
		int count = 0;
		ArrayList<String[]> array = new ArrayList<String[]>();
		while (scanner.hasNextLine()) {
			count = count + 1;
			String data = scanner.nextLine();
			String input[] = data.split(" ");
			array.add(input);// Add the array to the arraylist so we can loop again after
			for (int i = 0; i < input.length; i++) {
				if (input[i].length() > 32) {
					System.out.println("count:" + count);
					scanner.close();
					throw new ImportFormatException();// TODO: add in exception args?
				}
			}
			if (input.length == 1 || input.length > 3) {
				if (!input[0].contentEquals("")) {
					System.out.println("count:" + count + " input line length");
					scanner.close();
					throw new ImportFormatException();// TODO: add in exception args?
				}
			}
		}
		for (int j = 0; j < array.size(); j++) {
			if (array.get(j)[0].equals("a")) {
				if (array.get(j).length == 2) {// if add vertex
					graph.addVertex((array.get(j)[1]));
					logList.add("a " + (array.get(j)[1]));
				}
				if (array.get(j).length == 3) {// if add edge
					graph.addEdge((array.get(j)[1]), (array.get(j)[2]));
					logList.add("a " + (array.get(j)[1]) + " " + (array.get(j)[2]));
				}
			} else if (array.get(j)[0].equals("r")) {
				if (array.get(j).length == 3) {// if remove edge
					graph.removeEdge((array.get(j)[1]), (array.get(j)[2]));
					logList.add("r " + (array.get(j)[1] + " " + (array.get(j)[2])));
				}
				if (array.get(j).length == 2) {// if remove vertex
					graph.removeVertex((array.get(j)[1]));
					logList.add("r " + (array.get(j)[1]));
				}

			} else if (array.get(j)[0].equals("s")) {// if change focusUser
				setFocus(array.get(j)[1]);
				//logList adds focus user changes inside of setFocus()
			}
		}
		if (focusUser.isBlank()) {
			Set<String> users = graph.getAllVertices();
			Iterator<String> listIterator = users.iterator();
			focusUser = listIterator.next();
		}
		scanner.close();
		clearNetwork();
		createNewPeople();
		updateFriendships();
	}// importGraph

	/**
	 * Creates a file and writes the current state of the graph to this file. The
	 * current focusUser is added to the last line of the file ex: s focusUser
	 * 
	 * @param filename
	 */
	public void exportGraph(String filename) throws FileNotFoundException {
		if (!filename.contains(".txt")) {
			filename = filename + ".txt";
		}
		Set<String> list = graph.getAllVertices();
		Iterator<String> listIterator = list.iterator();
		Set<String> singleOutput;
		Set<Set<String>> output = new LinkedHashSet<Set<String>>();

		if (list != null) {
			while (listIterator.hasNext()) {
				String user = listIterator.next();
				List<String> adjacent = graph.getAdjacentVerticesOf(user);
				if (adjacent.isEmpty()) {
					singleOutput = new LinkedHashSet<String>();
					singleOutput.add(user);
					output.add(singleOutput);
				} else {
					for (int i = 0; i < adjacent.size(); i++) {
						singleOutput = new LinkedHashSet<String>();
						singleOutput.add(user);
						singleOutput.add(adjacent.get(i));
						if (!output.contains(singleOutput)) {
							output.add(singleOutput);
						}
					}//for
				}//else
			}
			// We should now have a set without duplicates of all of the users and their
			// relationships
		}
		PrintWriter out = new PrintWriter(filename);

		Iterator<Set<String>> setIterator = output.iterator();
		while (setIterator.hasNext()) {
			Set<String> line = new LinkedHashSet<String>();
			line = setIterator.next();
			Iterator<String> lineIterator = line.iterator();
			String outputString = "a";
			while (lineIterator.hasNext()) {
				String outputPiece = lineIterator.next();
				outputString = outputString + " " + outputPiece;
			}
			out.println(outputString);
		}
		if (!focusUser.equals("")) {
			out.println("s " + focusUser);
		}
		out.close();
	}

	/**
	 * Sets graph equal to a new empty graph
	 */
	public void clearGraph() {
		this.clearNetwork();
		this.setFocus("");
		graph = new UndirectedGraph();
	}

	/**
	 * Changes the focus member of the graph. Resets the network graph.
	 * 
	 * The user must be in the network to become the new focus user.
	 * 
	 * @return - true if user is in network, false if not
	 */
	public boolean changeFocus(String name) {
		if (graph.getAllVertices().contains(name) == false) {
			return false;
		}
		this.setFocus(name); //logList modified in setFocus
		this.clearNetwork();
		this.createNewPeople();
		this.updateFriendships();
		return true;
	}

	/**
	 * Sets new start or 'root' member for the graph.  Intended for internal
	 * modification of focusUser.
	 * 
	 * @param name
	 */
	private void setFocus(String name) {
		logList.add("s "+name);
		focusUser = name;
	}

	/**
	 * gets the focusUser
	 */
	public String getFocus() {
		return focusUser;
	}

	////////////////////////////////////////////////////////
	// Wrapper methods for interacting with UndirectedGraph//
	////////////////////////////////////////////////////////

	/**
	 * Attempts to add a name to the underlying graph. If the name parameter is not
	 * a valid string
	 * 
	 * 
	 */
	public boolean addUser(String name) {
		int initialUsers = graph.order();
		graph.addVertex(name);
		System.out.println("Adding: " + name);
		int currentUsers = graph.order();

		// if this is the first user successfully added to an empty graph, make it the
		// focus
		if (initialUsers == 0 && currentUsers == 1) {
			this.setFocus(name);
		}
		// update the Network graph
		createNewPeople();
		updateFriendships();

		if (currentUsers > initialUsers) {
			logList.add("a "+name);
			return true;
		} else {
			return false;
		}
	}

	public boolean addFriendship(String name1, String name2) {
		int initialUsers = graph.order();
		int initialEdges = graph.size();
		graph.addEdge(name1, name2);
		int currentUsers = graph.order();
		int currentEdges = graph.size();

		if (initialUsers == 0 && currentUsers > 0) {
			this.setFocus(name1);
		}
		System.out.println("Adding: " + name1 + " and " + name2);
		// update the Network graph
		createNewPeople();
		updateFriendships();

		if (currentEdges > initialEdges) {
			logList.add("a "+name1+" "+name2);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Removes the user from the underlying graph and modifies the network graph.
	 * 
	 * If the last user is removed, sets focusUser to ""
	 * 
	 * If the focus user is removed
	 * 
	 * @param name
	 * @return
	 */
	public boolean removeUser(String name) {
		int initialUsers = graph.order();
		ArrayList<String> connected = BFTraversal(this.getFocus());
		graph.removeVertex(name); // attempt to remove the node
		int currentUsers = graph.order();
		if (currentUsers < initialUsers) {// if removal is successful
			if (name.equals(this.focusUser)) {
				if (currentUsers == 0) {// if this is the last user in the true graph
					this.setFocus("");
					clearNetwork();
				} else {// change focus to another user
					if (currentlyConnectedPeople.size() > 1) {// if there are other users in the network graph
						connected.remove(focusUser);
						currentlyConnectedPeople.remove(focusUser);

						setFocus(connected.get(0));
						clearNetwork();
						createNewPeople();
						updateFriendships();
					} else {// if focusUser is last in the sub-graph
						clearNetwork();

						Set<String> otherUsers = graph.getAllVertices();
						String[] otherUsersArray = otherUsers.toArray(new String[graph.order()]);
						int next = rnd.nextInt(otherUsersArray.length);
						setFocus(otherUsersArray[next]);
						createNewPeople();
						updateFriendships();
					}
				}
			} else {// if removal is not the focus user...
				System.out.println("DEBUG: removing a non-focus user");
				clearNetwork();
				createNewPeople();
				updateFriendships();
			}
			logList.add("r "+name);
			return true;
		} // if graph.removeVertex fails
		return false;
	}

	public boolean removeFriendship(String name1, String name2) {
		int initialEdges = graph.size();
		graph.removeEdge(name1, name2);
		ArrayList<String> connected = BFTraversal(this.getFocus());
		int currentEdges = graph.size();
		if (currentEdges < initialEdges) {
			if (!connected.contains(name1)) {
				currentlyConnectedPeople.remove(name1);
			}
			if (!connected.contains(name2)) {
				currentlyConnectedPeople.remove(name2);
			}
			currentlyConnectedFriends.clear();
			updateFriendships();
			logList.add("r "+name1+" "+name2);
			return true;
		}
		return false;
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
	//////////////// End of Wrapper methods//////////////////
	////////////////////////////////////////////////////////

	/**
	 * Confirms whether the string passed into add/remove methods is <= 32
	 * characters and has only 1 word
	 */
	public boolean validString(String input) {
		if (input.length() > 32) {
			return false;
		}
		String sentence = input;
		String[] words = sentence.split(" ");
		if (words.length > 1) {
			return false;
		}
		return true;
	}

	/**
	 * Performs a breadth-first traversal of the graph from focusUser
	 * 
	 * @param focusUser - precondition: focusUser is in the graph
	 * @return - returns a list of all nodes that can be reached from focusUser
	 */
	private ArrayList<String> BFTraversal(String focusUser) {
		LinkedList<String> queue = new LinkedList<String>(); // LinkedList implements deque
		ArrayList<String> visited = new ArrayList<String>();
		if (focusUser.isEmpty()) {
			return visited;
		}
		visited.add(focusUser);
		queue.addFirst(focusUser); // enqueue
		while (!queue.isEmpty()) {
			String current = queue.removeLast();
			System.out.println("BFTraversal current: " + current);
			for (String v : graph.getAdjacentVerticesOf(current)) {
				System.out.println("BFTraversal v: " + v);
				if (!visited.contains(v)) {
					visited.add(v);
					queue.addFirst(v);
				} // end if v not visited
			} // end for every successor of v
		} // end while queue not empty
		return visited;
	}// BFTraversal

	/**
	 * Checks if there are any names in the graph connected to focusUser that have
	 * not been added to currentlyConnectedPeople.
	 * 
	 * Creates a new Person and generates (X,Y) coords before adding to HashMap
	 * 
	 * @return
	 */
	private void createNewPeople() {
		ArrayList<String> traversalResults = BFTraversal(focusUser);
		for (String name : traversalResults) {
			if (!currentlyConnectedPeople.keySet().contains(name)) {
				if (name.equals(focusUser)) {
					currentlyConnectedPeople.put(name, new Person(name, 0, 0));
				} else {
					double[] coords = getNextCoords();
					double nextX = coords[0];
					double nextY = coords[1];
					currentlyConnectedPeople.put(name, new Person(name, nextX, nextY));
				}
			}
		}
	}// createNewPeople

	/**
	 * Generates X and Y coordinates
	 * 
	 * Currently just randomly generates X-Y coords within the canvas
	 * 
	 * @return
	 */
	private double[] getNextCoords() {
		double[] coords = new double[2];
		coords[0] = (double) rnd.nextInt(720) - 360; // x
		coords[1] = (double) rnd.nextInt(360) - 180; // y
		return coords;
	}

	/**
	 * Checks all users in currentlyConnectedPeople and creates a list of their
	 * relationship sets by referencing the underlying graph.
	 */
	private void updateFriendships() {
		for (String p : currentlyConnectedPeople.keySet()) {
			List<String> friends = graph.getAdjacentVerticesOf(p);
			for (String f : friends) {
				HashSet<Person> friendship = new HashSet<Person>();
				friendship.add(currentlyConnectedPeople.get(f));
				friendship.add(currentlyConnectedPeople.get(p));
				if (!currentlyConnectedFriends.contains(friendship)) {
					currentlyConnectedFriends.add(friendship);
				}
			}
		}
	}

	public ArrayList<HashSet<Person>> getFriendships() {
		return currentlyConnectedFriends;
	}

	public HashMap<String, Person> getConnectedUsers() {
		return currentlyConnectedPeople;
	}

	/**
	 * Resets the graph at the network level while leaving the underlying
	 * UndirectedGraph unaltered.
	 */
	public void clearNetwork() {
		currentlyConnectedPeople.clear();
		currentlyConnectedFriends.clear();
	}

	/**
	 * Writes the current contents of logList to a file
	 * named log.txt.  
	 */
	public void writeToLog() {
		PrintWriter out = null;
		try {
			out = new PrintWriter("log.txt");
		}catch(FileNotFoundException e) {
			System.out.println("ERROR: log.txt failed to write!");
		}
		//Print each line of logList to the file.
		for(String line : logList) {
			out.println(line);
		}
		out.close();
	}
	
}
