
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

/**
 * * -- Town is represent Graph's Vertices; -- Using adjList to contain the
 * adjacent vertices and edge weight.
 *
 * @author fiona
 */
public class Town {

	/** The upload cost. */
	// immutable
	private final int unloadCost;

	/** The name. */
	private final String name;

	/** The adjacent towns. */
	private HashMap<Town, Integer> adjacentTowns;

	/**
	 * Instantiates a new town.
	 *
	 * @param unloadCost
	 *            the upload cost
	 * @param name
	 *            the name
	 */
	public Town(int unloadCost, String name) {
		this.unloadCost = unloadCost;
		this.name = name;
		this.adjacentTowns = new HashMap<Town, Integer>();
	}

	/**
	 * Gets the adjacent towns.
	 *
	 * @return the adjacentTowns
	 */
	public HashMap<Town, Integer> getAdjacentTowns() {
		return adjacentTowns;
	}

	/**
	 * Gets the unload cost.
	 *
	 * @return the uploadCost
	 */
	public int getUnloadCost() {
		return unloadCost;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
