import java.util.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc {
	public int numberInQueue = 0, accumulated, noMeasurements;
	public Proc sendTo;
	Random slump = new Random();
	public LinkedList<Double> arrivals = new LinkedList<Double>();

	public void TreatSignal(Signal x) {
		switch (x.signalType) {
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

	private void arrival() {
		numberInQueue++;
		arrivals.addLast(time);
		if (numberInQueue == 1) {
			SignalList.SendSignal(READY, this, time + expDist(0.5));
		}
	}

	private void ready() {
		numberInQueue--;
		timeSpent += time - arrivals.poll().doubleValue();
		noOfReady++;
		if (sendTo != null) {
			SignalList.SendSignal(ARRIVAL, sendTo, time);
		}
		if (numberInQueue > 0) {
			SignalList.SendSignal(READY, this, time + expDist(0.5));
		}
	}

	private void measure() {
		noMeasurements++;
		accumulated = accumulated + numberInQueue;
		SignalList.SendSignal(MEASURE, this, time + 2 * slump.nextDouble());
	}

	private double expDist(double mean) {
		return -(mean) * Math.log(slump.nextDouble());
	}
}