package com.example.triage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.triagetwo.R;

import HospitalStuff.HospitalDatabase;
import HospitalStuff.Nurse;
import HospitalStuff.Patient;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A class that implements the functionality for the 
 * PatientStatusActivity of the application that allows a user
 * to see the personal information and the medical information (vitals, 
 * symptoms, prescription information) of the patient whose health card number
 * was passed to this activity.
 * @author Ou Ye, Lindsey Poad, Marina Tawfik , Dazhen Wang
 *
 */
public class PatientStatusActivity extends Activity {
	
	/** this PatientStatusActivity's HospitalDatabase where the activity
	 *  stores the data of all Patients.
	 */
	private HospitalDatabase hospitalDatabase;
	
	/** The name of file containing Patients' data.
	 */
	public static final String FILENAME = "records.txt";
	
	/** 
	 * sets up this activity when it starts.
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_status);
		patientLatestStatus();
	}

	/**
	 * Auto generated
	 * @param menu
	 * @return
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to
		// the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient_status, menu);
		return true;
	}
	
	/** 
	 * transitions the user to UpdatePatientActivity where the user can update 
	 * the patient's activity given that the user is a nurse.
	 * @param v Activity view.
	 */
	public void updatePatientVitals(View v){
		Intent intentN = getIntent();
		if (intentN.getStringExtra("userType").toString().equals("physician")){
			Toast msg =  Toast.makeText(this,"You Have No Access", 
					Toast.LENGTH_LONG );
			msg.show();
		}else{
			Intent intent = new Intent(this, UpdatePatientVitalActivity.class);
			intent.putExtra("healthNumber", 
					intentN.getStringExtra("HealthCardNumber"));
			startActivity(intent);
		}
	}
	
	/**
	 * transitions the user to DoctorActivity where the user can update the 
	 * prescription information of the patient given that the user is a doctor.
	 * @param v Activity view.
	 */
	public void doctorMethods(View v){
		Intent intentN = getIntent();
		if (intentN.getStringExtra("userType").toString().equals("nurse")){
			Toast msg =  Toast.makeText(this,"You Have No Access", 
					Toast.LENGTH_LONG );
			msg.show();
		}else{
			Intent intent = new Intent(this, DoctorActivity.class);
			intent.putExtra("HealthCardNumber", 
					intentN.getStringExtra("HealthCardNumber"));
			intent.putExtra("userType", 
					intentN.getStringExtra("userType"));
			startActivity(intent);
		}
	}
	
	/**
	 * transitions the user to PatientHistoryActivity where the user
	 * can see the entire medical record of the patient.
	 * @param v
	 */
	public void patientHistory(View v){
		Intent intent = new Intent(this, PatientHistoryActivity.class);
		Intent intentN = getIntent();
		intent.putExtra("HealthCardNumber", 
				intentN.getStringExtra("HealthCardNumber"));
		intent.putExtra("userType", intentN.getStringExtra("userType"));
		startActivity(intent);
	}
	
	/**
	 * changes the seen by doctor status of the patient whose health card
	 * number was passed to this PatientStatusActivity to true.
	 * @param v
	 * @throws IOException
	 */
	public void seenByDoctor(View v) throws IOException{
		Intent intentN = getIntent();
		if (!intentN.getStringExtra("userType").toString().equals("nurse")){
			Toast msg =  Toast.makeText(this,"You Have No Access", 
					Toast.LENGTH_LONG );
			msg.show();
		}else{
			Nurse nurse = new Nurse(
					this.getApplicationContext().getFilesDir(), FILENAME);
			nurse.seenByDoctor(
					Integer.parseInt(intentN.getStringExtra("HealthCardNumber")));
	        FileOutputStream outputStream;
	        try {
	            outputStream = openFileOutput(DisplayActivity.FILENAME, 
	                                          Context.MODE_PRIVATE);
	            nurse.saveDataToFile(outputStream);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
		}
	}
	
	/**
	 * returns the user to DisplayActivity.
	 * @param v
	 */
	public void backToDisplay(View v){
		Intent intent = new Intent(this, DisplayActivity.class);
		Intent intentN = getIntent();
		intent.putExtra("HealthCardNumber",
				intentN.getStringExtra("HealthCardNumber"));
		intent.putExtra("userType", intentN.getStringExtra("userType"));
		startActivity(intent);
	}
	
	/**
	 * update the text views such that they show the personal
	 * information and the medical information (vitals, 
     * symptoms, prescription information) of the patient 
     * whose health card number was passed to this PatientStatusActivity.
	 */
	private void patientLatestStatus(){
        try {
        	hospitalDatabase = new HospitalDatabase(
        			this.getApplicationContext().getFilesDir(), FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
		Intent intent = getIntent();
		TextView patient_name = (TextView) findViewById(R.id.patient_name);
		TextView patient_dob = (TextView) findViewById(R.id.textView1);
		TextView patient_temperature = (TextView) findViewById(R.id.textView2);
		TextView patient_heartRate = (TextView) findViewById(R.id.textView3);
		TextView patient_bloodPressure = 
				(TextView) findViewById(R.id.textView4);
		TextView patient_symptoms = (TextView) findViewById(R.id.textView5);
		TextView patientMedicationName = 
				(TextView) findViewById(R.id.textView6);
		TextView patientMedicationInstruction =
				(TextView) findViewById(R.id.textView7);
		// if patient is in the hospital database.
		if(hospitalDatabase.findPatient(Integer.parseInt(intent.getStringExtra(
				"HealthCardNumber"))) != null){
			Integer healthCardNumber = 
					Integer.parseInt(intent.getStringExtra(
							"HealthCardNumber"));
			Patient patient = hospitalDatabase.findPatient(healthCardNumber);
			patient_name.setText("Patient Name: " + patient.getPatientName());
			patient_dob.setText("BirthDate: " + patient.getdob());
			patient_temperature.setText("Temperature: " + 
					patient.getMedRecord().getTemperature().toString());
			patient_heartRate.setText("HeartRate: " + 
					patient.getMedRecord().getHeartRate().toString());
			patient_bloodPressure.setText("Blood Pressure: " + 
					patient.getMedRecord().getBloodPressure().toString());
			patient_symptoms.setText("Symptoms: " + 
					patient.getMedRecord().getSymptoms());
			patientMedicationName.setText("Medication Name: " + 
					patient.getMedRecord().getMedicationName());
			patientMedicationInstruction.setText("Medication Instruction: " + 
					patient.getMedRecord().getMedicationInstruction());
		}
	}
}
