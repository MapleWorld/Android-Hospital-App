package HospitalStuff;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.annotation.SuppressLint;

/**
 * A representation of a Patient.
 * @author Ou Ye, Lindsey Poad, Marina Tawfik , Dazhen Wang
 */
public class Patient implements Serializable  {

	/** This Patient's unique ID for serialization.*/
	private static final long serialVersionUID = 8377228248845285495L;
	
	/** This patient's name.*/
	protected String name;
	
	/** This patient's date of birth.*/
	protected String dob;
		
	/** This patient's health card number.*/
	protected Integer healthCardNumber;
	
	/** This patient's visits to the hospital.*/
	protected Visit visits ;
	
	/** This patient's arrival time to the hospital.*/
	protected String arrivalTime;
	
	/** This patient's medical record.*/
	protected MedicalRecord patientMedRecord;
	
	/** Constructs this Patient with name patientName, with health card
	 *  number healthCardNumber who was born on day day of month month
	 *  in year year.
	 * @param patientName the patient's name.
	 * @param healthCardNumber the patients health card number.
	 * @param month	the patient's month of birth.
	 * @param day	the patient's day of birth.
	 * @param year	the patient's month of birth.
	 */
	@SuppressLint("SimpleDateFormat")
	public Patient(String name,  Integer healthCardNumber, int day, 
			int month, int year) {
		this.name = name;
		this.healthCardNumber = healthCardNumber;
	    this.dob = String.valueOf(day) + "-" + String.valueOf(month) + 
	    		"-" + String.valueOf(year);
	    this.visits = new Visit(); 
	    this.patientMedRecord = new MedicalRecord();
		this.arrivalTime = new SimpleDateFormat("MM-dd-HH-mm").
				format(Calendar.getInstance().getTime());
	}

	/** Returns this Patient's age.
	 * @return this Patient's age.
	 */
	public Integer getAge(){
		String [] dateParts = dob.split("-");
		int year = Integer.parseInt(dateParts[2]);
		Integer patientAge = 
				Calendar.getInstance().get(Calendar.YEAR) - year ;
		return patientAge;
	}
	
	/**
	 * returns this Patient's visits to the hospital.
	 * @return this Patient's visits to the hospital.
	 */
	public Visit getVisits (){
		return visits;
	}

	/**
	 * returns this Patient's health card number.
	 * @returnthis Patient's health card number.
	 */
	public Integer getHealthCardNumber() {
		return healthCardNumber;
	}

	/**
	 * returns this Patient's name.
	 * @return this Patient's name.
	 */
	public String getPatientName(){
		return name;
	}
	
	/**
	 * Returns a string representation of this Patient.
	 */
	@ Override
	public String toString(){
		String str = name + "#" + dob + "#" + 
				healthCardNumber.toString() + "#";
		
		str += this.getAge() + "@" + this.getArrivalTime() + "@" +
				this.patientMedRecord.toString();
		
		str += this.getVisits().toString();
		
		return str;
	}
	
	/**
	 * Sets this Patient's arrival time to the hospital to arrivalTime.
	 * @param arrivalTime this Patient's arrival time to the hospital.
	 */
	public void setArrivalTime(String arrivalTime){
		this.arrivalTime = arrivalTime;
	}
	
	/** Returns this Patient's arrival time to the hospital.
	 * @return this Patient's arrival time to the hospital.
	 */
	public String getArrivalTime(){
		return this.arrivalTime;
	}

	/** Returns this Patient's medical record which includes the Patient's
	 * vitals, symptoms and prescription information.
	 * @return this Patient's medical record which includes the Patient's
	 *         vitals, symptoms and prescription information.
	 */
	public MedicalRecord getMedRecord(){
		return this.patientMedRecord;
	}
	
	/** Returns this Patient's date of birth.
	 * @return this Patient's date of birth.
	 */
	public String getdob(){
		return dob;
	}
}
	
	
	


	


