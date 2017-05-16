import java.util.*;

class StateLinkedList extends GlobalSimulation {

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue = 0, accumulated = 0, noMeasurements = 0, nbrOfA = 0, nbrOfB = 0;

	Random slump = new Random(); // This is just a random number generator
	public LinkedList<String> buffer = new LinkedList<String>();

	// The following method is called by the main program each time a new event
	// has been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x) {
		switch (x.eventType) {
		case ARRIVALA:
			arrivalA();
			break;
		case ARRIVALB:
			arrivalB();
			break;
		case READYA:
			ready();
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

	private void arrivalA() {
		if (buffer.size() == 0) {
			insertEvent(READYA, time + 0.002);
		}
		buffer.addLast("a");
		insertEvent(ARRIVALA, time + expDist((double) 1 / 150));
	}

	public void arrivalB() {
		if (buffer.size() == 0) {
			insertEvent(READYA, time + 0.004);
		}
		buffer.addFirst("b");
	}

	private void ready() {
		String tempa = buffer.poll();
		if (tempa.equals("a")) {
			insertEvent(ARRIVALB, time + 1.0);
		}

		if (buffer.size() > 0) {
			String temp = buffer.peek();
			if (temp.equals("a")) {
				insertEvent(READYA, time + 0.002);
			} else {
				insertEvent(READYA, time + 0.004);
			}
		}
	}

	private void measure() {
		accumulated = accumulated + buffer.size();
		noMeasurements++;
		insertEvent(MEASURE, time + 0.1);
	}

	private double expDist(double mean) {
		return -(mean) * Math.log(slump.nextDouble());
	}
}