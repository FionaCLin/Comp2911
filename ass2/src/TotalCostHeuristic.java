import java.util.HashSet;

// TODO: Auto-generated Javadoc
/**
 * The Class TotalCostHeuristic.
 */
public class TotalCostHeuristic implements IHeuristicStrategy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see IHeuristicStrategy#calcHeuristic(java.util.HashSet)
	 */
	@Override
	public int calcHeuristic(HashSet<Job> jobs) {
		int res = 0;
		res += 6.5 * (jobs.stream().mapToInt(j -> j.getTotalCost()).sum()) / 10;
//		res += jobs.size()*5;
		return res;
	}
}
