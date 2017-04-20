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
					} else if (command.startsWith("Print")) {
						// System.out.println(command);
//						Town origin = freightTranSys.getLocations().get(0);
//						System.out.println("From => "+origin.getName());
//						LinkedList<Town> travel = freightTranSys.getMapGraph().bfs(origin);
//						travel.forEach(t -> System.out.println(t.getName()));
//						int total = 0;
//						Town origin1 = freightTranSys.getLocations().get(1);
//						LinkedList<RoutePath> travel1 = freightTranSys.getMapGraph().findShortestPath(origin, origin1);
//						int sum = travel1.stream().mapToInt(t -> t.getEnd().getUnloadCost()).sum();
//						sum += travel1.stream().mapToInt(t -> t.getCost()).sum();
//						System.out.println("Find shortest From => "+origin.getName() + " -> " + origin1.getName() + " total cost: $" + sum);
//						travel1.forEach(t -> System.out.println(t.getStart().getName() + " " +
//						t.getEnd().getName()	+ "\tunload:$"+ t.getEnd().getUnloadCost() + 
//						" trip cost: $" + t.getCost()));
					}
				}

				bR.close();
//				freightTranSys.getLocations().forEach(l -> {
//					System.out.println(l.getName() + " upload cost $" + l.getUploadCost());
//					l.printAdjacencyList();
//				});
//				
//				freightTranSys.getJobList().forEach(j -> {
//					System.out.println("Job " + j.getOrigin().getName() +
//							" --> " + j.getDestination().getName());
//					
//				});
//				
			} catch (Exception e) {
				e.printStackTrace();

				System.out.println(e.getMessage());

			}

		}

	}
}