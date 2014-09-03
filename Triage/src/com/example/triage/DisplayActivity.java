package com.example.triage;

import java.io.IOException;

import com.example.triagetwo.R;

import HospitalStuff.HospitalDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A class that implements the functionality for the second activity 
 * of the application.
 * @author Ou Ye, Lindsey Poad, Marina Tawfik , Dazhen Wang
 */
public class DisplayActivity extends Activity {

	/** the filename containing Patients' medical records. */
	public static final String FILENAME = "records.txt";
	
	/** this DisplayActivity's HospitalDatabase where the activity
	 *  stores the data of all Patients.
	 */
	private HospitalDatabase hospitalDatabase;
	
	/**
	 * Auto generated: sets up this Activity when created.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		loadDatabase();
	}

	/**
	 * Auto generated
	 * @param menu
	 * @return
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items 
		// to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display, menu);
		return true;
	}
	
	/**
	 * transition user to RegisterPatientActivity.
	 * @param v this Activity's view.
	 */
	public void newPatient(View v){
		newPatientFunction();
	}
	
	/**
	 * transitions user to PatientStatusActivity.
	 * @param v
	 */
	public void findPatient(View v){
		findPatientFunction();
	}
	
	/**
	 * transitions user to PatientListActivity given that the user
	 * is a Nurse.
	 * @param v this Activity's view.
	 */
	public void listPatients(View v){
		listPatientsFunction();
	}
	
	/**
	 * transitions user back to MainActivity.
	 * @param v
	 */
	public void logoutUser(View v){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	/**
	 * obtains Patient's health card number from the text field,
	 * and transitions the user to the PatientStatusActivity containing 
	 * information about the Patient with the provided health card number
	 * iff such a Patient exists in the hospital's recorded data.
	 */
	private void findPatientFunction(){
		EditText patientHealthCardNumberText = (EditText) findViewById(
				R.id.patient_health_card_number);
		String patientHealthCardNumber = patientHealthCardNumberText.
				getText().toString();

		if(!patientHealthCardNumber.equals("")){
			if(hospitalDatabase.findPatient(
					Integer.parseInt(patientHealthCardNumber)) != null){
				Intent intentN = getIntent();
				Intent intent = new Intent(this, PatientStatusActivity.class);
				// pass the Health card number which identifies the patient.
				intent.putExtra("HealthCardNumber", patientHealthCardNumber);
				intent.putExtra("userType", intentN.getStringExtra("userType"));
		        startActivity(intent);
			}else{
				Toast msg =  Toast.makeText(this,"Patient Not Found.", 
						Toast.LENGTH_LONG );
				msg.show();
			}
		}else{
			Toast msg =  Toast.makeText(this,"Patient Not Found.", 
					Toast.LENGTH_LONG );
			msg.show();
		}
	}
	
	/**
	 * transitions the user to PatientListActivity that contains the list
	 * of Patients who have not yet been seen by a doctor ordered by their
	 * urgency level (according to the hospital policy)given that the user is a
	 * Nurse.
	 */
	private void listPatientsFunction(){
		Intent intentN = getIntent();
		System.out.println(!intentN.getStringExtra("userType").toString().
				equals("physician"));
		if (intentN.getStringExtra("userType").toString().equals("physician")){
			Toast msg =  Toast.makeText(this,"You Have No Access", 
					Toast.LENGTH_LONG );
			msg.show();
		}else{
			Intent intent = new Intent(this, PatientListActivity.class);
			intent.putExtra("userType", intentN.getStringExtra("userType"));
			startActivity(intent);
        }
	}
	
	/**
	 * creates a new HospitalDatabase to be able to manage the hospital's
	 * recorded data.
	 */
	private void loadDatabase(){
        try {
        	hospitalDatabase = new HospitalDatabase
        			(this.getApplicationContext().getFilesDir(), FILENAME);
        } catch (IOException e) {
        	//need to change this
            e.printStackTrace();
        }
	}
	
	/**
	 * transitions the user to RegisterPatientActivity where a new Patient
	 * can be registered, given that the user is a Nurse.
	 */
	private void newPatientFunction(){
		Intent intentN = getIntent();
		if (intentN.getStringExtra("userType").toString().equals("physician")){
			Toast msg =  Toast.makeText(this,"You Have No Access", 
					Toast.LENGTH_LONG );
			msg.show();
		}else{
			Intent intent = new Intent(this, RegisterPatientActivity.class);
			startActivity(intent);
        }
	}
}


