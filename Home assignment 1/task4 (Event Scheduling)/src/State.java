import java.util.*;

class State extends GlobalSimulation {

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int serversOccupied = 0, accumulated = 0, noMeasurements = 0, noServers, serviceTime;
	public double interarrival, timeMeasurements;

	// List holding results from measurements
	public ArrayList<String> noCustomers = new ArrayList<String>();

	Random slump = new Random(); // This is just a random number generator

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
		if (serversOccupied < noServers) {
			serversOccupied++;
			insertEvent(READY, time + serviceTime);
		}
		insertEvent(ARRIVAL, time + expDist(interarrival));
	}

	private void ready() {
		serversOccupied--;
	}

	private void measure() {
		noMeasurements++;
		noCustomers.add(Integer.toString(serversOccupied));
		insertEvent(MEASURE, time + timeMeasurements);
	}

	private double expDist(double mean) {
		return -(mean) * Math.log(slump.nextDouble());
	}
}