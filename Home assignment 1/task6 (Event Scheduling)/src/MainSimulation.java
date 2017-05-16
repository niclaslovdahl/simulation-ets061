import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {
		Event actEvent;
		State actState; // The state that shoud be used
		// Some events must be put in the event list at the beginning
		double timeSpent = 0;
		double noOfReady = 0;
		double overtime = 0;

		for (int i = 0; i < 1000; i++) {
			actState = new State();
			insertEvent(ARRIVAL, 0);
			// The main simulation loop
			while (!actState.close) {
				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
			}
			timeSpent += actState.timeSpent;
			noOfReady += actState.noOfReady;
			overtime += time - 480;
		}

		// Printing the result of the simulation, in this case a mean value
		int hours = (int) (17 + ((overtime / 1000) / 60));
		int minutes = ((int) (overtime / 1000) % 60) + 1; // Rounding up.

		System.out.println("Average time when his work will have finished: " + hours + ":" + minutes);
		System.out.println("Average time from the arrival of a prescription until it has been filled in: "
				+ timeSpent / noOfReady + " minutes");
	}
}