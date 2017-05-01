import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Node {
	private State state;
	private Node visited;
	private boolean isJob;
	// this path cost and unload cost;
	// g(n)
	private int cost;
	// h(n)
	private int heuristic;

	public Node(State state, Node visited, boolean isJob) {
		this.state = state;
		this.visited = visited;
		this.isJob = isJob;
		this.setCost();
	}

	/**
	 * @param cost
	 *            the cost to set
	 */

	private void setCost() {
		if (this.visited != null) {
			this.cost = this.visited.getCurrentLocation().getCostToAdjacentTown(this.state.getTown().getName());
			this.cost += this.visited.getCost();
			if (isJob) {
				this.cost += this.state.getTown().getUnloadCost();
			}
		} else {
			this.cost = 0;
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
	public HashSet<Job> getTodo() {
		return this.state.getJobs();
	}


	/**
	 * @return the currentLocation
	 */
	public Town getCurrentLocation() {
		return this.state.getTown();
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
	public Node makeMove(Town neightbour){
		
		// is that a job
		Job job = null;
		Iterator<Job> itr = this.state.getJobs().iterator();
		while(itr.hasNext()){
			Job temp = itr.next();
			if (temp.getOrigin().equals(this.state.getTown()) && temp.getDestination().equals(neightbour)) {
				job = temp;
				break;
			}
		}
		HashSet<Job> nextJobs = this.state.getJobs();
		if(job != null) {
			nextJobs = new HashSet<Job>(nextJobs);
			nextJobs.remove(job);
		}
		
		return new Node(new State(neightbour, nextJobs),this, job != null);
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}
	
}
