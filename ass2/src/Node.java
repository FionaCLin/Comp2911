import java.util.HashMap;
import java.util.LinkedList;

public class Node {
	private LinkedList<Job> todo;

	private Node visited;
	private Town currentLocation;
	private boolean isJob;
	// this path cost and unload cost;
	// g(n)
	private int cost;
	// h(n)
	private int heuristic;

	public Node(Town currentLocation, Node visited, boolean isJob) {
		this.currentLocation = currentLocation;
		this.visited = visited;
		this.isJob = isJob;
		this.setCost();
		if (visited != null) {
			this.setTodo(visited.getTodo());
		}
	}

	/**
	 * @param cost
	 *            the cost to set
	 */

	public void setCost() {
		if (this.visited != null) {
			this.cost = this.visited.getCurrentLocation().getCostToAdjacentTown(currentLocation.getName());
			this.cost += this.visited.getCost();
			if (isJob) {
				this.cost += this.currentLocation.getUnloadCost();
			}
		} else {
			this.cost = 0;
			this.isJob = false;
		}
	}

	/**
	 * @return the totalCost
	 */
	public int getTotalCost() {
		return this.cost + this.heuristic;
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
		this.todo = new LinkedList<>();
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
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}

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

	public boolean isInfinitLoop(Town nextTown) {
		if (this.visited == null || this.visited.visited == null)
			return false;
		Node lastTown = this.visited;
		Node last2rdTown = lastTown.visited;
		if (lastTown.getCurrentLocation().equals(nextTown)
				&& this.currentLocation.equals(last2rdTown.currentLocation)) {
			if (!lastTown.isJob && !last2rdTown.isJob) {
				return true;
			}
		}
		return false;

	}
}
