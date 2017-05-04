import java.util.Objects;

// TODO: Auto-generated Javadoc
/**
 * The Class Job.
 */
public class Job {

	/** The src. */
	private final Town src;

	/** The dest. */
	private final Town dest;

	/**
	 * Instantiates a new job.
	 *
	 * @param src
	 *            the src
	 * @param dest
	 *            the dest
	 */
	public Job(Town src, Town dest) {
		// immutable
		this.src = src;
		this.dest = dest;
	}

	/**
	 * Gets the origin.
	 *
	 * @return the origin
	 */
	public Town getStart() {
		return this.src;
	}

	/**
	 * Gets the destination.
	 *
	 * @return the destination
	 */
	public Town getDestination() {
		return this.dest;
	}

	/**
	 * Gets the total cost.
	 *
	 * @return the total cost
	 */
	public int getTotalCost() {
		// return src.getCostToAdjacentTown(dest.getName()) +
		// dest.getUnloadCost();
		return src.getAdjacentTowns().get(dest) + dest.getUnloadCost();
	}
	// @Override
	// public boolean equals(Object o){
	// Job job = (Job)o;
	// return this.src.equals(job.src) && this.dest.equals(job.dest);
	// }
	// @Override
	// public int hashCode() {
	// return Objects.hash(src,dest);
	// }
	//

}
