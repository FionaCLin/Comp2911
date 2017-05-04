import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Class GeoMap.
 */
public class GeoMap {
	private static String START_LOC;
	/** The locations. */
	private HashSet<Town> locations;

	/** The num of explored. */
	private int numOfExplored;

	/** The heuristic. */
	private IHeuristicStrategy heuristic;

	/**
	 * Instantiates a new geo map.
	 *
	 * @param Gives
	 *            the town object an unloading cost
	 * @param Name
	 *            the town
	 * @param heuristic
	 *            the class implements heuristic function
	 */
	public GeoMap(int unloadCost, String name, IHeuristicStrategy heuristic, String start_loc) {
		this.locations = new HashSet<>();
		this.locations.add(new Town(unloadCost, name));
		this.START_LOC = start_loc;
		this.heuristic = heuristic;
	}

	/**
	 * Gets the num of expored.
	 *
	 * @return the num of expored
	 */
	public int getExporedNum() {
		return numOfExplored;
	}

	/**
	 * Gets the locations.
	 *
	 * @return the locations
	 */
	public HashSet<Town> getLocations() {
		return locations;
	}

	/**
	 * Adds the locations.
	 *
	 * @param Pass
	 *            an unload cost into the town object
	 * @param Name
	 *            the town
	 */
	public void addLocation(int unloadCost, String name) {
		this.locations.add(new Town(unloadCost, name));
	}

	/**
	 * Adds the adj town cost.
	 *
	 * @param Pass
	 *            in the starting town as a string
	 * @param Pass
	 *            in the destination town as a string
	 * @param Pass
	 *            in the unloading cost for the town
	 */
	public void addAdjTownCost(String origin, String destination, int cost) {
		Town start = null;
		Town end = null;
		for (Town t : this.locations) {
			if (t.getName().equals(origin)) {
				start = t;
			}
			if (t.getName().equals(destination)) {
				end = t;
			}
		}
		if (start != null && end != null) {
			start.getAdjacentTowns().put(end, cost);
			end.getAdjacentTowns().put(start, cost);

		}
	}

	/**
	 * A* search.
	 *
	 * @param input
	 *            the list of jobs to be searched
	 * @return the node
	 */
	public Move aStarSearch(HashSet<Job> jobs) {
		this.numOfExplored = 0;
		Town start = null;
		for (Town t : this.locations) {
			if (t.getName().equals(START_LOC)) {
				start = t;
			}
		}

		State first_state = new State(start, jobs);
		Move first = new Move(first_state, null, false);

		first.setHeuristic(heuristic.calcHeuristic(jobs));

		PriorityQueue<Move> openSet = new PriorityQueue<Move>();

		HashSet<State> seen = new HashSet<State>();
		openSet.add(first);
		seen.add(first_state);

		while (!openSet.isEmpty()) {

			Move currentPos = openSet.poll();
			this.numOfExplored++;
			Town current = currentPos.getLocation();

			if (currentPos.getTodo().isEmpty()) {
				return currentPos;
			}
			// make a set of possible moves
			// then for every state of the set
			// if seen set contain it then skip it
			// else add to the openSet and the seen
			for (Town neightbour : current.getAdjacentTowns().keySet()) {
				Move nextMove = currentPos.makeMove(neightbour);
				if (seen.contains(nextMove.getState())) {
					continue;
				}
				nextMove.setHeuristic(heuristic.calcHeuristic(nextMove.getTodo()));
				openSet.add(nextMove);
				seen.add(nextMove.getState());
			}
		}
		return null;
	}

	/**
	 * Reconstruct path.
	 *
	 * @param Read
	 *            in the optimal path one node at a time.
	 */
	public void reconstruct_path(Move journey) {
		if (journey == null)
			return;
		else if (journey.getVisited() != null) {
			reconstruct_path(journey.getVisited());
			if (journey.isJob()) {
				System.out.print("Job ");
			} else {
				System.out.print("Empty ");
			}
			String printRes = journey.getVisited().getLocation().getName() + " to ";
			printRes += journey.getLocation().getName() + "\t";
			System.out.println(printRes);
		}
	}
}
