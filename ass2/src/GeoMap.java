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

	private int heuristic(RoutePath journey, LinkedList<Job> jobs) {
		Iterator<Job> itr = jobs.iterator();
		int remainJobTotalCost = 0;
		while (itr.hasNext()) {
			remainJobTotalCost += itr.next().getTotal();
		}
		return journey.getTotal() + remainJobTotalCost;
	}

	// Uniform Cost Search (Dijistra Search)
	public RoutePath UniformCostSearch(LinkedList<Job> jobs) {

		// It always start from Sydney; a frontier to explore
		RoutePath init = new RoutePath(null, this.start, 0);

		LinkedList<RoutePath> closeSet = new LinkedList<>();

		PriorityQueue<RoutePath> openSet = new PriorityQueue<RoutePath>();
		openSet.add(init);

		HashMap<Town, Integer> gScore = new HashMap<Town, Integer>();
		HashMap<Town, Integer> fScore = new HashMap<Town, Integer>();
		Iterator<Town> itr = this.locations.iterator();
		while (itr.hasNext()) {
			Town t = itr.next();
			if (t.equals(this.start)) {
				gScore.put(this.start, 0);
				// For the first node, that value is completely heuristic.
				fScore.put(this.start, heuristic(init, jobs));
			} else {
				gScore.put(t, Integer.MAX_VALUE);
				fScore.put(t, Integer.MAX_VALUE);
			}
		}

		while (openSet.isEmpty()) {
			RoutePath currentPath = openSet.poll();
			// define when we meet the goal state.
			if (jobs.isEmpty()) {
				return null;
			}
			Iterator<Job> search = jobs.iterator();
			openSet.remove(currentPath);
			closeSet.add(currentPath);
			Town current = currentPath.getCurrent();
			Set<Entry<Town, Integer>> neightbours = current.getAdjacentTowns().entrySet();
			for (Entry<Town, Integer> neightbour : neightbours) {
				// check if there is match job
				Job todo = null;
				while (search.hasNext()) {
					Job searchToDo = search.next();
					if (searchToDo.getOrigin().equals(current) && searchToDo.getDestination().equals(neightbour))
						todo = searchToDo;
				}
				if (todo != null) {
					// there is a job to do, build my path
					todo.setFrom(currentPath);
					if (!openSet.contains(todo))
						openSet.add(todo);
					else if (todo.getTotal() >= gScore.get(neighbour.getKey()))
						continue;
					gScore.put(neighbour.getKey(), tentativeGScore);
					fScore.put(neighbour.getKey(),
							gScore.get(neighbour.getKey()) + heuristic(neighbour.getKey(), jobs));
				}

			}
		}
		return res.getFirst();
	}

	private Journey reconstruct_path(HashMap<Town, Town> comeForm, Town cur) {
		// TODO Auto-generated method stub
		Journey j = new Journey();
		return null;
	}

}
