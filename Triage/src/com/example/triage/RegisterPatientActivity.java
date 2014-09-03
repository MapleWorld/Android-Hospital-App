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
import android.widget.EditText;
import android.widget.Toast;

/**
 * A class that implements the functionality for the 
 * RegisterPatientActivity of the application that allows a user
 * to add a new patient to the Hospital database.
 * @author Ou Ye, Lindsey Poad, Marina Tawfik , Dazhen Wang
 */
public class RegisterPatientActivity extends Activity {

	/** this RegisterPatientActivity's HospitalDatabase where the activity
	 *  stores the data of all Patients.
	 */
	private HospitalDatabase hospitalDatabase;
	
	/**
	 *  The name of the file containing all patients' data.
	 */
	public static final String FILENAME = "records.txt";
	
	/** 
	 * Auto generated: sets up this activity when it starts.
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_patient);
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
		getMenuInflater().inflate(R.menu.register_patient, menu);
		return true;
	}
	
	/**
	 * obtains the data from UI, registers the Patient and saves the Patient's
	 * information and transitions the user to UpdatePatientVitalActivity given
	 * that the user provides correct input.
	 * @param v Activity view.
	 * @throws IOException 
	 */
	public void registerNewPatient(View v) throws IOException{

		//get data from text fields
        EditText patientNameText = (EditText) 
        		findViewById(R.id.patient_name);
        EditText patientHealthCardNumberText = (EditText) 
        		findViewById(R.id.patient_health_card_number);
        EditText patientDayText = (EditText) 
        		findViewById(R.id.day);
        EditText patientMonthText = (EditText) 
        		findViewById(R.id.month);
        EditText patientYearText = (EditText) 
        		findViewById(R.id.year);
        
        String patientName = patientNameText.getText().
        		toString();
        String patientHealthCard =
        		patientHealthCardNumberText.getText().toString();
        String patientDay = patientDayText.getText().
        		toString();
        String patientMonth = patientMonthText.getText().
        		toString();
        String patientYear = patientYearText.getText().
        		toString();

		if(registerPatientValidation(patientName,
	    		patientHealthCard,patientDay,
	    		patientMonth,patientYear)){
        	Toast msg =  Toast.makeText(this,"Invalid Input", 
					Toast.LENGTH_LONG );
			msg.show();
		}
		else{
			SaveRegisterPatientInfo(patientName,
		    		patientHealthCard,patientDay,
		    		patientMonth,patientYear);
	        
	        Intent intent = new Intent(this, UpdatePatientVitalActivity.class);
	        intent.putExtra("healthNumber", patientHealthCard);
	        startActivity(intent);
		}
	}
	
	/**
	 * saves the information of the Patient with name patientName, 
	 * health card number patientHealthCard, who was born on patientDay day 
	 * of month patientMonth in year patientYear then writes the data to file. 
	 * @param patientName the Patient's name.
	 * @param patientHealthCard the Patient's health card number.
	 * @param patientDay the day on which this Patient was born.
	 * @param patientMonth the month on which this Patient was born.
	 * @param patientYear the year in which this Patient was born.
	 * @throws IOException
	 */
    private void SaveRegisterPatientInfo(String patientName,
    		String patientHealthCard,
    		String patientDay,
    		String patientMonth,
    		String patientYear) throws IOException{
    	
        Nurse nurse = new Nurse(
        		this.getApplicationContext().getFilesDir(), FILENAME);
        Patient newPatient = nurse.recordIndividualPatientData(
        		patientName, 
        		Integer.parseInt(patientHealthCard), 
        		Integer.parseInt(patientDay), 
        		Integer.parseInt(patientMonth), 
        		Integer.parseInt(patientYear));
        
        hospitalDatabase.addPatient(newPatient);

        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(DisplayActivity.FILENAME, 
                                          Context.MODE_PRIVATE);
            hospitalDatabase.saveDataToFile(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * returns true iff the fields patientName,patientHealthCard, patientDay,
     * patientMonth and patientYear are valid data for creating a Patient.
     * @param patientName the data entered by user for a Patient's name.
     * @param patientHealthCard the data entered by user for a Patient's 
     * health card number.
     * @param patientDay the data entered by user for the day on which
     * the patient was born.
     * @param patientMonth the data entered by user for the month on which
     * the patient was born.
     * @param patientYear the data entered by user for the year on which
     * the patient was born.
     * @return
     */
    private boolean registerPatientValidation(String patientName,
    		String patientHealthCard,
    		String patientDay,
    		String patientMonth,
    		String patientYear){
    	
    	boolean validate = false;
    	
    	if(patientName.equals("") | 
				patientHealthCard.equals("") |
				patientDay.equals("") |
				patientMonth.equals("") |
				patientYear.equals("")){
    		validate = true;
		}
		else if(Integer.parseInt(patientDay) >= 31 ){
			validate = true;
		}
		else if(Integer.parseInt(patientMonth) >= 13 ){
			validate = true;
		}
		else if(Integer.parseInt(patientYear) >= 2013 ){
			validate = true;
		}
    	return validate;
 
    }

    /**
	 * loads the Patients' data in file.
	 */
	private void loadDatabase(){
        try {
        	hospitalDatabase = new HospitalDatabase(
        			this.getApplicationContext().getFilesDir(), FILENAME);
        } catch (IOException e) {
            //e.printStackTrace();
        	Toast msg =  Toast.makeText(this,"File Not Found.", 
					Toast.LENGTH_LONG );
			msg.show();
        }
	}
}
