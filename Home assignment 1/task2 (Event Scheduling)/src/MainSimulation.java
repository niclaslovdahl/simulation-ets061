import java.util.*;
import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static final int PRIOA = 1, PRIOB = 2, EXP = 3;

	public static void simulate(int type) {
		Event actEvent;
		State actState = new State(); // The state that shoud be used
		// Some events must be put in the event list at the beginning
		String typeString = "";

		if (type == PRIOA) {
			insertEvent(ARRIVALA, 0);
			insertEvent(MEASURE, 5);
			typeString = "(Prio A)";
		} else if (type == PRIOB) {

		} else if (type == EXP) {
			insertEvent(ARRIVALAEXP, 0);
			insertEvent(MEASURE, 5);
			typeString = "(Prio A and Exp delay)";
		}

		// The main simulation loop
		while (actState.noMeasurements < 1000) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}

		// Printing the result of the simulation, in this case a mean value
		System.out.println("---------- Mean number of jobs " + typeString + " ----------");
		System.out.println("Mean number of jobs in buffer: " + 1.0 * actState.accumulated / actState.noMeasurements);
	}

	public static void main(String[] args) throws IOException {
		simulate(PRIOA);
		simulate(EXP);
	}
}