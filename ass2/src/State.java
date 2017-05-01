
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class State {
	//immutable
	private final Town town;
	private final HashSet<Job> jobs; // the list of job remaining
	/**
	 * @param town
	 * @param jobs
	 */
	public State(Town town, HashSet<Job> jobs) {
		this.town = town;
		this.jobs = jobs;
	}
	/**
	 * @return the town
	 */
	public Town getTown() {
		return town;
	}
	/**
	 * @return the jobs
	 */
	public HashSet<Job> getJobs() {
		return jobs;
	}
	//the search creates multiple states objects with the same contents
	// they have to compare each other with this equal function 
	// to check they are the same state
	@Override
	public boolean equals(Object o){
		State state = (State)o;
		return (this.town.equals(state.town)
				&& this.jobs.equals(state.jobs));
	}
	@Override 
	public int hashCode() {
		return Objects.hash(town,jobs);
	}
}
