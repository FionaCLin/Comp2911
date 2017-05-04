
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Class State.
 */
public class State {

	/** The town. */
	// immutable
	private final Town town;

	/** The jobs. */
	private final HashSet<Job> jobs; // the list of job remaining

	/**
	 * Instantiates a new state.
	 *
	 * @param town
	 *            the town
	 * @param jobs
	 *            the jobs
	 */
	public State(Town town, HashSet<Job> jobs) {
		this.town = town;
		this.jobs = jobs;
	}

	/**
	 * Gets the town.
	 *
	 * @return the town
	 */
	public Town getTown() {
		return town;
	}

	/**
	 * Gets the jobs.
	 *
	 * @return the jobs
	 */
	public HashSet<Job> getJobs() {
		return jobs;
	}

	// the search creates multiple states objects with the same contents
	// they have to compare each other with this equal function
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	// to check they are the same state
	@Override
	public boolean equals(Object o) {
		State state = (State) o;
		return (this.town.equals(state.town) && this.jobs.equals(state.jobs));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(town, jobs);
	}
}
