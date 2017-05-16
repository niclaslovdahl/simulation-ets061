import java.util.*;

class State extends GlobalSimulation {

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue = 0, accumulated = 0, noMeasurements = 0;

	Random slump = new Random(); // This is just a random number generator
	public boolean close = false;
	public LinkedList<Double> arrivals = new LinkedList<Double>();
	public double timeSpent = 0;
	public double noOfReady = 0;

	// The following method is called by the main program each time a new event
	// has been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x) {
		switch (x.eventType) {
		case ARRIVAL:
			arrival();
			break;
		case READY:
			ready();
			break;
		case CLOSE:
			close();
			break;
		}
	}

	// The following methods defines what should be done when an event takes
	// place. This could
	// have been placed in the case in treatEvent, but often it is simpler to
	// write a method if
	// things are getting more complicated than this.

	private void arrival() {
		if (numberInQueue == 0)
			insertEvent(READY, time + (10 * slump.nextDouble() + 10));
		numberInQueue++;
		arrivals.addLast(new Double(time));
		if (time < 480) {
			insertEvent(ARRIVAL, time + expDist(15));
		} else {
			insertEvent(CLOSE, time);
		}

	}

	private void ready() {
		numberInQueue--;
		timeSpent += time - arrivals.poll().doubleValue();
		noOfReady++;
		if (numberInQueue > 0)
			insertEvent(READY, time + (10 * slump.nextDouble() + 10));
	}

	private void close() {
		if (numberInQueue == 0) {
			close = true;
		} else {
			insertEvent(CLOSE, time + 1); // Try to close again in 1 minute.
		}
	}

	private double expDist(double mean) {
		return -(mean) * Math.log(slump.nextDouble());
	}
}