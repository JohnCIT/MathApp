package controller;

import GetNumbersForGame.GetNumbers;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import cit.madProj.madproj2.AnswerScreen;
import cit.madProj.madproj2.MainScreen;
import cit.madProj.madproj2.MathFunctionChoice;

/**
 * 
 * The controller for the main interface
 *
 */
public class MainInterfaceCont {
	
	// The point Result score
	final int ADDITION_SUBTRACTION = 10;
	final int MULTIPLY_DIVISION	   = 20;
	
	// The numbers being used in the game
	int firstNum;
	int secondNum;
	public boolean userWin;
	int GET_EXTRA_DATA = 2020;
	int points = 0;
		
	//The view
	MainScreen view;
	
	public MainInterfaceCont() {}
	
	
	/**
	 * Constructor
	 */
	public MainInterfaceCont(MainScreen view) {
		this.view = view;
		
		// Attach the action listener
		view.spinnerAction  (new DivisionSelectionFromSpinner());
		view.startGameAction(new StartGame());
		view.finalInputButt (new GoToFinalAnswerGcreen());
	}
	
	
	
	/**
	 * Set the user interface back to defaults ready for the next round
	 */
	public void setUpInterface() {
		// Set the default state
		view.setAnswerEnabledOrDisabled(false);
		view.setSpinner(true);
		view.setStartGameButton(true);
		view.resetInputLabel();
				
		if (userWin) {
			view.setStartGameText("Continue Game");
			view.setProblemLabelMessage("Well done!");
			addPoints();
			view.setPointsView("Points so far = " + points);
						
			// Check if user beat there high Score. If so update it
			if (points > view.highScore) {
				view.highScore = points;
			}
			
		}
		else {
			view.setStartGameText("Restart Game");
			view.setProblemLabelMessage("Whoops, Try again?");
			view.setPointsView("Points this round = " + points);
			
			// Check if user beat there high Score. If so update it
			if (points > view.highScore) {
				view.highScore = points;
			}
			
			// Reset the points
			points = 0;	
		}
		
	}
	
	
	/**
	 * Increment the points
	 */
	public void addPoints() {
		if (view.getArithmiticChoice() == MathFunctionChoice.ADDITION || view.getArithmiticChoice() == MathFunctionChoice.SUBTRACTION) {
			points += ADDITION_SUBTRACTION;
		}
		else {
			points += MULTIPLY_DIVISION;
		}
	}
	
	
	
	/**
	 * Reset the points
	 * Display the label with the new result
	 */
	public void resetPointsAndRedisplayLabel() {
		points = 0;
		view.setPointsView("Points Reset = " + points);
	}
	
	
	
	
	
	/////////////////////// Action event classes to control the main activity//////////////////////////////
	
	/**
	 * If division is selected enable the remainder field
	 *
	 */
	class DivisionSelectionFromSpinner implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			boolean state = (view.getArithmiticChoice() == MathFunctionChoice.DIVISION);
			view.setDivisionComponents(state);
		}
		public void onNothingSelected(AdapterView<?> arg0) {}		
	}
	
	
	
	/**
	 * When the start game is pressed start the game
	 */
	class StartGame implements OnClickListener {
		public void onClick(View v) {
			
			//Each choice should have a sensible number range
			switch (view.getArithmiticChoice()) {
			case ADDITION:
				firstNum  = GetNumbers.getRandomNumberBetweenTwoNums(1, 100);
				secondNum = GetNumbers.getRandomNumberBetweenTwoNums(1, 100);
				break;
			
			case SUBTRACTION:
				firstNum  = GetNumbers.getRandomNumberBetweenTwoNums(10, 100);
				secondNum = GetNumbers.getRandomNumberBetweenTwoNums(1, firstNum);
				break;
			
			case MULTIPLY:
				firstNum  = GetNumbers.getRandomNumberBetweenTwoNums(1, 12);
				secondNum = GetNumbers.getRandomNumberBetweenTwoNums(1, 12);
				break;
			
			case DIVISION:
				firstNum  = GetNumbers.getRandomNumberBetweenTwoNums(11, 100);
				secondNum = GetNumbers.getRandomNumberBetweenTwoNums(1, 9);
				break;
			
			default:
				break;			
			}
			
			view.setProblemLabel(firstNum, secondNum);
			
			// Disable the start button
			view.setStartGameButton(false);
			
			// Enable the answer field
			view.setAnswerEnabledOrDisabled(true);
			
			// Disable the spinner choice
			view.setSpinner(false);
		}		
	}
	
	
	
	
	
	
	/**
	 *  When a answer is entered go to the new intent with the details
	 */
	class GoToFinalAnswerGcreen implements OnClickListener {

		public void onClick(View v) {
			Intent answer = new Intent(view, AnswerScreen.class);
			
			answer.putExtra("firstNum",   	firstNum);
			answer.putExtra("secondNum",  	secondNum);
			answer.putExtra("userAnswer", 	view.getUserAnswer());
			answer.putExtra("remainder",	view.getRemainderInput());
			answer.putExtra("stringChoice", view.getStringArithChoice());
			answer.putExtra("choice", 	  	view.getArithmiticChoice());
			
			view.startActivityForResult(answer, GET_EXTRA_DATA);			
		}		
	}
	
	
	
	
	
}
