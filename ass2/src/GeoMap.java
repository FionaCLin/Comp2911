import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;

public class GeoMap {
	private LinkedList<Town> locations;
	private Town start;
	private int numOfExplored;
	private IHeuristicStrategy heuristic;

	/**
	 * @param locations
	 */
	public GeoMap(int uploadCost, String name, IHeuristicStrategy heuristic) {
		this.locations = new LinkedList<>();
		this.locations.add(new Town(uploadCost, name));
		// change to be more dynamic
		this.start = Town.fetchTownByName(this.locations, "Sydney");
		this.heuristic = heuristic;
	}

	/**
	 * @return the numOfExpored
	 */
	public int getNumOfExpored() {
		return numOfExplored;
	}

	/**
	 * @return the locations
	 */
	public LinkedList<Town> getLocations() {
		return locations;
	}

	public void addLocations(int uploadCost, String name) {
		this.locations.add(new Town(uploadCost, name));
	}

	// equivalent to ==> addEdge() – Appends an edge startVertex → endVertex
	// with a certain weight. This is an O(1) Insertion.
	public void addAdjTownCost(String origin, String destination, int cost) {
		// TODO Auto-generated method stub
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

	public Set<Town> outPaths(Town t) {
		return t.getAdjacentTowns().keySet();
	}

	public int indexOf(Town origin) {
		return this.locations.indexOf(origin);
	}

	// Uniform Cost Search (Dijistra Search)
	public Node aStarSearch(HashSet<Job> jobs) {

		State first_state = new State(this.start, jobs);
		Node first = new Node(first_state, null, false);

		first.setHeuristic(heuristic.calcHeuristic(jobs));

		PriorityQueue<Node> openSet = new PriorityQueue<Node>(new TotalCostComparator());

		HashSet<State> seen = new HashSet<State>();
		openSet.add(first);
		seen.add(first_state);
		while (!openSet.isEmpty()) {

			Node currentPos = openSet.poll();
			this.numOfExplored++;
			Town current = currentPos.getCurrentLocation();

			if (currentPos.getTodo().isEmpty()) {
				return currentPos;
			}
			// make a set of possible moves
			// then for every state of the set
			// if seen set contain it then skip it
			// else add to the openSet and the seen
			for (Town neightbour : current.getAdjacentTowns().keySet()) {
				Node nextMove = currentPos.makeMove(neightbour);
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

	public void reconstruct_path(Node journey) {
		if (journey == null)
			return;
		else if (journey.getVisited() != null) {
			reconstruct_path(journey.getVisited());
			if (journey.isJob()) {
				System.out.print("Job ");
			} else {
				System.out.print("Empty ");
			}
			String printRes = journey.getVisited().getCurrentLocation().getName() + " to ";
			printRes += journey.getCurrentLocation().getName() + "\t";

			System.out.println(printRes);
		}
	}

}
