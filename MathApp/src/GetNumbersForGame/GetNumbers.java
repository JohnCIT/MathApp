package GetNumbersForGame;

public class GetNumbers {
	
	/**
	 * Get a number between two numbers
	 * @return Random number
	 */
	public static int getRandomNumberBetweenTwoNums(int min, int max) {
		return (int) (Math.random() * (max - min)) + min;		
	}
	
	
	
	

}
