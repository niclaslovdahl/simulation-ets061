import java.util.*;

class State extends GlobalSimulation {

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue = 0, accumulated = 0, noMeasurements = 0, noOfA = 0, noOfB = 0;
	public boolean prio;

	Random slump = new Random(); // This is just a random number generator

	// The following method is called by the main program each time a new event
	// has been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x) {
		switch (x.eventType) {
		case ARRIVALA:
			arrivalA();
			break;
		case READYA:
			readyA();
			break;
		case ARRIVALB:
			arrivalB();
			break;
		case READYB:
			readyB();
			break;
		case MEASURE:
			measure();
			break;
		case ARRIVALAEXP:
			arrivalAExp();
			break;
		case READYAEXP:
			readyAExp();
			break;
		case READYBEXP:
			readyBExp();
			break;
		}
	}

	// The following methods defines what should be done when an event takes
	// place. This could
	// have been placed in the case in treatEvent, but often it is simpler to
	// write a method if
	// things are getting more complicated than this.

	private void arrivalA() {
		if (noOfA + noOfB == 0) {
			insertEvent(READYA, time + 0.002);
		}
		noOfA++;
		insertEvent(ARRIVALA, time + expDist((double) 1 / 150));
	}

	private void readyA() {
		noOfA--;
		insertEvent(ARRIVALB, time + 1.0);
		checkQueue();
	}

	private void arrivalB() {
		if (noOfA + noOfB == 0) {
			insertEvent(READYB, time + 0.004);
		}
		noOfB++;
	}

	private void readyB() {
		noOfB--;
		checkQueue();
	}

	private void measure() {
		accumulated = accumulated + noOfA + noOfB;
		noMeasurements++;
		insertEvent(MEASURE, time + 0.1);
	}

	private void checkQueue() {
		if (prio) {
			if (noOfB > 0) {
				insertEvent(READYB, time + 0.004);
			} else if (noOfA > 0) {
				insertEvent(READYA, time + 0.002);
			}
		} else {
			if (noOfA > 0) {
				insertEvent(READYA, time + 0.002);
			} else if (noOfB > 0) {
				insertEvent(READYB, time + 0.004);
			}
		}
	}

	private void arrivalAExp() {
		if (noOfA + noOfB == 0) {
			insertEvent(READYAEXP, time + 0.002);
		}
		noOfA++;
		insertEvent(ARRIVALAEXP, time + expDist((double) 1 / 150));
	}

	private void readyAExp() {
		noOfA--;
		insertEvent(ARRIVALB, time + expDist(1.0));
		checkQueueExp();
	}

	private void readyBExp() {
		noOfB--;
		checkQueueExp();
	}

	private void checkQueueExp() {
		if (prio) {
			if (noOfB > 0) {
				insertEvent(READYBEXP, time + 0.004);
			} else if (noOfA > 0) {
				insertEvent(READYAEXP, time + 0.002);
			}
		} else {
			if (noOfA > 0) {
				insertEvent(READYAEXP, time + 0.002);
			} else if (noOfB > 0) {
				insertEvent(READYBEXP, time + 0.004);
			}
		}
	}

	private double expDist(double mean) {
		return -(mean) * Math.log(slump.nextDouble());
	}

}