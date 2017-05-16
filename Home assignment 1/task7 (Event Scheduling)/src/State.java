import java.util.*;

class State extends GlobalSimulation {

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue = 0, accumulated = 0, noMeasurements = 0;
	public boolean comp1 = true, comp2 = true, comp3 = true, comp4 = true, comp5 = true;

	Random slump = new Random(); // This is just a random number generator

	public State() {
		insertEvent(COMP1, slump.nextDouble() * 4 + 1);
		insertEvent(COMP2, slump.nextDouble() * 4 + 1);
		insertEvent(COMP3, slump.nextDouble() * 4 + 1);
		insertEvent(COMP4, slump.nextDouble() * 4 + 1);
		insertEvent(COMP5, slump.nextDouble() * 4 + 1);
	}

	// The following method is called by the main program each time a new event
	// has been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x) {
		switch (x.eventType) {
		case COMP1:
			comp1();
			break;
		case COMP2:
			comp2();
			break;
		case COMP3:
			comp3();
			break;
		case COMP4:
			comp4();
			break;
		case COMP5:
			comp5();
			break;
		}
	}

	// The following methods defines what should be done when an event takes
	// place. This could
	// have been placed in the case in treatEvent, but often it is simpler to
	// write a method if
	// things are getting more complicated than this.

	private void comp1() {
		comp1 = false;
		insertEvent(COMP2, time);
		insertEvent(COMP5, time);
	}

	private void comp2() {
		comp2 = false;
	}

	private void comp3() {
		comp3 = false;
		insertEvent(COMP4, time);
	}

	private void comp4() {
		comp4 = false;
	}

	private void comp5() {
		comp5 = false;
	}

	public boolean breakdown() {
		if (!comp1 && !comp2 && !comp3 && !comp4 && !comp5) {
			return true;
		}
		return false;
	}
}