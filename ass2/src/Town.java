
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

/***
 * -- Town is represent Graph's Vertices; -- Using adjList to contain the
 * adjacent vertices and edge weight
 * 
 * @author fiona
 *
 */
public class Town {
	private int uploadCost;
	private String name;
	private HashMap<Town, Integer> adjacentTowns;

	/**
	 * @param uploadCost
	 * @param name
	 */
	public Town(int uploadCost, String name) {
		this.uploadCost = uploadCost;
		this.name = name;
		this.adjacentTowns = new HashMap<Town, Integer>();
	}

	/**
	 * @return the adjacentTowns
	 */
	public HashMap<Town, Integer> getAdjacentTowns() {
		return adjacentTowns;
	}

	/**
	 * @return the adjacentTowns
	 */
	public Integer getCostToAdjacentTown(String name) {
//		Town res = (Town) this.adjacentTowns.keySet().stream().filter(t -> t.getName().equals(name));
		Town res = this.fetchTownByName(this.adjacentTowns.keySet(), name);
		return adjacentTowns.get(res);
	}

	/**
	 * @return the uploadCost
	 */
	public int getUnloadCost() {
		return uploadCost;
	}

	/**
	 * @param uploadCost
	 *            the uploadCost to set
	 */
	public void setUploadCost(int uploadCost) {
		this.uploadCost = uploadCost;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	// getNumberOfEdgesFromVertex() – Returns the number of edges coming out
	// from a vertex. Basically, it returns the length of the linked list
	// associated with the vertex.
	public int getNumberOfEdgesFromTown() {
		return this.getAdjacentTowns().size();
	}

	// getEdgesFromVertex() – Returns a LinkedList object of edges that come out
	// of this vertex. Changes made to the object returned does not affect the
//	// encapsulated adjacency list.
//	public LinkedList<RoutePath> getEdgesFromTown() {
//		LinkedList<RoutePath> res = new LinkedList<RoutePath>();
//		for (Entry<Town, Integer> entry : this.adjacentTowns.entrySet()) {
//			res.add(new RoutePath(this, entry.getKey(), entry.getValue()));
//		}
//		return res;
//	}
//
//	public RoutePath getEdgesFromTown(Town end) {
//
//		for (Entry<Town, Integer> entry : this.adjacentTowns.entrySet()) {
//			if (entry.getKey().equals(end)) {
//				return new RoutePath(this, entry.getKey(), entry.getValue());
//			}
//		}
//		return null;
//	}

	// printAdjacencyList() – Prints the adjacency list top-to-down.
	public void printAdjacencyList() {
		this.adjacentTowns.entrySet().forEach(t -> {
			System.out.println("\t" + t.getKey().getName() + " $" + t.getValue());
		});
	}
	// removeEdge() – Removes an edge coming out of startVertex. It expects an
	// object of Pair<Integer, Integer>

	public boolean hasEdge(Town t) {
		return this.adjacentTowns.containsKey(t);
	}
	public static Town fetchTownByName(Collection<Town> locations, String name) {
		Iterator<Town> itr = locations.iterator();
		while (itr.hasNext()) {
			Town temp = itr.next();
			if (temp.getName().equals(name)) {
				return temp;
			}
		}
		return null;
	}
}
