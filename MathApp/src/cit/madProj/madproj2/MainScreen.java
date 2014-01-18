package cit.madProj.madproj2;

import java.util.List;

import controller.MainInterfaceCont;
import android.net.Uri;
import android.os.Bundle;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.text.InputFilter.LengthFilter;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AdapterView.*;


public class MainScreen extends Activity {

	// Create the components
	private TextView answerInput;
	private TextView remainder;
	private TextView points;
	private TextView problemDisplay;
	private Spinner arithChoice;
	private Button 	startGameButton;
	private Button	answerInputButt;
	private Button	rules;
			
	// Code
	int GET_EXTRA_DATA = 2020;
	
	// Controller instance
	MainInterfaceCont cont;
	
	// Menu group ID
	private int groupId = 1;
	
	// Menu item IDs
	final int onlineID 		= Menu.FIRST;
	final int launchActID 	= Menu.FIRST + 1;
	final int resetScore	= Menu.FIRST + 2;
	final int viewHighScore = Menu.FIRST + 3;
	
	// High score
	public int highScore = 0;
	
	
	
	/**
	 * Create the interface and set up all the components
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		
		// Takes any data that has been saved to the phone and assigns it to the variable
		SharedPreferences savedVars = getSharedPreferences("PrefName", Context.MODE_PRIVATE);
		highScore = savedVars.getInt("highScore", 0);
				
		// Change background colour
		getWindow().getDecorView().setBackgroundColor(Color.LTGRAY);	
		
		// Set up components
		setUpComponentsDefaultState();
		
		// Set up intents
		setUpIntents();
		
		// Set up the controller so the activity has functionality
		cont = new MainInterfaceCont(this);	
		
		// Save the High Score
		saveHighScore();
		
		// Make a notification
		createNotification();
	}

	
	
	
	
	/**
	 * Create the menu
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Create the menu components
		menu.add(groupId, onlineID, onlineID, 			"Online Math Material");
		menu.add(groupId, launchActID, launchActID, 	"Go to the Math Museum");
		menu.add(groupId, resetScore, resetScore, 		"Reset Score");
		menu.add(groupId, viewHighScore, viewHighScore, "View High Score");
				
		return super.onCreateOptionsMenu(menu);
	}
	
	
	/**
	 * Get the result back from the intent
	 */
	protected void onActivityResult(int requestCode,
            int resultCode, Intent data) {
    	
        if (requestCode == GET_EXTRA_DATA && resultCode == RESULT_OK) {  
            cont.userWin = data.getExtras().getBoolean("didUserWin");
            cont.setUpInterface();
            saveHighScore();
        }
                
        super.onActivityResult(requestCode, resultCode, data);
    }
	
	
	
	
	
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		
			case onlineID:
				// Go to a web page
				Uri webpage = Uri.parse("http://www.kidsmathgamesonline.com/facts.html");
				Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
				startActivity(webIntent);
				return true;
	
			case launchActID:
				// Map to the math location 
				Uri location = Uri.parse("geo:0,0?q=National+Museum+of+Mathematics+11+East+26th+Street+New+York,+NY+10010+United+States");
				Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
				
				// Check if the map can be launched
				if (doesIntentHaveAppToRecieveIt(mapIntent)) {
					startActivity(mapIntent);
				}
				else {
					Toast.makeText(getApplicationContext(), "Cannot launch map as you have no Map App", Toast.LENGTH_LONG).show();
				}
							
				return true;
	
			case resetScore:
				// Reset the score
				cont.resetPointsAndRedisplayLabel();
				return true;
	
			case viewHighScore:
				// Display the high score
				Toast.makeText(getApplicationContext(), "High score: " + highScore, Toast.LENGTH_LONG).show();
				return true;
	
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	
	
	

	
	
	
	////////// Component manipulation /////////////////////
	//////////////////////////////////////////////////////
	
	/**
	 * Get the remainder
	 */
	public int getRemainderInput() {
		if(remainder.getText().length() != 0) {
			return Integer.parseInt( remainder.getText().toString() );
		}
		
		return 0;		
	}
	
	
	/**
	 * Get the user input
	 */
	public int getUserAnswer() {
		if(answerInput.getText().length() != 0) {
			return Integer.parseInt( answerInput.getText().toString());
		}
		
		return 0;
	}
	
	
	/**
	 * Set what the points view says
	 */
	public void setPointsView(String msg) {
		points.setText(msg);
	}
	
	
	/**
	 * Set the division section enabled or disable
	 */
	public void setDivisionComponents(boolean set) {
		remainder.setEnabled(set);
	}
	
	
	/**
	 * Set the answerInputButt button to be enabled or disabled
	 */
	public void setAnswerEnabledOrDisabled(boolean set) {
		answerInputButt.setEnabled(set);
		answerInput.setEnabled(set);
	}
	
	
	/**
	 * Get the selected item from the spinner
	 * @return What math function the user selected
	 */
	public MathFunctionChoice  getArithmiticChoice() {
		
		// Switch deciding what to return. Avoids magic numbers
		switch ( (int) arithChoice.getSelectedItemId() ) {
		case 0:
			return MathFunctionChoice.ADDITION;
		
		case 1:
			return MathFunctionChoice.SUBTRACTION;
		
		case 2:
			return MathFunctionChoice.MULTIPLY;
		
		case 3:
			return MathFunctionChoice.DIVISION;
			
		default:
			return MathFunctionChoice.ADDITION;
		}		
	}
	
	
	
	/**
	 * Get string version of the spinner choice
	 */
	public String getStringArithChoice() {
		
		switch ( (int) arithChoice.getSelectedItemId() ) {
		case 0:
			return " + ";
		
		case 1:
			return " - ";
		
		case 2:
			return " * ";
		
		case 3:
			return " / ";
			
		default:
			return " + ";
		}		
	}
	
	
	
	/**
	 * Set the problem label text
	 */
	public void setProblemLabel(int firstNum, int secondNum) {
		problemDisplay.setText(firstNum + getStringArithChoice() + secondNum);
	}
	
	
	/**
	 * Set the start game button
	 */
	public void setStartGameButton(boolean set) {
		startGameButton.setEnabled(set);
	}
	
	
	/**
	 * Set whether the spinner is enabled or disabled
	 */
	public void setSpinner(boolean set) {
		arithChoice.setEnabled(set);
	}
	
	
	/**
	 * Set the start button Text
	 */
	public void setStartGameText(String msg) {
		startGameButton.setText(msg);
	}
	
	
	/**
	 * Reset the input labels
	 */
	public void resetInputLabel() {
		answerInput.setText("");
		remainder.setText("");
	}
	
	
	/**
	 * Set the problem label with a message
	 */
	public void setProblemLabelMessage(String msg) {
		problemDisplay.setText(msg);
	}
	
	
	
	
	
	
	
	////// Save the high score //////////////////////////////////
	////////////////////////////////////////////////////////////
	
	
	public void saveHighScore() {
		//Save the High Score
		SharedPreferences saveVars = getSharedPreferences("PrefName", Context.MODE_PRIVATE);
		saveVars.edit().putInt("highScore", highScore).commit();
	}
	
	
	
	
	
	
	
	
	
	////////////// Action Listener ///////////////////////////
	/////////////////////////////////////////////////////////
	
	
	
	
	/**
	 * Action Listener for the start game button
	 */
	public void startGameAction(OnClickListener e) {
		startGameButton.setOnClickListener(e);
	}
	
	
	/**
	 * Action listener for the final answer button
	 */
	public void finalInputButt(OnClickListener e) {
		answerInputButt.setOnClickListener(e);
	}
	
	
	/**
	 * Set up the spinner action
	 */
	public void spinnerAction(OnItemSelectedListener e) {
		arithChoice.setOnItemSelectedListener(e);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/////////////// Private //////////////////////////////////////
	/////////////////////////////////////////////////////////////




	/**
	 * Set up the default state of the components
	 */
	private void setUpComponentsDefaultState() {
		answerInput 	= (TextView) findViewById(R.id.answerInput);
		remainder		= (TextView) findViewById(R.id.remainderInput);
		points			= (TextView) findViewById(R.id.userEnterLabel);
		problemDisplay  = (TextView) findViewById(R.id.answerLabel);
		arithChoice 	= (Spinner)  findViewById(R.id.arithPick);
		startGameButton	= (Button)   findViewById(R.id.startGame);
		answerInputButt	= (Button)	 findViewById(R.id.finalAnswerInput);
		rules			= (Button)	 findViewById(R.id.rule);

		// Set the default state
		setDivisionComponents(false);
		setAnswerEnabledOrDisabled(false);
	}



	/**
	 * Set up intent
	 */
	private void setUpIntents() {
		rules.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				goToRules();
			}
		});
	}


	/**
	 * Go to the rules intent
	 */
	private void goToRules() {
		Intent goToRules = new Intent(this, Rules.class);
		startActivity(goToRules);
	}
	
	
	/**
	 * Check if intent has app to receive it
	 */
	private boolean doesIntentHaveAppToRecieveIt(Intent intent) {
		PackageManager packageManager = getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
		return activities.size() > 0;
	}


	/**
	 * Create a notification
	 */
	private void createNotification() {
		// Build the notification
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)  
		.setSmallIcon(R.drawable.ic_launcher)  
		.setContentTitle("Get Extra Math Information")  
		.setContentText("Visit helpful math site");  

		// Build the intent
		Uri webpage 				= Uri.parse("http://www.kidsmathgamesonline.com/facts.html");
		Intent webIntent			= new Intent(Intent.ACTION_VIEW, webpage);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, webIntent, PendingIntent.FLAG_UPDATE_CURRENT);  
		builder.setContentIntent(contentIntent);  

		// Add as notification  
		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
		manager.notify(BIND_AUTO_CREATE, builder.build());  
	}
	












}
