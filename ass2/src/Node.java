import java.util.HashMap;
import java.util.LinkedList;

public class Node {
	private LinkedList<Job> todo;
	private Node visited; 
	private Town currentLocation;
	// path cost and unload cost;
	private int cost;
	private int heuristic;
	/**
	 * @param todo
	 * @param currentLocation
	 */
	public Node(Town currentLocation, Node visited,int cost) {
		this.currentLocation = currentLocation;
		this.visited = visited;
		this.cost = cost;
		this.todo = new LinkedList<>();
	}
	/**
	 * @return the todo
	 */
	public LinkedList<Job> getTodo() {
		return todo;
	}
	/**
	 * @param todo the todo to set
	 */
	public void setTodo(LinkedList<Job> todo) {
		for(Job j : todo) {
			this.todo.add(j);
		}
	}
	/**
	 * @return the currentLocation
	 */
	public Town getCurrentLocation() {
		return currentLocation;
	}
	/**
	 * @return the visited
	 */
	public Node getVisited() {
		return visited;
	}
	/**
	 * @param visited the visited to set
	 */
	public void setVisited(Node visited) {
		this.visited = visited;
	}
	/**
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}
	/**
	 * @return the heuristic
	 */
	public int getHeuristic() {
		return heuristic;
	}
	/**
	 * @param heuristic the heuristic to set
	 */
	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}
	
}
	
