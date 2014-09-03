package HospitalStuff;

import java.io.Serializable;
import java.util.Stack;

/**
 * implements the Medical Record of a Patient that includes
 * the Patient's vitals, symptoms and prescription information.
 * @author Ou Ye, Lindsey Poad, Marina Tawfik , Dazhen Wang.
 */
public class MedicalRecord implements Serializable {
	
	/** this MedicalRecord's unique ID for serialization. */
	private static final long serialVersionUID = -7731337940944264288L;

	/** The patient's symptoms.*/
	private Stack<String> symptoms;
		
	/** The patient's temperature.*/
	private Stack<Double> temp;
		
	/** The patient's Systolic blood pressure.*/
	private Stack<String> bloodPressure;
		 
	/** The patient's hear rate.*/
	private Stack<Double> heartRate;
		
	/** the prescription instructions provided to the Patient*/
	private Stack<String> medicationInstruction;
	
	/** the prescription names provided to the Patient*/
	private Stack<String> medicationName;
	
	/** Constructs the patient's Medical Record.*/
	public MedicalRecord(){
		symptoms = new Stack<String>();
		temp = new Stack<Double>();
		bloodPressure = new Stack<String>();
		heartRate = new Stack<Double>();
		medicationInstruction = new Stack<String>();
		medicationName = new Stack<String>();
	}
		
	/** Update's the patient's symptoms with his/her current symptoms
	 * currentSymptoms.
	 * @param currentSymptoms the patient's current symptoms.
	 */
	public void updateSymptoms(String currentSymptoms){
		symptoms.push(currentSymptoms.trim());
		
	}
		
	/** Updates the patient's temperature with the current temperature
	 * temperature.
	 * @param temperature the patient's current temperature.
	 */
	public void updateTemperature(Double temperature){
		 temp.push(temperature);
	}
	
	/**
	 * Updates the patient's blood pressure based on the patient's current
	 * systolic blood presuure systolicBloodPressure and diastolic blood
	 * pressure diastolicBloodPressure.
	 * @param systolicBloodPressure the patient's current systolic blood
	 *  pressure.
	 * @param diastolicBloodPressure the patient's current diastolic blood 
	 * pressure.
	 */
	public void updateBloodPressure(Double systolicBloodPressure,
			Double diastolicBloodPressure){
		String bloodPressure = systolicBloodPressure.toString()+
				 "/" + diastolicBloodPressure.toString();
		this.bloodPressure.push(bloodPressure);
	}
	
	/** Updates the patient's heart rate with the patient's current heart rate
	 * heartRate.
	 * @param heartRate	the patient's current heart rate.
	 */
	public void updateHeartRate(Double heartRate){
		 this.heartRate.push(heartRate);
	}
		
	/** Returns the patient's most recent symptoms.
	 * @return the patient's most recent symptoms.
	 */
	public String getSymptoms(){
		return symptoms.peek();
	}
	
	/** Returns the patient's most recent temperature.
	 * @return the patient's most recent temperature.
	 */
	public Double getTemperature(){
		return temp.peek();
	}
	
	/** Returns the patient's most recent blood pressure.
	 * @return the patient's most recent blood pressure.
	 */
	public String getBloodPressure(){
		return bloodPressure.peek();
	}
	
	/** Returns the patient's most recent heart rate.
	 * @return the patient's most recent heart rate.
	 */
	public Double getHeartRate(){
		return heartRate.peek();
	}
	
	/** Returns a string representation of the Patient's Symptoms History.
	 * @return a string representation of the Patient's Symptoms History.
	 */
	public String getSymptomsHistory(){
		return symptoms.toString();
	}
	
	/** Returns a string representation of the Patient's temperature History.
	 * @return a string representation of the Patient's temperature history.
	 */
	public String getTemperatureHistory(){
		return temp.toString();
	}
	
	/** Returns a string representation of the Patient's blood pressure
	 *          history.
	 * @return a string representation of the Patient's 
	 *         blood pressure history.
	 */
	public String getBloodPressureHistory(){
		return bloodPressure.toString();
	}
	
	/** Returns a string representation of the Patient's heart rate history.
	 * @return a string representation of the Patient's heart rate history.
	 */
	public String getHeartRateHistory(){
		return heartRate.toString();
	}

	/** Returns a string representation of the Patient's prescription name
	 *  history.
	 * @return a string representation of the Patient's prescription name
	 *  history.
	 */ 
	public String getMedicationNameHistory(){
		return medicationName.toString();
	}
	
	/** Returns a string representation of the Patient's prescription 
	 * instructions history.
	 * @return a string representation of the Patient's prescription
	 * instructions history.
	 */ 
	public String getMedicationInstructionHistory(){
		return medicationInstruction.toString();
	}
	
	/** Returns the patient's most recent prescription instructions.
	 * @return the patient's most recent prescription instructions.
	 */
	public String getMedicationInstruction() {
		return medicationInstruction.peek();
	}

	/**
	 * Updates the patient's prescription instructions with the 
	 * new prescription instructions medicationInstruction.
	 * @param medicationInstruction the new prescription instructions.
	 */
	public void setMedicationInstruction(String medicationInstruction) {
		this.medicationInstruction.push(medicationInstruction);
	}
	
	/** Returns the patient's most recent prescription name.
	 * @return the patient's most recent prescription name.
	 */
	public String getMedicationName() {
		return medicationName.peek();
	}

	/**
	 * Updates the patient's prescription name with the 
	 * new prescription name medicationName.
	 * @param medicationName the new prescription name.
	 */
	public void setMedicationName(String medicationName) {
		this.medicationName.push(medicationName);
	}
	
	/**
	 * returns a String representation of this medical record.
	 */
	@Override
	public String toString(){
		String str = getTemperatureHistory() + "@" + 
				getBloodPressureHistory() + "@" + 
				getHeartRateHistory() + "@" + 
				getSymptomsHistory() + "@" + 
				getMedicationNameHistory() + "@" + 
				getMedicationInstructionHistory();
		return str;
	}
}
