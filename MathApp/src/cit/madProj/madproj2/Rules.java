package cit.madProj.madproj2;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.graphics.Color;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

public class Rules extends Activity {
	
	// Components
	Button goBack;
	ToggleButton togButt;
	ProgressBar progBar;
	
	// Handles the update of the progress bar
	private Handler handler = new Handler();
	
	private int progress = 0;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rules);
		
		// Change background colour
		getWindow().getDecorView().setBackgroundColor(Color.LTGRAY);	
		
		// Set up the components
		setUpComponents();
		
		
	}

	
	
	
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rules, menu);
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	//// Private /////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////
	
	private void setUpComponents() {
		goBack  = (Button) 		 findViewById(R.id.backFromRules);
		togButt = (ToggleButton) findViewById(R.id.toggleButtonForLoad);
		progBar = (ProgressBar)  findViewById(R.id.progressBar);
		
		// Action listeners. They don't need to do much
		goBack.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		
		togButt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (togButt.isChecked()) {
					simulateLoad();
				}
				else {
					// Reset the progress bar
					progress = 0;
					progBar.setProgress(progress);
				}
			}
		});
		
		
	}
	
	
	
	
	/**
	 * Simulate loading for the progress bar
	 */
	private void simulateLoad() {
		// Start a thread so it does something in the background
		new Thread(new Runnable() {
			public void run() {

				// Control the progress bar
				while (progress < 100) {

					// Increment the progress bar				
					progress += doSomeMundaneWork();

					// Update the progress bar
					handler.post(new Runnable() {
						public void run() {
							progBar.setProgress(progress);					
						}
					});					
				}
			}


		}).start();

	}

	
	
	
	/**
	 * Do some slow work so the progress bar is incremented
	 */
	private int doSomeMundaneWork() {
		try {
			Thread.sleep(20);
			return 1;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 1;
	}

}

























