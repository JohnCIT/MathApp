package util;

import cit.madProj.madproj2.MathFunctionChoice;

public class GetGameAnswer {
	
	/**
	 * Get the answer for the math problems
	 */
	public static int getMathAnswer(int firstNum, int secondNum, MathFunctionChoice choice) {
		switch ( choice ) {
		case ADDITION:
			return firstNum + secondNum;
		case SUBTRACTION:
			return firstNum - secondNum;
		case MULTIPLY:
			return firstNum * secondNum;
		case DIVISION:
			return firstNum / secondNum;
			
		default:
			return firstNum + secondNum;
		}		
	}
	
	
	/**
	 * Get the remainder after division
	 */
	public static int getRemainderAfterDivison(int firstNum, int secondNum) {
		return firstNum % secondNum;
	}

}
