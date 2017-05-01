
public class Job {
	private Town src;
	private Town dest;
	private int totalCost;

	/**
	 * @param origin
	 * @param destination
	 */
	public Job(Town src, Town dest) {
		//immutable
		this.src = src;
		this.dest = dest;
		this.setTotal();
	}

	public void setTotal() {
		this.totalCost = this.src.getCostToAdjacentTown(dest.getName()) + this.dest.getUnloadCost();
	}

	/**
	 * @return the origin
	 */
	public Town getOrigin() {
		return this.src;
	}

	/**
	 * @return the destination
	 */
	public Town getDestination() {
		return this.dest;
	}

	/**
	 * @return the unload
	 */
	public int getCost() {
		return this.totalCost;
	}

	

}
