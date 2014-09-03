package com.example.triage;

import java.io.IOException;
import java.util.ArrayList;

import com.example.triagetwo.R;

import HospitalStuff.HospitalDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A class that implements the functionality for the activity 
 * that allows a user to check a Patient's medical history
 * (symptoms, vitals and prescription information).
 * @author Ou Ye, Lindsey Poad, Marina Tawfik , Dazhen Wang
 */
public class PatientHistoryActivity extends Activity {
	
	/** this PatientHistoryActivity's HospitalDatabase where the activity
	 *  stores the data of all Patients.
	 */
	private HospitalDatabase hospitalDatabase;
	
	/** The name of the file containing Patients' data.
	 */
	public static final String FILENAME = "records.txt";
	
	/** 
	 * sets up this activity when it starts.
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_history);
		loadDatabase();
		populatePatientHistoryListView();
	}

	/**
	 * Auto generated.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient_history, menu);
		return true;
	}
	
	/**
	 * returns the user to PatientStatusActivity.
	 * @param v
	 */
	public void backToDisplay(View v){
		Intent intent = new Intent(this, PatientStatusActivity.class);
		Intent intentN = getIntent();
		intent.putExtra("HealthCardNumber", 
				intentN.getStringExtra("HealthCardNumber"));
		intent.putExtra("userType", intentN.getStringExtra("userType"));
		startActivity(intent);
	}
	
	/**
	 * adds all the data of the Patient whose health card number was passed
	 * to this PatientHistoryActivity to the list view.
	 */
	private void populatePatientHistoryListView(){
        Intent intentN = getIntent();
        
		ArrayList<String> patientList = hospitalDatabase.eachPatientHistory(
				Integer.parseInt(intentN.getStringExtra("HealthCardNumber")));
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this,
				R.layout.each_patient,
				patientList);
		
		ListView list = (ListView) findViewById(R.id.listView1);
		list.setAdapter(adapter);
	}

	/**
	 * loads the Patients' data stored in the file containing the 
	 * Patients' records.
	 */
	private void loadDatabase(){
        try {
        	hospitalDatabase = new HospitalDatabase(
        			this.getApplicationContext().getFilesDir(), FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
