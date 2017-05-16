import java.util.*;

//It inherits Proc so that we can use time and the signal names without dot notation 

class Gen extends Proc {

	// The random number generator is started:
	Random slump = new Random();

	// There are two parameters:
	public Proc sendTo; // Where to send customers
	public double lambda; // Constant stating how often to generate a arrival.
	public int numberOfRejected;
	public int numberOfArrivals;
	public QS1 Q;

	public Gen(QS1 Q) {
		this.Q = Q;
	}

	// What to do when a signal arrives
	public void TreatSignal(Signal x) {
		switch (x.signalType) {
		case READY: {
			if (Q.numberInQueue <= 10) {
				SignalList.SendSignal(ARRIVAL, sendTo, time);
			} else {
				numberOfRejected++;
			}
			SignalList.SendSignal(READY, this, time + lambda);
			numberOfArrivals++;
		}
			break;
		}
	}
}