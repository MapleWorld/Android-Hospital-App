package HospitalStuff;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import HospitalStuff.Patient;

/**
 * A representation of a Nurse. 
 * @author Ou Ye, Lindsey Poad, Marina Tawfik , Dazhen Wang
 */
public class Nurse extends HealthProfessional implements Serializable {

	/** This Nurse's unique ID for serialization.*/
	private static final long serialVersionUID = -3437891170663779781L;

	/** 
	 * Constructs a new Nurse that manages a collection of Patients'
	 * records stored in a directory dir in file named fileName.
	 * @param dir the directory in which the patients' data is stored.
	 * @param fileName the data file containing the patients' information.
	 * @throws IOException
	 */
	public Nurse(File dir, String fileName) throws IOException {
		super(dir, fileName);
	}
	
	/** Records the data of the Patient with name PatientName,
	 *  health card number HealthCardNumber, who was born on day day 
	 *  of month month in the year year to the hospital database.
	 * @param patientName the Patient's name.
	 * @param healthCardNumber the patient's health card number.
	 * @param day the day when this patient was born.
	 * @param month the month on which this patient was born.
	 * @param year the year in which this patient was born.
	 */
	public Patient recordIndividualPatientData(String patientName,  
			Integer healthCardNumber, int day, int month, int year){
		Patient newPatient = new Patient(patientName, 
				healthCardNumber,
				day,month, year);
		return newPatient;
	}
	
	/** Updates vitals of the patient with Health card number healthCardNumber
	 * whose temperature is temperature, blood pressure equals to bloodPressure
	 * and heart rate equals to heartRate.
	 * @param healthCardNumber the patient's health card number.
	 * @param temperature the patient's temperature.
	 * @param bloodPressure the patient's blood pressure.
	 * @param heartRate the patient's heart rate.
	 */
	public void updatePatientVitals(Integer healthCardNumber, 
			Double temperature, Double systolic, 
			Double diastolic, Double heartRate){
		Patient patient = hospitalDatabase.findPatient(healthCardNumber);
		patient.getMedRecord().updateTemperature(temperature);
		patient.getMedRecord().updateBloodPressure(systolic, diastolic);
		patient.getMedRecord().updateHeartRate(heartRate);
		
		patient.getVisits().calculateUrgency(patient.getAge(), 
				temperature, systolic, diastolic, heartRate);
	}
	
	/**
	 * Updates symptoms of the patient with Health card number healthCardNumber
	 * whose new symptoms are symptoms.
	 * @param healthCardNumber the patient's health card number.
	 * @param symptoms the patient's symptoms.
	 */
	public void updatePatientSymptoms(Integer healthCardNumber, 
			String symptoms){
		Patient patient = hospitalDatabase.findPatient(healthCardNumber);
		patient.getMedRecord().updateSymptoms(symptoms);	
	}
	
	/** 
	 * returns the Patient with health card number healthCardNumber.
	 * @param healthCardNumber the patient's health card number.
	 * @return the Patient with health card number healthCardNumber.
	 */
	public Patient currentPatient(Integer healthCardNumber){
		Patient patient = hospitalDatabase.findPatient(healthCardNumber);
		return patient;
	}
	
	/**
	 * sets the most recent seen by doctor status of the  patient with health 
	 * card number healthCardNumber to true.
	 * @param healthCardNum the patient's health card number.
	 */
	public void seenByDoctor(Integer healthCardNumber){
		Patient patient = currentPatient(healthCardNumber);
		patient.getVisits().setSeenByDr(true);
	}
}
