import java.io.*;
import java.time.Duration;
import java.time.Instant;

// TODO: Auto-generated Javadoc
/**
 * The Class FreightSystem.
 */
public class FreightSystem {

	/** The start loc. */
	static final String START_LOC = "Sydney";

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		BufferedReader bR = null;
		if (args.length != 1) {
			System.out.println("Require input text file as command argument");
		} else {
			FreightTransportSystem freightTranSys = null;
			try {
				InputStream iS = new FileInputStream(args[0]);
				DataInputStream dS = new DataInputStream(iS);
				bR = new BufferedReader(new InputStreamReader(dS));
				String line;
				while ((line = bR.readLine()) != null) {
					String command = line;
					if (line.indexOf("#") != -1) {
						command = line.substring(0, line.indexOf("#"));
					}
					if (command.startsWith("Unloading")) {
						int cost = Integer.parseInt(command.split(" ")[1]);
						String name = command.split(" ")[2];
						if (freightTranSys == null) {
							freightTranSys = new FreightTransportSystem(cost, name, START_LOC);
						} else {
							freightTranSys.addLocation(cost, name);
						}
					} else if (command.startsWith("Cost")) {
						int cost = Integer.parseInt(command.split(" ")[1]);
						String origin = command.split(" ")[2];
						String destination = command.split(" ")[3];
						freightTranSys.addAdjTownCost(origin, destination, cost);

					} else if (command.startsWith("Job")) {
						String origin = command.split(" ")[1];
						String destination = command.split(" ")[2];
						if (!freightTranSys.addJobList(origin, destination)) {
							// handle invalid Job input
							// System.out.println(command);
						}
					}
				}
				Instant s = Instant.now();
				freightTranSys.aStarSearch();
				Instant e = Instant.now();
				Duration d = Duration.between(s, e);
				System.out.println("Running time: " + d.toMillis());

				bR.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}
}