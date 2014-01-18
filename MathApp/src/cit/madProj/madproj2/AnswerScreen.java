package cit.madProj.madproj2;

import util.GetGameAnswer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.*;
import android.widget.*;

public class AnswerScreen extends Activity {

	//Variables
	int finalAnswer;
	boolean didUserWin;
	int firstNum;
	int secondNum;
	int userInput;
	int userRemainder;
	String arithChoice;
	MathFunctionChoice choice;
	
	
	
	
	//Make the components
	private TextView problemField;
	private TextView answerLabel;
	private TextView userEnterLabel;
	private TextView CorrectOrNoLabel;
	private Button   goBack;
	
	
	//When the activity is created this is run
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answer_screen);
		
		//Change background colour
		getWindow().getDecorView().setBackgroundColor(Color.LTGRAY);	
		
		//set up the components
		setUpComponents();
		
		//Get the data from the previous intent
		Intent prevIntent = getIntent();
		firstNum  		= prevIntent.getIntExtra("firstNum",   0);
		secondNum 		= prevIntent.getIntExtra("secondNum",  0);
		userInput 		= prevIntent.getIntExtra("userAnswer", 0);
		userRemainder 	= prevIntent.getIntExtra("remainder",  0);
		arithChoice		= prevIntent.getStringExtra("stringChoice");
		choice			= (MathFunctionChoice) prevIntent.getSerializableExtra("choice");
		
		//Get the answer
		finalAnswer = GetGameAnswer.getMathAnswer(firstNum, secondNum, choice);
		
		//Initialise the didUserWin variable
		didUserWin = checkIfUserIsCorrect();
	
		//Fill in the labels
		fillInLabels();	
	}

	
	
	
	
	
	
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.answer_screen, menu);
		return true;
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	/////////////////////////////// Private ////////////////////////////////// 
	
	
	
	
	
	private void setUpComponents() {
		problemField  	 = (TextView) findViewById(R.id.problemField);
		answerLabel  	 = (TextView) findViewById(R.id.answerLabel);
		userEnterLabel   = (TextView) findViewById(R.id.userEnterLabel);
		CorrectOrNoLabel = (TextView) findViewById(R.id.CorrectOrNo);
		goBack 			 = (Button) findViewById(R.id.answerGoBack);
		
		goBack.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent prevIntent = getIntent();
				
				//Add data to the intent
				prevIntent.putExtra("didUserWin", didUserWin);
				
				//Set the intent result
				setResult(RESULT_OK, prevIntent);
				finish();	
			}
		});
		
	}
	
	
	
	//Fill in the labels
	private void fillInLabels() {
		problemField.	 setText("The problem was " + firstNum + arithChoice + secondNum);
		answerLabel.	 setText("The answer is " + getResultToPrint());
		userEnterLabel.	 setText("You entered" + showUserAnswer());
		CorrectOrNoLabel.setText(printwinOrLOseMessage());
	}
	
	
	
	//Return the answer ready to print
	private String getResultToPrint() {
		
		finalAnswer = GetGameAnswer.getMathAnswer(firstNum, secondNum, choice);
		
		if (choice == MathFunctionChoice.DIVISION) {
			return " " + finalAnswer +  " Remainder " + GetGameAnswer.getRemainderAfterDivison(firstNum, secondNum);
		}
				
		return " " + finalAnswer;
	}
	
	
	//Check if the user is dividing, if so print there remainder as well
	private String showUserAnswer() {
		if (choice == MathFunctionChoice.DIVISION) {
			return " " + userInput + " Remainder " + userRemainder;
		}
		else {
			return " " + userInput;
		}
	}
	
	
	
	//Print the victory or lose message
	private String printwinOrLOseMessage() {
		if(didUserWin) {
			return "Correct!";
		}
		
		return "Sorry the answer was wrong, go back and try again";
	}
	
	
	//Check if the user is correct
	private boolean checkIfUserIsCorrect() {
		if (choice == MathFunctionChoice.DIVISION) {
			if (userInput == finalAnswer && userRemainder == GetGameAnswer.getRemainderAfterDivison(firstNum, secondNum)) {
				return true;
			}
		}
		else if (userInput == finalAnswer) {
			return true;
		}
		
		
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
