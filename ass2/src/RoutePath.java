
public class RoutePath implements Comparable<RoutePath> {
	private RoutePath from;
	private Town current;
	private int cost;
	private int unloadcost;
	private int total;

	/**
	 * @param vertices
	 * @param cost
	 */
	public RoutePath(RoutePath from, Town current, int cost) {
		this.from = from;
		this.current = current;
		if (from == null) {
			this.cost = 0;
			this.unloadcost = 0;
			this.total = 0;
		} else {
			this.unloadcost = cost;
			this.cost = this.setCost();
			this.setTotal();
		}
	}

	public int setCost() {
		return this.from.getCurrent().getCostToAdjacentTown(current.getName());
	}

	public void setTotal() {
		this.total = this.from.getTotal() + this.getCost() + this.unloadcost;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @return the vertices
	 */
	public RoutePath getFrom() {
		return from;
	}

	/**
	 * @return the vertices
	 */
	public Town getCurrent() {
		return current;
	}

	/**
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	protected void setFrom(RoutePath from) {
		this.from = from;
		this.cost = this.setCost();
		this.setTotal();
	}

	@Override
	public int compareTo(RoutePath o) {
		return this.getTotal() - o.getTotal();
	}
}
