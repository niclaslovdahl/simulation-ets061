import java.util.*;

class State extends GlobalSimulation {

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue1 = 0, numberOfRejectedQueue1 = 0, numberOfArrivalsQueue1 = 0, numberInQueue2 = 0,
			accumulated = 0, noMeasurements = 0;

	Random slump = new Random(); // This is just a random number generator

	public double interarrival = 0;
	public double timeSpent = 0;
	public double noOfReady = 0;
	public LinkedList<Double> arrivals = new LinkedList<Double>();

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
		case ARRIVAL2:
			arrival2();
			break;
		case READY2:
			ready2();
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

	private void arrival() {
		numberOfArrivalsQueue1++;
		arrivals.addLast(new Double(time));
		if (numberInQueue1 == 0) {
			insertEvent(READY, time + expDist(1));
		}
		insertEvent(ARRIVAL, time + expDist(interarrival));
		numberInQueue1++;
	}

	private void ready() {
		numberInQueue1--;
		insertEvent(ARRIVAL2, time);
		if (numberInQueue1 > 0) {
			insertEvent(READY, time + expDist(1));
		}

	}

	private void arrival2() {
		if (numberInQueue2 == 0) {
			insertEvent(READY2, time + expDist(1));
		}
		numberInQueue2++;
	}

	private void ready2() {
		numberInQueue2--;
		timeSpent += time - arrivals.poll().doubleValue();
		noOfReady++;
		if (numberInQueue2 > 0) {
			double temp = expDist(1);
			timeSpent += temp;
			insertEvent(READY2, time + temp);
		}

	}

	private void measure() {
		accumulated = accumulated + numberInQueue1 + numberInQueue2;
		noMeasurements++;
		insertEvent(MEASURE, time + expDist(5));
	}

	private double expDist(double mean) {
		return -(mean) * Math.log(slump.nextDouble());
	}
}