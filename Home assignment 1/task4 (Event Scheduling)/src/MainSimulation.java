import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class MainSimulation extends GlobalSimulation {

	public static void simulate(int noServers, int serviceTime, double interarrival, double timeMeasurements,
			String fileName) throws FileNotFoundException, UnsupportedEncodingException {

		Event actEvent;
		State actState = new State(); // The state that shoud be used
		// Some events must be put in the event list at the beginning
		insertEvent(ARRIVAL, 0);
		insertEvent(MEASURE, 5);

		// Variables declared in actstate
		actState.noServers = noServers;
		actState.serviceTime = serviceTime;
		actState.interarrival = interarrival;
		actState.timeMeasurements = timeMeasurements;

		// The main simulation loop
		while (actState.noMeasurements < 1000) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}

		// Creating file in res directory
		File file = new File("res/" + fileName);
		file.getParentFile().mkdirs();
		PrintWriter pw = new PrintWriter(file, "UTF-8");

		for (String s : actState.noCustomers) {
			System.out.println(s);
			pw.println(s);
		}

		pw.close();
	}

	public static void main(String[] args) throws IOException {
		simulate(1000, 100, (double) 1 / 8, 1, "4.1.txt");
	}
}