package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class NetworkManager {
	private UndirectedGraph graph; //The underlying graph
	String focusUser; //The user who the network is focused on
	Random rnd = new Random();
	
	//Set of people currently in a network with focusUser
	HashMap<String, Person> currentlyConnectedPeople;
	ArrayList<HashSet<Person>> currentlyConnectedFriends;
	//currently connected variables will contain a subset of the vertices in a disconnected graph
	
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
			int count=0;
			ArrayList<String[]> array = new ArrayList<String[]>();
			
			while (scanner.hasNextLine()) {
				count = count + 1;
				String data = scanner.nextLine();
				String input[] = data.split(" ");
				
				array.add(input);//Add the array to the arraylist so we can loop again after
				
				if (input.length <2 || input.length >3) {
					//String output = "File is invalid at line "+count;
					throw new ImportFormatException("File is invalid at line "+count);
				}
			}
			
			
			for(int j=0; j<array.size(); j++) {
				if (array.get(j)[0].equals("a")) {
					for (int i = 1; i < array.get(j).length; i++) {
						addUser(array.get(j)[i]);
					}
					if (array.get(j).length > 2) {
						addFriendship(array.get(j)[1], array.get(j)[2]);
					}
				}
				if (array.get(j)[0].equals("r")) {
					if (array.get(j).length == 3) {
						removeFriendship(array.get(j)[1], array.get(j)[2]);
					} else {
						for (int i = 1; i < array.get(j).length; i++) {
							removeUser(array.get(j)[i]);
						}
					}
				}
				if (array.get(j)[0].equals("s")) {
					setFocus(array.get(j)[1]);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// What do we want to do here? is it easy to trigger a popup from here to say
			// "invalid file, please try again?"
			System.out.println("File not found error");
			//TODO: throw a popup saying the file doesn't exist
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
	 * Changes the focus member of the graph.  Resets the network graph.
	 * 
	 * The user must be in the network to become the new focus user.
	 * 
	 * @return - true if user is in network, false if not
	 */
	public boolean changeFocus(String name) {
		if(!validString(name)) {
			return false;
		}
		boolean inGraph = false; 
		for(String p : graph.getAllVertices()) {
			if(p.equals(name)) {
				inGraph = true;
				focusUser = name;
			}
		}
		if(inGraph == false) {
			return false;
		}
		this.clearNetwork();
		this.createNewPeople();
		this.updateFriendships();
		return inGraph;
	}
	
	/**
	 * Sets new start or 'root' member for the graph.
	 * @param name
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
			//TODO: figure out how to handle this, prompt the user to enter a new focus user?
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
	 * <= 32 characters and has only 1 word
	 */
	private boolean validString(String input) {
		if(input.length() > 32) {
			return false;
		}
		String sentence = input;
		String[] words = sentence.split(" ");
		if(words.length > 1) {
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
	private ArrayList<String> BFTraversal(String focusUser){ 
		LinkedList<String> queue = new LinkedList<String>(); //LinkedList implements deque
		ArrayList<String> visited = new ArrayList<String>();
		
		visited.add(focusUser);
		queue.addFirst(focusUser); //enqueue
		while(!queue.isEmpty()) {
			String current = queue.removeLast();
			for(String v : graph.getAdjacentVerticesOf(current)) {
				if(!visited.contains(v)) {
					visited.add(v);
					queue.addFirst(v);
				}//end if v not visited
			}// end for every successor of v
		}//end while queue not empty
		return visited;	
	}//BFTraversal
	
	/**
	 * Checks if there are any names in the graph connected to focusUser
	 * that have not been added to currentlyConnectedPeople.  
	 * 
	 * Creates a new Person and generates (X,Y) coords before adding to HashMap
	 * @return
	 */
	private void createNewPeople(){
		ArrayList<String> traversalResults = BFTraversal(focusUser);
		for(String name : traversalResults) {
			if(!currentlyConnectedPeople.keySet().contains(name)) {
				if(name.equals(focusUser)){
					currentlyConnectedPeople.put(name, new Person(name, 0, 0));
				}else {
				double [] coords = getNextCoords();
				double nextX = coords[0];
				double nextY = coords[1];
				currentlyConnectedPeople.put(name, new Person(name, nextX, nextY));
				}
			}
		}
	}//createNewPeople
	
	/**
	 * Generates X and Y coordinates
	 * 
	 * Currently just randomly generates X-Y coords within the canvas
	 * @return
	 */
	private double[] getNextCoords() {
		double[] coords = new double[2]; 
		coords[0] = (double) rnd.nextInt(720) - 360; //x
		coords[1] = (double) rnd.nextInt(360) - 180; //y
		return coords;
	}
	
	/**
	 * Checks all users in currentlyConnectedPeople and creates a list of their
	 * relationship sets by referencing the underlying graph.
	 */
	private void updateFriendships() {
		for(String p : currentlyConnectedPeople.keySet()) {
			List<String> friends = graph.getAdjacentVerticesOf(p);
			for(String f : friends) {
				HashSet<Person> friendship = new HashSet<Person>();
				friendship.add(currentlyConnectedPeople.get(f));
				friendship.add(currentlyConnectedPeople.get(p));
				if(!currentlyConnectedFriends.contains(friendship)) {
					currentlyConnectedFriends.add(friendship);
				}
			}
		}
	}
	
	public ArrayList<HashSet<Person>> getFriendships(){
		return currentlyConnectedFriends;
	}
	
	public HashMap<String, Person> getConnectedUsers(){
		return currentlyConnectedPeople;
	}
	
	/**
	 * Resets the graph at the network level while leaving the underlying UndirectedGraph
	 * unaltered.  
	 */
	public void clearNetwork() {
		currentlyConnectedPeople.clear();
		currentlyConnectedFriends.clear();
	}
	
	//Debug method, remove later
	public void testStaticGraph() {
		this.setFocus("Ross");
		try {
			importGraph("C:\\Users\\19204\\Documents\\Java\\Hello_JavaFX17\\application\\testFile.txt");
		} catch (IOException | ImportFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//graph.printGraph();
		this.createNewPeople();
		this.updateFriendships();
		for(String P : currentlyConnectedPeople.keySet()) {
			System.out.println(currentlyConnectedPeople.get(P).getName()+ " X: " +currentlyConnectedPeople.get(P).getPosX()+
					" Y: " +currentlyConnectedPeople.get(P).getPosY());
		}
		System.out.println("Now printing friendships.");
		for(HashSet<Person> friendship: currentlyConnectedFriends) {
			for(Person person : friendship) {
				System.out.print(person.getName()+ " ");
			}
			System.out.println();
		}
	}
	
	
	
}
