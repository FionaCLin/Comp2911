import java.util.HashSet;

public class TotalCostHeuristic implements IHeuristicStrategy {
	
	@Override
	public int calcHeuristic(HashSet<Job> jobs) {
		int res = 0;
		res += jobs.stream().mapToInt(j -> j.getTotalCost()).sum();
		return res;
	}
}
