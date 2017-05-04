import java.util.HashSet;

// TODO: Auto-generated Javadoc
/**
 * The Class FreightTransportSystem.
 */
public class FreightTransportSystem {

	/** The map graph. */
	private GeoMap mapGraph;

	/** The job list. */
	private HashSet<Job> jobList;

	/**
	 * Instantiates a new freight transport system.
	 *
	 * @param uploadCost
	 *            the upload cost
	 * @param name
	 *            the name
	 */
	public FreightTransportSystem(int uploadCost, String name, String start_loc) {
		this.mapGraph = new GeoMap(uploadCost, name, new TotalCostHeuristic(), start_loc);
		this.jobList = new HashSet<Job>();

	}

	/**
	 * Gets the locations.
	 *
	 * @return the locations
	 */
	public HashSet<Town> getLocations() {
		return this.mapGraph.getLocations();
	}

	/**
	 * Adds the locations.
	 *
	 * @param uploadCost
	 *            the upload cost
	 * @param name
	 *            the name
	 */
	public void addLocation(int uploadCost, String name) {
		this.mapGraph.addLocation(uploadCost, name);
	}

	/**
	 * Adds the adjavcent town cost.
	 *
	 * @param from
	 *            the origin
	 * @param to
	 *            the destination
	 * @param cost
	 *            the cost
	 */
	public void addAdjTownCost(String from, String to, int cost) {
		Town start = null;
		Town end = null;
		for (Town t : this.mapGraph.getLocations()) {
			if (t.getName().equals(from)) {
				start = t;
			}
			if (t.getName().equals(to)) {
				end = t;
			}
		}
		if (start != null && end != null) {
			start.getAdjacentTowns().put(end, cost);
			end.getAdjacentTowns().put(start, cost);

		}
	}

	/**
	 * Adds the job list.
	 *
	 * @param from
	 *            the origin
	 * @param to
	 *            the destination
	 * @return true, if successful
	 */
	public boolean addJobList(String from, String to) {
		Town start = null;
		Town end = null;
		for (Town t : this.mapGraph.getLocations()) {
			if (t.getName().equals(from)) {
				start = t;
			}
			if (t.getName().equals(to)) {
				end = t;
			}
		}
		if (start != null && end != null && start.getAdjacentTowns().containsKey(end)) {
			this.jobList.add(new Job(start, end));
			return true;
		} else {
			// System.out.print("Invalid ");
			return false;
		}
	}

	/**
	 * A star search.
	 */
	public void aStarSearch() {
		Move routes = this.mapGraph.aStarSearch(this.jobList);
		System.out.println(this.mapGraph.getExporedNum() + " nodes expand");
		if (routes != null) {
			System.out.println("cost = " + routes.getCost());
			this.mapGraph.reconstruct_path(routes);
		} else {
			System.out.println("No Solution");
		}

	}

}