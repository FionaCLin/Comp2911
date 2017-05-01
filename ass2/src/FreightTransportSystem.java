import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class FreightTransportSystem {
	private GeoMap mapGraph;
	private HashSet<Job> jobList;

	/**
	 * @param locations
	 */
	public FreightTransportSystem(int uploadCost, String name) {
		this.mapGraph = new GeoMap(uploadCost, name, new TotalCostHeuristic());
		this.jobList = new HashSet<Job>();

	}

	public LinkedList<Town> getLocations() {
		return this.mapGraph.getLocations();
	}

	public void addLocations(int uploadCost, String name) {
		this.mapGraph.addLocations(uploadCost, name);
	}

	public void addAdjTownCost(String origin, String destination, int cost) {
		Town start = null;
		Town end = null;
		for (Town t : this.mapGraph.getLocations()) {
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

	public boolean addJobList(String origin, String destination) {
		// TODO Auto-generated method stub
		Town start = null;
		Town end = null;
		for (Town t : this.mapGraph.getLocations()) {
			if (t.getName().equals(origin)) {
				start = t;
			}
			if (t.getName().equals(destination)) {
				end = t;
			}
		}
		if (start != null && end != null && start.getAdjacentTowns().containsKey(end)) {

			this.jobList.add(new Job(start, end));
			return true;
		} else {
			System.out.print("Invalid ");
			return false;
		}
	}


	public void AStarSearch() {

		Node routes = this.mapGraph.aStarSearch(this.jobList);

		System.out.println(this.mapGraph.getNumOfExpored() + " nodes expand");
		if (routes != null) {
			System.out.println("cost = " + routes.getCost());
			this.mapGraph.reconstruct_path(routes);
		} else {
			System.out.println("No Solution");
		}

	}

}