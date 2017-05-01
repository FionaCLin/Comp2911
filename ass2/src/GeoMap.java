import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Stream;

public class GeoMap {
	private LinkedList<Town> locations;
	private Town start;
	private int jobSize;
	private int numOfExplored;

	/**
	 * @param locations
	 */
	public GeoMap(int uploadCost, String name) {
		this.locations = new LinkedList<>();
		this.locations.add(new Town(uploadCost, name));
		// change to be more dynamic
		this.start = Town.fetchTownByName(this.locations, "Sydney");
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

	private int heuristic(HashSet<Job> jobs) {
		int res = 0;
		res += jobs.stream().mapToInt(j -> j.getCost()).sum();
		// if (this.jobSize > 10) {
		// if (jobs.size() >= (this.jobSize / 2)) {
		// res += jobs.size() * 100;
		// } else {
		// res += jobs.size() * 3;
		// }
		// }
		// return res;
		return 0;
	}

	// Uniform Cost Search (Dijistra Search)
	public Node aStarSearch(HashSet<Job> jobs) {

		State first_state = new State(this.start, jobs);
		// It always start from Sydney; a frontier to explore
		Node first = new Node(first_state, null, false);

		first.setHeuristic(heuristic(jobs));

		PriorityQueue<Node> openSet = new PriorityQueue<Node>(new TotalCostComparator());
		HashSet<State> seen = new HashSet<State>();
		openSet.add(first);
		seen.add(first_state);
		while (!openSet.isEmpty()) {

			Node currentPos = openSet.poll();
			this.numOfExplored++;
//			System.out.println(this.numOfExplored + " queue " + openSet.size() + " todo Size "
//					+ currentPos.getTodo().size() + " " + currentPos.getTotalCost());
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
//					System.out.println("Seen");
					continue;
				}
				
				nextMove.setHeuristic(heuristic(nextMove.getTodo()));
				openSet.add(nextMove);
				seen.add(nextMove.getState());
			}
		}
		return null;

	}

	public void reconstruct_path(Node journey) {
		// TODO Auto-generated method stub
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
