package com.example.triage;

import java.io.IOException;
import java.util.ArrayList;

import com.example.triagetwo.R;

import HospitalStuff.HospitalDatabase;
import HospitalStuff.Patient;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A class that implements the functionality for the activity 
 * that allows the user to see the list of Patients that have not yet been
 * seen by a doctor ordered according to their umergency level (according to
 * the hospital's policy)
 * @author Ou Ye, Lindsey Poad, Marina Tawfik , Dazhen Wang
 */
public class PatientListActivity extends Activity {

	/** the filename containing Patients' medical records. */
	public static final String FILENAME = "records.txt";
	
	/** this PatientListActivity's HospitalDatabase where the activity
	 *  stores the data of all Patients.
	 */
	private HospitalDatabase hospitalDatabase;
	
	/**
	 * Auto generated: sets the activity when created.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_list);
        populateListView();
	}

	/**
	 * Auto generated.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient_list, menu);
		return true;
	}
	
	/**
	 * transitions the user back to DisplayActivity.
	 * @param v Activity view.
	 */
	public void backToDisplay(View v){
		Intent intent = new Intent(this, DisplayActivity.class);
		Intent intentN = getIntent();
		intent.putExtra("userType", intentN.getStringExtra("userType"));
		startActivity(intent);
	}
	
	/**
	 * adds the following information of patients in the hospital database
	 * that have not yet been seen by a doctor to the list view of this
	 * PatientListActivity, ordered according to urgency level (based on
	 * the hospital policy):
	 * name, date of birth and health card number.
	 */
	private void populateListView(){
        try {
        	hospitalDatabase = new HospitalDatabase
        			(this.getApplicationContext().getFilesDir(), FILENAME);
        } catch (IOException e) {
        	//need to change this
            e.printStackTrace();
        }
        
		ArrayList<Patient> patientList =
				hospitalDatabase.getCategorizePatientList();
		ArrayList<String> patientStringList = new ArrayList<String>();
		for (Patient i:patientList){
			patientStringList.add("Name: " + i.getPatientName() + 
					"\nDate of Birth: " + i.getdob() + 
					" \nHealth Card Number: " + i.getHealthCardNumber());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this,
				R.layout.each_patient,
				patientStringList);
		
		ListView list = (ListView) findViewById(R.id.listView1);
		list.setAdapter(adapter);
	}
}
