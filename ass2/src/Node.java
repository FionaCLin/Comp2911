import java.util.HashMap;
import java.util.LinkedList;

public class Node implements Comparable<Node> {
	private LinkedList<Job> todo;
	private Node visited;
	private Town currentLocation;
	// path cost and unload cost;
	private int cost;
	private int heuristic;
	private boolean isJob;

	public Node(Town currentLocation, Node visited, boolean isJob) {
		this.currentLocation = currentLocation;
		this.visited = visited;
		this.isJob = isJob;
		this.setCost();
		this.todo = new LinkedList<>();
	}

	public void setCost() {
		if (this.visited != null) {
			this.cost = this.visited.getCurrentLocation().getCostToAdjacentTown(currentLocation.getName());
			if (isJob) {
				this.cost += this.currentLocation.getUnloadCost();
			}
		} else {
			this.cost = 0;
			this.isJob = false;
		}
	}

	/**
	 * @return the isJob
	 */
	public boolean isJob() {
		return isJob;
	}

	/**
	 * @return the todo
	 */
	public LinkedList<Job> getTodo() {
		return todo;
	}

	/**
	 * @param todo
	 *            the todo to set
	 */
	public void setTodo(LinkedList<Job> todo) {
		for (Job j : todo) {
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
	 * @param visited
	 *            the visited to set
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
	 * @param cost
	 *            the cost to set
	 */
	/**
	 * @return the heuristic
	 */
	public int getHeuristic() {
		return heuristic;
	}

	/**
	 * @param heuristic
	 *            the heuristic to set
	 */
	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}

	@Override
	public int compareTo(Node o) {
		return this.todo.size() - o.todo.size();
	}

}
