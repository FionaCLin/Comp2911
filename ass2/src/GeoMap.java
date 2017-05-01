import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
//		if (this.jobSize > 10) {
//			if (jobs.size() >= (this.jobSize / 2)) {
//				res += jobs.size() * 100;
//			} else {
//				res += jobs.size() * 3;
//			}
//		}
		return res;
//		 return 0;
	}

	// Uniform Cost Search (Dijistra Search)
	public Node UniformCostSearch(LinkedList<Job> jobs) {
		this.jobSize = jobs.size();
		
		// It always start from Sydney; a frontier to explore
		Node init = new Node(this.start, null, false);
		init.setTodo(jobs);
		init.setHeuristic(heuristic(jobs));
		// TODO: set the heuristic value

		PriorityQueue<Node> openSet = new PriorityQueue<Node>(new TotalCostComparator());

		HashMap<Town, HashMap<Integer, LinkedList<Node>>> closeSet = new HashMap<Town, HashMap<Integer, LinkedList<Node>>>();
		openSet.add(init);

		while (!openSet.isEmpty()) {

			Node currentPos = openSet.poll();
			this.numOfExpored++;
			System.out.println(this.numOfExpored + " queue " + openSet.size() + " todo Size "
					+ currentPos.getTodo().size() + " " + currentPos.getTotalCost());
			Town current = currentPos.getCurrentLocation();

			if (closeSet.containsKey(current)) {
				int jSize = currentPos.getTodo().size();
				HashMap<Integer, LinkedList<Node>> temp = closeSet.get(current);
				if (temp == null) {
					HashMap<Integer, LinkedList<Node>> Set = new HashMap<Integer, LinkedList<Node>>();
					LinkedList<Node> newList = new LinkedList<Node>();
					newList.add(currentPos);
					Set.put(jSize, newList);
				} else if (temp.containsKey(jSize)) {
					temp.get(jSize).add(currentPos);
				} else {
					LinkedList<Node> s = new LinkedList<Node>();
					s.add(currentPos);
					temp.put(jSize, s);
				}
			} else {
				HashMap<Integer, LinkedList<Node>> Set = new HashMap<Integer, LinkedList<Node>>();
				LinkedList<Node> newList = new LinkedList<Node>();
				int jSize = currentPos.getTodo().size();
				newList.add(currentPos);
				Set.put(jSize, newList);
				closeSet.put(current, Set);
			}

			// add <n, p> to CLOSED

			if (currentPos.getTodo().isEmpty()) {// && openSet2.isEmpty()) {
				return currentPos;
			}

			Set<Entry<Town, Integer>> neightbours = current.getAdjacentTowns().entrySet();
			for (Entry<Town, Integer> neightbour : neightbours) {
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
					nextMove.setHeuristic(heuristic(nextMove.getTodo()));
					openSet.add(nextMove);

				} else {
					// if (currentPos.isInfinitLoop(neightbour.getKey()))
					// continue;
					nextMove = new Node(neightbour.getKey(), currentPos, isJob);
					nextMove.setTodo(currentPos.getTodo());
					nextMove.setHeuristic(heuristic(nextMove.getTodo()));
					// if <n’, p’> is on CLOSED then if f(n’, p+e) < f(n’, p’)

					Node toRemove = null;
					boolean canAdd = true;
					int jobLen = nextMove.getTodo().size();
					if (closeSet.containsKey(nextMove.getCurrentLocation())) {
						HashMap<Integer, LinkedList<Node>> hset = closeSet.get(nextMove.getCurrentLocation());
						if (hset.containsKey(jobLen)) {
							LinkedList<Node> set = hset.get(jobLen);
							Iterator<Node> itr = set.iterator();
							while (itr.hasNext()) {
								Node temp = itr.next();
								if (temp.getTodo().containsAll(nextMove.getTodo())) {
									if (temp.getCost() >= nextMove.getCost()) {
										toRemove = temp;
									} else {
										canAdd = false;
									}
								}
							}
							if (toRemove != null) {
								set.remove(toRemove);
							}
						}
					}
					if (canAdd) {
						openSet.add(nextMove);
					}
				}
				// if (nextMove.getTotalCost() < ) {
				// remove <n’, p’> from closed and add <n’, p+e> to OPEN
				// else if <n’, p’> is on OPEN then if f(n’, p+e) < f(n’, p’)
				// replace <n’, p’> by <n’, p+e> on OPEN
				// else if n’ is not on OPEN add <n’, p+e> to OPEN
				// } else if {

				// }
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
