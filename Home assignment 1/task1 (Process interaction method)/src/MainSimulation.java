import java.io.*;

//It inherits Proc so that we can use time and the signal names without dot notation

public class MainSimulation extends Global {

	public static void simulate(int interarrival) {
		// The signal list is started and actSignal is declaree. actSignal is
		// the latest signal that has been fetched from the
		// signal list in the main loop below.

		Signal actSignal;
		new SignalList();

		// Here process instances are created (two queues and one generator) and
		// their parameters are given values.

		QS1 Q1 = new QS1();
		QS2 Q2 = new QS2();

		Q1.sendTo = Q2; // Served customers in Q1 shall be sent to Q2
		Q2.sendTo = null; // End of system

		Gen gen = new Gen(Q1);
		gen.lambda = interarrival; // Generator shall generate one arrival every
		// lambda/time unit
		gen.sendTo = Q1; // The generated customers shall be sent to Q1

		// To start the simulation the first signals are put in the signal list

		SignalList.SendSignal(READY, gen, time);
		SignalList.SendSignal(MEASURE, Q1, time);
		SignalList.SendSignal(MEASURE, Q2, time);

		// This is the main loop

		while (Q2.noMeasurements < 1000) {
			actSignal = SignalList.FetchSignal();
			time = actSignal.arrivalTime;
			actSignal.destination.TreatSignal(actSignal);
		}

		// Finally the result of the simulation is printed below:
		System.out.println("---------- Interarrival time " + interarrival + " ----------");
		System.out.println("Mean number of customers in queuing system 2: " + 1.0 * Q2.accumulated / Q2.noMeasurements);
		System.out.println("Number of customers arrived in queuing system 1: " + gen.numberOfArrivals);
		System.out.println("Number of rejected customers in queuing system 1: " + gen.numberOfRejected);
		System.out.println(
				"Propability of rejection in queuing system 1: " + 1.0 * gen.numberOfRejected / gen.numberOfArrivals);
	}

	public static void main(String[] args) throws IOException {
		simulate(1);
		simulate(2);
		simulate(5);
	}
}