import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static final int PRIOB = 1, EXP = 2, PRIOA = 3;

	public static void simulate(int type, String fileName) throws FileNotFoundException, UnsupportedEncodingException {
		Event actEvent;
		// The state that shoud be used
		// Some events must be put in the event list at the beginning
		String typeString = "";

		State actState = new State();

		if (type == PRIOB) {
			actState.prio = true;
			insertEvent(ARRIVALA, 0);
			insertEvent(MEASURE, 5);
			typeString = "(Prio B)";
		} else if (type == EXP) {
			actState.prio = true;
			insertEvent(ARRIVALAEXP, 0);
			insertEvent(MEASURE, 5);
			typeString = "(Prio A and Exp delay)";
		} else if (type == PRIOA) {
			actState.prio = false;
			insertEvent(ARRIVALA, 0);
			insertEvent(MEASURE, 0);
			typeString = "(Prio A)";
		}

		// The main simulation loop
		while (actState.noMeasurements < 1000) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}

		File file = new File("res/" + fileName);
		file.getParentFile().mkdirs();
		PrintWriter pw = new PrintWriter(file, "UTF-8");

		for (String s : actState.noCustomers) {
			pw.println(s);
		}

		pw.close();

		// Printing the result of the simulation, in this case a mean value
		System.out.println("---------- Mean number of jobs " + typeString + " ----------");
		System.out.println("Mean number of jobs in buffer: " + 1.0 * actState.accumulated / actState.noMeasurements);
	}

	public static void main(String[] args) throws IOException {
		simulate(PRIOB, "priob.txt");
		simulate(EXP, "priobexp.txt");
		simulate(PRIOA, "prioa.txt");
	}
}