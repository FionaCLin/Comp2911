import java.util.HashMap;
import java.util.HashSet;

// TODO: Auto-generated Javadoc
/**
 * The Class Node.
 */
public class Move implements Comparable<Move>{

	/** The state. */
	private State state;

	/** The visited. */
	private Move visited;

	/** The is job. */
	private boolean isJob;

	/** The cost. */
	private int cost;

	/** The heuristic. */
	private int heuristic;

	/**
	 * Instantiates a new node.
	 *
	 * @param state
	 *            the state
	 * @param visited
	 *            the visited
	 * @param isJob
	 *            the is job
	 */
	public Move(State state, Move visited, boolean isJob) {
		this.state = state;
		this.visited = visited;
		this.isJob = isJob;
		this.setCost();
	}

	/**
	 * Sets the cost.
	 */
	private void setCost() {
		if (this.visited != null) {
			this.cost = this.visited.getLocation().getAdjacentTowns().get(this.state.getTown());
			this.cost += this.visited.getCost();
			if (isJob) {
				this.cost += this.state.getTown().getUnloadCost();
			}
		} else {
			this.cost = 0;
		}
	}

	/**
	 * Gets the total cost.
	 *
	 * @return the total cost
	 */
	public int getTotalCost() {
		return this.cost + this.heuristic;
	}

	/**
	 * Checks if is job.
	 *
	 * @return true, if is job
	 */
	public boolean isJob() {
		return isJob;
	}

	/**
	 * Gets the todo.
	 *
	 * @return the todo
	 */
	public HashSet<Job> getTodo() {
		return this.state.getJobs();
	}

	/**
	 * Gets the current location.
	 *
	 * @return the current location
	 */
	public Town getLocation() {
		return this.state.getTown();
	}

	/**
	 * Gets the visited.
	 *
	 * @return the visited
	 */
	public Move getVisited() {
		return visited;
	}

	/**
	 * Gets the cost.
	 *
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Gets the heuristic.
	 *
	 * @return the heuristic
	 */
	public int getHeuristic() {
		return heuristic;
	}

	/**
	 * Sets the heuristic.
	 *
	 * @param heuristic
	 *            the new heuristic
	 */
	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}

	/**
	 * Make move.
	 *
	 * @param neightbour
	 *            the neightbour
	 * @return the node
	 */
	public Move makeMove(Town neightbour) {

		// is that a job
		Job job = null;

		for (Job temp : this.state.getJobs()) {
			if (temp.getStart().equals(this.state.getTown()) && temp.getDestination().equals(neightbour)) {
				job = temp;
				break;
			}
		}
		HashSet<Job> nextJobs = this.state.getJobs();
		if (job != null) {
			nextJobs = new HashSet<Job>(nextJobs);
			nextJobs.remove(job);
		}
		return new Move(new State(neightbour, nextJobs), this, job != null);

	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	@Override
	public int compareTo(Move o) {
		return this.getTotalCost()-o.getTotalCost();
	}

}
