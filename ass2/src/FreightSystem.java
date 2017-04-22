import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class FreightSystem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
							freightTranSys = new FreightTransportSystem(cost, name);
						} else {
							freightTranSys.addLocations(cost, name);
						}

					} else if (command.startsWith("Cost")) {
						// Cost 240 Dubbo Bathurst # Travel cost is 240 from
						// Dubbo to Bathurst
						int cost = Integer.parseInt(command.split(" ")[1]);
						String origin = command.split(" ")[2];
						String destination = command.split(" ")[3];
						freightTranSys.addAdjTownCost(origin, destination, cost);
						// System.out.println(command);
					} else if (command.startsWith("Job")) {
						// Job Grafton Wagga
						String origin = command.split(" ")[1];
						String destination = command.split(" ")[2];
						if (!freightTranSys.addJobList(origin, destination)){
							System.out.println(command);
						}
						//
						// System.out.println(command);
					} 
				}
				freightTranSys.AStarSearch();
				bR.close();
			} catch (Exception e) {
				e.printStackTrace();

				System.out.println(e.getMessage());

			}

		}

	}
}