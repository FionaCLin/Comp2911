

import java.util.HashMap;
import java.util.LinkedList;

public class Journey {
	private LinkedList<Job> todo;
	private LinkedList<Job> done;
	private HashMap<RoutePath, Integer> routine; 
	private Town currentLocation;
	/**
	 * @param todo
	 * @param currentLocation
	 */
	public Journey(LinkedList<Job> todo, Town currentLocation) {
		this.todo = todo;
		this.currentLocation = currentLocation;
	}
	public Journey(){
		this.done = new LinkedList<>();
	}
	
}
