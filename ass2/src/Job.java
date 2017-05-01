import java.util.Objects;

public class Job {
	private final Town src;
	private final Town dest;

	/**
	 * @param origin
	 * @param destination
	 */
	public Job(Town src, Town dest) {
		//immutable
		this.src = src;
		this.dest = dest;
	}


	/**
	 * @return the origin
	 */
	public Town getOrigin() {
		return this.src;
	}

	/**
	 * @return the destination
	 */
	public Town getDestination() {
		return this.dest;
	}
	
	public int getTotalCost(){
		return src.getCostToAdjacentTown(dest.getName()) + dest.getUnloadCost();
	}
//	@Override
//	public boolean equals(Object o){
//		Job job = (Job)o;
//		return this.src.equals(job.src) && this.dest.equals(job.dest); 
//	}
//	@Override 
//	public int hashCode() {
//		return Objects.hash(src,dest);
//	}
	

}
