import java.io.*;

//It inherits Proc so that we can use time and the signal names without dot notation

public class MainSimulation extends Global {

	public static void simulate(int type, double interarrival) {
		// The signal list is started and actSignal is declaree. actSignal is
		// the latest signal that has been fetched from the
		// signal list in the main loop below.

		Signal actSignal;
		new SignalList();

		// Here process instances are created (two queues and one generator) and
		// their parameters are given values.
		QS Q1, Q2, Q3, Q4, Q5;

		Q1 = new QS();
		Q2 = new QS();
		Q3 = new QS();
		Q4 = new QS();
		Q5 = new QS();
		Q1.sendTo = null;
		Q2.sendTo = null;
		Q3.sendTo = null;
		Q4.sendTo = null;
		Q5.sendTo = null;

		Gen Generator = new Gen();
		Generator.lambda = (double) 1 / interarrival; // Uniform 0.12
		Generator.Q1 = Q1;
		Generator.Q2 = Q2;
		Generator.Q3 = Q3;
		Generator.Q4 = Q4;
		Generator.Q5 = Q5;

		// To start the simulation the first signals are put in the signal list

		String stringType = "";

		if (type == READYRANDOM) {
			stringType = "(Random)";
			SignalList.SendSignal(READYRANDOM, Generator, time);
			SignalList.SendSignal(MEASURE, Q1, time);
			SignalList.SendSignal(MEASURE, Q2, time);
			SignalList.SendSignal(MEASURE, Q3, time);
			SignalList.SendSignal(MEASURE, Q4, time);
			SignalList.SendSignal(MEASURE, Q5, time);
		} else if (type == READYROUND) {
			stringType = "(Round robin)";
			SignalList.SendSignal(READYROUND, Generator, time);
			SignalList.SendSignal(MEASURE, Q1, time);
			SignalList.SendSignal(MEASURE, Q2, time);
			SignalList.SendSignal(MEASURE, Q3, time);
			SignalList.SendSignal(MEASURE, Q4, time);
			SignalList.SendSignal(MEASURE, Q5, time);
		} else if (type == READYPRIO) {
			stringType = "(Prio)";
			SignalList.SendSignal(READYPRIO, Generator, time);
			SignalList.SendSignal(MEASURE, Q1, time);
			SignalList.SendSignal(MEASURE, Q2, time);
			SignalList.SendSignal(MEASURE, Q3, time);
			SignalList.SendSignal(MEASURE, Q4, time);
			SignalList.SendSignal(MEASURE, Q5, time);
			Generator.queueList.add(Q1);
			Generator.queueList.add(Q2);
			Generator.queueList.add(Q3);
			Generator.queueList.add(Q4);
			Generator.queueList.add(Q5);
		}

		// This is the main loop

		while (time < 100000) {
			actSignal = SignalList.FetchSignal();
			time = actSignal.arrivalTime;
			actSignal.destination.TreatSignal(actSignal);
		}

		// Finally the result of the simulation is printed below:

		double totalNoCustomers = (1.0 * Q1.accumulated / Q1.noMeasurements)
				+ (1.0 * Q2.accumulated / Q2.noMeasurements) + (1.0 * Q3.accumulated / Q3.noMeasurements)
				+ (1.0 * Q4.accumulated / Q4.noMeasurements) + (1.0 * Q5.accumulated / Q5.noMeasurements);

		System.out.println("---------- Interarrival time " + interarrival + " " + stringType + " ----------");
		System.out.println("Mean number of customers in queuing system: " + totalNoCustomers);
		System.out.println("Result using little’s theorem: " + (1 / interarrival) * (timeSpent / noOfReady));

	}

	public static void main(String[] args) throws IOException {
		simulate(READYRANDOM, 0.11); // change values.
	}
}