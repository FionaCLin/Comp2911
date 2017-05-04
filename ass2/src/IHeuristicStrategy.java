import java.util.HashSet;

// TODO: Auto-generated Javadoc
/**
 * The Interface IHeuristicStrategy.
 */
public interface IHeuristicStrategy {

	/**
	 * 
	 * Calculate the heuristic .
	 *
	 * @param jobs
	 *            the set of jobs
	 * @return the total cost int
	 */
	public int calcHeuristic(HashSet<Job> jobs);
}
