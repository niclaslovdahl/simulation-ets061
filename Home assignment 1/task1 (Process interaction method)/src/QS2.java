import java.util.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS2 extends Proc {
	public int numberInQueue = 0, accumulated, noMeasurements;
	public Proc sendTo;
	Random slump = new Random();

	public void TreatSignal(Signal x) {
		switch (x.signalType) {

		case ARRIVAL: {
			numberInQueue++;
			if (numberInQueue == 1) {
				SignalList.SendSignal(READY, this, time + 2);
			}
		}
			break;

		case READY: {
			numberInQueue--;
			if (sendTo != null) {
				SignalList.SendSignal(ARRIVAL, sendTo, time);
			}
			if (numberInQueue > 0) {
				SignalList.SendSignal(READY, this, time + 2);
			}
		}
			break;

		case MEASURE: {
			noMeasurements++;
			accumulated = accumulated + numberInQueue;
			SignalList.SendSignal(MEASURE, this, time + expDist(5));
		}
			break;
		}
	}

	public double expDist(double mean) {
		return -(mean) * Math.log(slump.nextDouble());
	}
}