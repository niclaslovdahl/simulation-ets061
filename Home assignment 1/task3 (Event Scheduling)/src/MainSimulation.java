import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void simulate(double interarrival) {
		Event actEvent;
		State actState = new State(); // The state that shoud be used
		actState.interarrival = interarrival; // Interarrival set to State
												// class.
		// Some events must be put in the event list at the beginning
		insertEvent(ARRIVAL, 0);
		insertEvent(MEASURE, 5);

		// The main simulation loop
		while (actState.noMeasurements < 1000) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}

		// Printing the result of the simulation, in this case a mean value
		System.out.println("---------- Interarrival time " + interarrival + " ----------");
		System.out.println(
				"Mean number of customers in queuing system: " + 1.0 * actState.accumulated / actState.noMeasurements);
		System.out.println(
				"Mean time a customer spends in the queuing network: " + (actState.timeSpent / actState.noOfReady));

	}

	public static void main(String[] args) throws IOException {
		simulate(2);
		simulate(1.5);
		simulate(1.1);
	}
}