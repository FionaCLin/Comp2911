import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;

public class GeoMap {
	private LinkedList<Town> locations;
	private Town start;
	private int numOfExpored;

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
		return numOfExpored;
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

	private int heuristic(LinkedList<Job> jobs) {
		int res = 0;
		res += jobs.stream().mapToInt(j -> j.getCost()).sum();
		return res;
		// return 0;
	}

	// Uniform Cost Search (Dijistra Search)
	public Node UniformCostSearch(LinkedList<Job> jobs) {
		// It always start from Sydney; a frontier to explore
		Node init = new Node(this.start, null, false);
		init.setTodo(jobs);
		init.setHeuristic(heuristic(jobs));
		PriorityQueue<Node> openSet = new PriorityQueue<Node>();
		openSet.add(init);
		int k = 1;
		while (!openSet.isEmpty()) {

			Node currentPos = openSet.poll();

			this.numOfExpored++;
//			int minJobSize = jobs.size();
//			if (openSet.size() > 100000) {
//				System.out.println(" Trim " + k++ + "current #Jobs" + currentPos.getTodo().size());
//				PriorityQueue<Node> temp = new PriorityQueue<Node>();
//				for (int i = 0; i < 99999; i++) {
//					if (minJobSize > currentPos.getTodo().size()) {
//						minJobSize = currentPos.getTodo().size();
//					}
//					Node tempNode = openSet.poll();
//					if (tempNode.getTodo().size() == minJobSize) {
//						temp.add(tempNode);
//					}
//
//				}
//				openSet = temp;
//			}
			// define when we meet the goal state.
			if (currentPos.getTodo().isEmpty()) {
				return currentPos;
			}

			Town current = currentPos.getCurrentLocation();

			Set<Town> neightbours = current.getAdjacentTowns().keySet();
			for (Town neightbour : neightbours) {
				// System.out.println(
				// "##" + current.getName() + "=>" + neightbour.getName() + " "
				// + currentPos.getTotalCost());
			
//				boolean isReturnedJob = false;
				// Instant s = Instant.now();
				Job todo = null;
				Iterator<Job> search = jobs.iterator();
				while (search.hasNext()) {
					Job searchToDo = search.next();
					if (searchToDo.getOrigin().equals(current) && searchToDo.getDestination().equals(neightbour)) {
						todo = searchToDo;
						break;
					}
				}
				Job rTodo = null;
				if (todo != null) {
					search = jobs.iterator();
					while (search.hasNext()) {
						Job searchToDo = search.next();
						if (searchToDo.getOrigin().equals(neightbour) && searchToDo.getDestination().equals(current)) {
							rTodo = searchToDo;
							break;
						}
					}
				}
				if (currentPos.isInfinitLoop(neightbour) && (todo == null || rTodo == null))
					continue;
				boolean isJob = (todo != null);
				boolean isReturnedJob = (rTodo != null);
				Node nextMove = null;
				if (isJob) {
//					if (!isReturnedJob) {
						// there is a job to do, make a new node and build the
						// path
						nextMove = new Node(todo.getDestination(), currentPos, isJob);
						nextMove.getTodo().remove(todo);
				} else if (isReturnedJob) {
						nextMove = new Node(neightbour, currentPos, isJob);
						nextMove = new Node(rTodo.getDestination(), nextMove, isReturnedJob);
						nextMove.getTodo().remove(rTodo);	
				} else {
					nextMove = new Node(neightbour, currentPos, isJob);
				}
				nextMove.setHeuristic(heuristic(nextMove.getTodo()));
				openSet.add(nextMove);
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
