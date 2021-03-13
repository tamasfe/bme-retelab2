package hu.bme.mit.yakindu.analysis.workhere;

import java.io.IOException;
import java.util.Scanner;

import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;



public class RunStatechart {
	
	public static void main(String[] args) throws IOException {
		ExampleStatemachine s = new ExampleStatemachine();
//		s.setTimer(new TimerService());
//		RuntimeService.getInstance().registerStatemachine(s, 200);
//		s.init();
//		s.enter();
//		s.runCycle();
//		print(s);
//		s.raiseStart();
//		s.runCycle();
//		System.in.read();
//		s.raiseWhite();
//		s.runCycle();
//		print(s);
//		System.exit(0);
		
		s.setTimer(new TimerService());
		RuntimeService.getInstance().registerStatemachine(s, 200);
		s.init();
		
		
		
		Scanner input = new Scanner(System.in);
		while (input.hasNext()) {
			String cmd = input.nextLine().toLowerCase().trim();
			if (cmd.equals("exit")) {
				System.exit(0);
			}
			String stateName = cmd;
			
			switch (stateName) {
			case "start":
				s.raiseStart();
				s.runCycle();
				break;
			case "black":
				s.raiseBlack();
				s.runCycle();
				break;
			case "white":
				s.raiseWhite();
				s.runCycle();
				break;
			}
			print(s);
		}
	}

	public static void print(IExampleStatemachine s) {
		System.out.println("W = " + s.getSCInterface().getWhiteTime());
		System.out.println("B = " + s.getSCInterface().getBlackTime());
	}
}
