import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class GeoMap {
	private LinkedList<Town> locations;
	private static Town start;

	/**
	 * @param locations
	 */
	public GeoMap(int uploadCost, String name) {
		this.locations = new LinkedList<>();
		this.locations.add(new Town(uploadCost, name));
		this.start = (Town) this.locations.stream().filter(t -> t.getName().equals("Sydney"));
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

	private int heuristic(Node journey, LinkedList<Job> jobs) {
		
		// return journey.getTotal() + remainJobTotalCost;
		return 0;
	}

	// Uniform Cost Search (Dijistra Search)
	public Node UniformCostSearch(LinkedList<Job> jobs) {

		// It always start from Sydney; a frontier to explore
		Node init = new Node(this.start, null, false);
		init.setTodo(jobs);
		// TODO: set the heuristic value
		LinkedList<Town> closedSet = new LinkedList<>();
		PriorityQueue<Node> openSet = new PriorityQueue<Node>();
		openSet.add(init);

		HashMap<Town, Integer> gScore = new HashMap<Town, Integer>();
		HashMap<Town, Integer> fScore = new HashMap<Town, Integer>();
		Iterator<Town> itr = this.locations.iterator();
		while (itr.hasNext()) {
			Town t = itr.next();
			if (t.equals(this.start)) {
				gScore.put(this.start, 0);
				// For the first node, that value is completely heuristic.
				// the goal state from start would be the travel cost exactly
				// same as the
				// the job required cost(The total cost)
				fScore.put(this.start, heuristic(init, jobs));
			} else {
				gScore.put(t, Integer.MAX_VALUE);
				fScore.put(t, Integer.MAX_VALUE);
			}
		}

		while (openSet.isEmpty()) {
			Node currentPos = openSet.poll();
			// define when we meet the goal state.
			if (currentPos.getTodo().isEmpty()) {
				return currentPos;
			}
			Iterator<Job> search = jobs.iterator();
			openSet.remove(currentPos);
			Town current = currentPos.getCurrentLocation();
			boolean isRelatedToJob = jobs.stream()
					.anyMatch(j -> j.getOrigin().equals(current) || j.getDestination().equals(current));
			if (!isRelatedToJob) {
				closedSet.add(current);
			}
			Set<Entry<Town, Integer>> neightbours = current.getAdjacentTowns().entrySet();
			for (Entry<Town, Integer> neightbour : neightbours) {
				// check if there is match job
				Job todo = null;
				while (search.hasNext()) {
					Job searchToDo = search.next();
					if (searchToDo.getOrigin().equals(current) && searchToDo.getDestination().equals(neightbour))
						todo = searchToDo;
				}
				boolean isJob = (todo != null);
				Node nextMove = null;
				if (isJob) {
					// there is a job to do, make a new node and build the path
					nextMove = new Node(todo.getDestination(), currentPos, isJob);
					nextMove.setTodo(currentPos.getTodo());
					nextMove.getTodo().remove(todo);
				} else {
					if (!(closedSet.contains(neightbour) && currentPos.getTodo().size() == jobs.size())) {
						nextMove = new Node(neightbour.getKey(), currentPos, isJob);
						nextMove.setTodo(currentPos.getTodo());

					}
				}
				// TODO: calculate the heuristic
				openSet.add(nextMove);
			}
		}
		return null;
	}

	private void reconstruct_path(Node journey) {
		// TODO Auto-generated method stub
		if (journey == null) return ;
		reconstruct_path(journey.getVisited());
		if(journey.isJob()){
			System.out.print("Job ");
		} else {
			System.out.print("Empty ");
		}
		String printRes = journey.getVisited().getCurrentLocation()+ " to ";
		printRes += journey.getCurrentLocation();
		System.out.println(printRes);
	}

}
