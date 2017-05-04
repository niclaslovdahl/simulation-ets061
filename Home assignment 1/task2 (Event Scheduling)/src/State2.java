import java.util.*;
import java.io.*;

class State2 extends GlobalSimulation {

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue = 0, accumulated = 0, noMeasurements = 0, nbrOfA = 0, nbrOfB = 0;

	Random slump = new Random(); // This is just a random number generator

	// The following method is called by the main program each time a new event
	// has been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x) {
		switch (x.eventType) {
		case ARRIVALA:
			arrival(x.prio);
			break;
		case READY:
			ready(x.prio);
			break;
		case MEASURE:
			measure();
			break;
		}
	}

	// The following methods defines what should be done when an event takes
	// place. This could
	// have been placed in the case in treatEvent, but often it is simpler to
	// write a method if
	// things are getting more complicated than this.

	private void arrival(int prio) {
		if (numberInQueue == 0) {
			if (prio == 0) {
				insertEvent(READY, time + 0.002, 0);
			} else {
				insertEvent(READY, time + 0.004, 1);
			}
		}

		if (prio == 0) {
			nbrOfA++;
		} else {
			nbrOfB++;
		}
		numberInQueue++;
		insertEvent(ARRIVALA, time + expDist((double) 1 / 150), 0);
	}

	private void ready(int prio) {
		if (prio == 0) {
			nbrOfA--;
			insertEvent(ARRIVALA, time + 1.0, 1);
		} else {
			nbrOfB--;
		}

		numberInQueue--;
		//System.out.println(noMeasurements);

		if (numberInQueue > 0) {
			if (nbrOfB > 0) {
				insertEvent(READY, time + 0.004, 1);
			} else {
				insertEvent(READY, time + 0.002, 0);
			}
		}
	}

	private void measure() {
		accumulated = accumulated + numberInQueue;
		noMeasurements++;
		insertEvent(MEASURE, time + 0.1, 0);
	}

	private double expDist(double mean) {
		return -(mean) * Math.log(slump.nextDouble());
	}

}