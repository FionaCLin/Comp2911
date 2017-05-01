
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
	//immutable
	private final int uploadCost;
	private final String name;
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
	 * @return the name
	 */
	public String getName() {
		return name;
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
