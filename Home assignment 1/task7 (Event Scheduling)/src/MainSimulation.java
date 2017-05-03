import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {
		Event actEvent;
		State actState; // The state that shoud be used
		double totalTime = 0;

		for (int i = 0; i < 1000; i++) {
			actState = new State();
			// The main simulation loop
			while (!actState.breakdown()) {
				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
			}
			totalTime += time;
		}

		// Printing the result of the simulation, in this case a mean value
		System.out.println("Mean time until the system breaks down: " + totalTime / 1000);
	}
}