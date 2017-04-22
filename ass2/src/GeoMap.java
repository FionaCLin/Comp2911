import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;

public class GeoMap {
	private LinkedList<Town> locations;
	private static Town start;
	private int numOfExpored;

	/**
	 * @param locations
	 */
	public GeoMap(int uploadCost, String name) {
		this.locations = new LinkedList<>();
		this.locations.add(new Town(uploadCost, name));
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

	// getNumberOfVertices() – Returns the number of vertices in the graph. This
	// value does not change once the graph is created.
	public int getNumberOfTowns() {
		return this.locations.size();
	}

	public int indexOf(Town origin) {
		return this.locations.indexOf(origin);
	}
	//
	// // BFS
	// public LinkedList<Town> bfs(Town origin) {
	// LinkedList<Town> visit = new LinkedList<Town>();
	// boolean[] seen = new boolean[this.getNumberOfTowns()];
	// Queue<RoutePath> q = new LinkedList<RoutePath>();
	// q.add(new RoutePath(origin, origin, 0));
	// while (!q.isEmpty()) {
	// RoutePath e = q.poll();
	// for (RoutePath p : e.getEnd().getEdgesFromTown()) {
	// if (!seen[indexOf(p.getEnd())]) {
	// q.add(p);
	// seen[indexOf(p.getEnd())] = true;
	// visit.addLast(p.getEnd());
	// }
	// }
	// }
	// return visit;
	// }
	//
	// // find Path from src to dest
	// public LinkedList<RoutePath> findShortestPath(Town src, Town dest) {
	// boolean isFound = false;
	// LinkedList<RoutePath> visit = new LinkedList<RoutePath>();
	// boolean[] seen = new boolean[this.getNumberOfTowns()];
	// int[] st = new int[this.getNumberOfTowns()];
	// Queue<RoutePath> q = new LinkedList<RoutePath>();
	// q.add(new RoutePath(src, src, 0));
	// while (!q.isEmpty() && !isFound) {
	// RoutePath e = q.poll();
	// for (RoutePath p : e.getEnd().getEdgesFromTown()) {
	// if (!seen[indexOf(p.getEnd())]) {
	// q.add(p);
	// seen[indexOf(p.getEnd())] = true;
	// st[indexOf(p.getEnd())] = indexOf(p.getStart());
	// }
	// if (p.getEnd().equals(dest)) {
	// isFound = true;
	// break;
	// }
	// }
	//
	// }
	// if (isFound) {
	// int i = indexOf(dest);
	// for (; i > indexOf(src);) {
	// Town start = this.getLocations().get(i);
	// i = st[i];
	// Town end = this.getLocations().get(i);
	// RoutePath p = start.getEdgesFromTown(end);
	// visit.add(p);
	// }
	//
	// }
	// return visit;
	// }

	private int heuristic(LinkedList<Job> jobs) {
		int res = 0;
		res += jobs.stream().mapToInt(j -> j.getCost()).sum();
		return res;
//		return 0;
	}

	// Uniform Cost Search (Dijistra Search)
	public Node UniformCostSearch(LinkedList<Job> jobs) {

		// It always start from Sydney; a frontier to explore
		Node init = new Node(this.start, null, false);
		init.setTodo(jobs);
		init.setHeuristic(heuristic(jobs));
		// TODO: set the heuristic value

		PriorityQueue<Node> openSet = new PriorityQueue<Node>();
		openSet.add(init);

		while (!openSet.isEmpty()) {
			Node currentPos = openSet.poll();
			this.numOfExpored++;
			// define when we meet the goal state.
			if (currentPos.getTodo().isEmpty()) {
				return currentPos;
			}

			Town current = currentPos.getCurrentLocation();

			Set<Entry<Town, Integer>> neightbours = current.getAdjacentTowns().entrySet();
			for (Entry<Town, Integer> neightbour : neightbours) {
				if (currentPos.isInfinitLoop(neightbour.getKey()))
					continue;
				// check if there is match job
				Job todo = null;
				Iterator<Job> search = jobs.iterator();
				while (search.hasNext()) {
					Job searchToDo = search.next();
					if (searchToDo.getOrigin().equals(current)
							&& searchToDo.getDestination().equals(neightbour.getKey())) {
						todo = searchToDo;
						break;
					}
				}
				boolean isJob = (todo != null);
				Node nextMove = null;
				if (isJob) {
					// there is a job to do, make a new node and build the path
					nextMove = new Node(todo.getDestination(), currentPos, isJob);
					nextMove.getTodo().remove(todo);
				} else {
					nextMove = new Node(neightbour.getKey(), currentPos, isJob);
					nextMove.setTodo(currentPos.getTodo());
				}
				// TODO: calculate the heuristic
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
			printRes += journey.getCurrentLocation().getName();
			System.out.println(printRes);
		}
	}

}
