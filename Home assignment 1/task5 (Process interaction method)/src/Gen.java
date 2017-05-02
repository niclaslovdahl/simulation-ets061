import java.util.*;
import java.io.*;

//It inherits Proc so that we can use time and the signal names without dot notation 

class Gen extends Proc {

	public QS Q1, Q2, Q3, Q4, Q5;

	// The random number generator is started:
	Random slump = new Random();

	// There are two parameters:
	public Proc sendTo; // Where to send customers
	public double lambda; // How many to generate per second

	// What to do when a signal arrives
	public void TreatSignal(Signal x) {
		switch (x.signalType) {
		case READYRANDOM:
			readyRandom();
			break;
		case READYROUND:
			readyRound();
			break;
		case READYPRIO:
			readyPrio();
			break;
		}
	}

	// Sends incoming arrivals to dispatcher to random server.
	private void readyRandom() {
		switch (slump.nextInt(5) + 1) {
		case 1:
			sendTo = Q1;
			break;
		case 2:
			sendTo = Q2;
			break;
		case 3:
			sendTo = Q3;
			break;
		case 4:
			sendTo = Q4;
			break;
		case 5:
			sendTo = Q5;
			break;
		}

		SignalList.SendSignal(ARRIVAL, sendTo, time);
		SignalList.SendSignal(READYRANDOM, this, time + (2.0 / lambda) * slump.nextDouble());

	}

	private void readyRound() {
		SignalList.SendSignal(ARRIVAL, sendTo, time);
		SignalList.SendSignal(READYROUND, this, time + (2.0 / lambda) * slump.nextDouble());
	}

	private void readyPrio() {
		SignalList.SendSignal(ARRIVAL, sendTo, time);
		SignalList.SendSignal(READYPRIO, this, time + (2.0 / lambda) * slump.nextDouble());
	}
}