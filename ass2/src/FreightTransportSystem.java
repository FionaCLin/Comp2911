import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class FreightTransportSystem {
	private GeoMap mapGraph;
	private LinkedList<Job> jobList;
	/**
	 * @param locations
	 */
	public FreightTransportSystem(int uploadCost, String name) {
		this.mapGraph = new GeoMap(uploadCost, name);
		this.jobList = new LinkedList<>();

	}

	/**
	 * @return the locations
	 */
	public LinkedList<Town> getLocations() {
		return this.mapGraph.getLocations();
	}

	public void addLocations(int uploadCost, String name) {
		this.mapGraph.addLocations(uploadCost, name);
	}

	public void addAdjTownCost(String origin, String destination, int cost) {
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

	/**
	 * @return the jobList
	 */
	public LinkedList<Job> getJobList() {
		return jobList;
	}

	public GeoMap getMapGraph() {
		return mapGraph;
	}

	public void AStarSearch() {
		Instant s = Instant.now();
		
		Node routes = this.mapGraph.aStarSearch(new HashSet<Job>(this.jobList));
		Instant e = Instant.now();
		Duration d = Duration.between(s, e);
		System.out.println("Job finding time: " + d.getSeconds());
		
		System.out.println(this.mapGraph.getNumOfExpored() +" nodes expand");	
		
		System.out.println("cost = "+ routes.getCost());		
		this.mapGraph.reconstruct_path(routes);
		
		
	}

}