package com.example.triage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.triagetwo.R;

import HospitalStuff.Doctor;
import HospitalStuff.HospitalDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

/**
 * A class that implements the functionality for the activity 
 * that allows a doctor to add prescription information to Patients.
 * @author Ou Ye, Lindsey Poad, Marina Tawfik , Dazhen Wang
 */
public class DoctorActivity extends Activity {
	
	/** this DoctorActivity's HospitalDatabase where the activity
	 *  stores the data of all Patients.
	 */
	@SuppressWarnings("unused")
	private HospitalDatabase hospitalDatabase;
	
	/** 
	 * The name of the file that contains the Patients' data.
	 */
	public static final String FILENAME = "records.txt";
	
	/** 
	 * Auto generated : sets up this activity when it starts.
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor);
		loadDatabase();
	}

	/**
	 * Auto generated.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if 
		// it is present.
		getMenuInflater().inflate(R.menu.doctor, menu);
		return true;
	}
	
	/**
	 * reads the prescription information (name and instructions) from the text
	 * fields and adds the information to the Patients' information then
	 * transitions the user to PatientStatusActivity.
	 * @param v Activity view.
	 * @throws IOException
	 * @throws NotSeenByNurseException
	 */
	public void saveMedicationInfo(View v) throws IOException{
		Intent intentN = getIntent();
		Integer healthCardNumber =
				Integer.parseInt(intentN.getStringExtra("HealthCardNumber"));
		TextView medicationNameText =
				(TextView) findViewById(R.id.medication_name);
		TextView medicationInstructionText = 
				(TextView) findViewById(R.id.medication_instruction);

		String name = medicationNameText.getText().toString();
		String instruction = medicationInstructionText.getText().toString();
		
		Doctor doctor = new Doctor(
				this.getApplicationContext().getFilesDir(), FILENAME);
		doctor.recordPrescription(healthCardNumber, name, instruction);
		
		FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(DisplayActivity.FILENAME, 
                                          Context.MODE_PRIVATE);
            doctor.saveDataToFile(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
		Intent intent = new Intent(this, PatientStatusActivity.class);
		intent.putExtra("HealthCardNumber",
				intentN.getStringExtra("HealthCardNumber"));
		intent.putExtra("userType", intentN.getStringExtra("userType"));
		startActivity(intent);
	}
	
	/**
	 * transitions the user to PatientStatusActivity.
	 * @param v Activity view.
	 */
	public void backToStatus(View v){
		Intent intent = new Intent(this, PatientStatusActivity.class);
		Intent intentN = getIntent();
		intent.putExtra("HealthCardNumber",
				intentN.getStringExtra("HealthCardNumber"));
		intent.putExtra("userType", intentN.getStringExtra("userType"));
		startActivity(intent);
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
