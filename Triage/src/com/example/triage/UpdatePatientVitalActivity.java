package com.example.triage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.triagetwo.R;

import HospitalStuff.HospitalDatabase;
import HospitalStuff.Nurse;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A class that implements the functionality for the 
 * UpdatePatientActivity of the application that allows the 
 * user to update the vitals and symptoms of the Patient whose 
 * health card number was passed to this UpdatePatientVitalActivity.
 * @author Ou Ye, Lindsey Poad, Marina Tawfik , Dazhen Wang
 */
public class UpdatePatientVitalActivity extends Activity {

	/** this UpdatePatientVitalActivity's HospitalDatabase where the activity
	 *  stores the data of all Patients.
	 */
	@SuppressWarnings("unused")
	private HospitalDatabase hospitalDatabase;
	
	/** 
	 * The name of the file that contains the Patients' data.
     */
	public static final String FILENAME = "records.txt";
	
	/** 
	 * Auto generated: sets up this activity when it starts.
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_patient_vital);
		
		loadDatabase();
	}

	/**
	 * Auto generated
	 * @param menu
	 * @return
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to 
		//the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_patient_vital, menu);
		return true;
	}
	
	/** 
	 * reads the data from the UI and saves it then transitions the user
	 * to PatientStatusActivity.
	 * @param v Activity view.
	 * @throws IOException 
	 */
	public void updatePatientVitals(View v) throws IOException{

		Intent intentN = getIntent();
		Integer healthCardNumber = 
				Integer.parseInt(intentN.getStringExtra("healthNumber"));

		EditText patientTemperatureText = (EditText) 
				findViewById(R.id.patient_temperature);
		EditText patientSystolicBloodPressureText = (EditText) 
				findViewById(R.id.patient_systolic_pressure);
		EditText patientDiastolicBloodPressureText = (EditText) 
				findViewById(R.id.patient_diastolic_pressure);
		EditText patientHeartRateText = (EditText) 
				findViewById(R.id.patient_heartRate);
		EditText patientSymptomsText = (EditText) 
				findViewById(R.id.patient_symptoms);

		String patientTemperature = patientTemperatureText.
				getText().toString();
		String patientSystolicBloodPressure = 
				patientSystolicBloodPressureText.getText().toString();
		String patientDiastolicBloodPressure = 
				patientDiastolicBloodPressureText.getText().toString();
		String patientHeartRate = patientHeartRateText.
				getText().toString();
		String patientSymptoms = patientSymptomsText.
				getText().toString();

		if(patientTemperature.equals("") | 
				patientSystolicBloodPressure.equals("") |
				patientDiastolicBloodPressure.equals("") |
				patientHeartRate.equals("") |
				patientSymptoms.equals("")){
			
			Toast msg =  Toast.makeText(this,"Invalid Input", 
					Toast.LENGTH_LONG );
			msg.show();
		}
		else{
			savePatientVitalInfo(healthCardNumber, 
					patientTemperature,
					patientSystolicBloodPressure, 
					patientDiastolicBloodPressure,
					patientHeartRate,
					patientSymptoms);
	        
	        Intent intent = new Intent(this, PatientStatusActivity.class);
	        intent.putExtra("userType", "nurse");
	    	intent.putExtra("HealthCardNumber", intentN.getStringExtra(
	    			"healthNumber"));
	        startActivity(intent);
		}
	}
	
	/**
	 * saves the Patient's data whose health card number is healthCardNumber,
	 * temperature patientTemperature, systolic blood pressure
	 * patientSystolicBloodPressure, diatolic blood pressure 
	 * patientDiastolicBloodPressure, heart rate  patientHeartRate and symptoms
	 * patientSymptoms and saves data to the file containing 
	 * the Patients' data.
	 * @param healthCardNumber the Patient's health card number.
	 * @param patientTemperature the Patient's temperature.
	 * @param patientSystolicBloodPressure the Patient's systolic blood 
	 * pressure.
	 * @param patientDiastolicBloodPressure the patient's diastolic blood 
	 * pressure.
	 * @param patientHeartRate the Patient's blood pressure.
	 * @param patientSymptoms the Patient's symptoms.
	 */
	private void savePatientVitalInfo(Integer healthCardNumber, 
			String patientTemperature,
			String patientSystolicBloodPressure, 
			String patientDiastolicBloodPressure,
			String patientHeartRate,
			String patientSymptoms) throws IOException{
	
		Nurse nurse = new Nurse(
				this.getApplicationContext().getFilesDir(),FILENAME);
		nurse.updatePatientVitals(healthCardNumber, 
				Double.parseDouble(patientTemperature),
				Double.parseDouble(patientSystolicBloodPressure), 
				Double.parseDouble(patientDiastolicBloodPressure),
				Double.parseDouble(patientHeartRate));
		
		nurse.updatePatientSymptoms(healthCardNumber,
				patientSymptoms);

        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(DisplayActivity.FILENAME, 
                                          Context.MODE_PRIVATE);
            nurse.saveDataToFile(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * loads the Patients' data stored in the file containing the 
	 * Patients' records.
	 */
	private void loadDatabase(){
        try {
        	hospitalDatabase = new HospitalDatabase(
        			this.getApplicationContext().getFilesDir(),FILENAME);
        } catch (IOException e) {
			Toast msg =  Toast.makeText(this,"File Not found.", 
					Toast.LENGTH_LONG );
			msg.show();
        }
	}
}

