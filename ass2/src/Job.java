
public class Job extends RoutePath {

	/**
	 * @param origin
	 * @param destination
	 */
	public Job(Town origin, Town destination) {
		super(new RoutePath(null, origin, 0), destination, destination.getUnloadCost());
	}

	/**
	 * @return the origin
	 */
	public Town getOrigin() {
		return super.getFrom().getCurrent();
	}

	/**
	 * @return the destination
	 */
	public Town getDestination() {
		return super.getCurrent();
	}

	/**
	 * @return the unload
	 */
	public int getUnloadCost() {
		return super.getCurrent().getUnloadCost();
	}

	public void setOrigin(RoutePath from) {
		super.setFrom(from);
	}
}
