package HospitalStuff;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.annotation.SuppressLint;

/**
 * A representation of a Patient's visit to the hospital. 
 * @author Ou Ye, Lindsey Poad, Marina Tawfik , Dazhen Wang
 */
public class Visit {
	
	/** This visit's seen by doctor status. */
	private Boolean seenByDr;
	
	/** This visit's urgency level (based on the hospital policy) */
	private Integer urgency;
	
	/** The time when the patient was seen by the doctor in this Visit. */
	private String timeSeenByDr;
	
	/**
	 * Constructs a new Visit.
	 */
	@SuppressLint("UseValueOf")
	public Visit(){
		this.seenByDr = new Boolean(false);
		this.urgency = 0;
		this.timeSeenByDr = "";
	}

	/**
	 * Sets this Visit's seen by doctor status to seenByDr.
	 * @param seenByDr the value to which this Visit's seen by 
	 * doctor status is set to.
	 */
	@SuppressLint("SimpleDateFormat")
	public void setSeenByDr (Boolean seenByDr){
		this.seenByDr = seenByDr;
		if (seenByDr == true){
			this.timeSeenByDr = new SimpleDateFormat("MM-dd-HH-mm").
					format(Calendar.getInstance().getTime());
		}
	}
	
	/**
	 * Sets the time at which the Patient was seen by a doctor
	 * during this visit to timeSeenByDr.
	 * @param timeSeenByDr the time at which the Patient was seen by a doctor
	 * during this visit.
	 */
	public void setTimeSeenByDr(String timeSeenByDr){
		this.timeSeenByDr = timeSeenByDr;
	}
	
	/**
	 * Calculates the urgency level (based on the hospital policy) of the
	 * patient with age age, temperature temperature, systolic blood pressure
	 * systolicBloodPressure, diastolic blood pressure diastolicBloodPressure
	 * and heart rate heartRate.
	 * @param age the patient's age.
	 * @param temperature the patient's temperature.
	 * @param systolicBloodPressure the patient's systolic blood pressure.
	 * @param diastolicBloodPressure the patient's diastolic blood pressure.
	 * @param heartRate the patient's heart rate.
	 * @return urgency level of the Patient with the given medical information.
	 */
	public int calculateUrgency(Integer age, Double temperature, 
			Double systolicBloodPressure, Double diastolicBloodPressure, 
			Double heartRate){
		int urgencyLevel = 0;
		if (age < 2){
			urgencyLevel += 1;
		}
		if (temperature >= 39 ){
			urgencyLevel += 1;
		}	
		if (systolicBloodPressure >= 140 || diastolicBloodPressure >= 90){
			urgencyLevel += 1;
		}
		if (heartRate >= 100 || heartRate <= 50){
			urgencyLevel += 1;
		}
		
		this.setUrgency(urgencyLevel);
		return urgencyLevel;
	}

	/**
	 * returns this Visit's seen by doctor status.
	 * @return this Visit's seen by doctor status.
	 */
	public Boolean getSeenByDr() {
		return seenByDr;
	}
	
	/**
	 * returns the time at which the Patient was seen by a doctor during this 
	 * Visit.
	 * @return the time at which the Patient was seen by a doctor during this 
	 * Visit.
	 */
	public String getTimeSeenByDr() {
		return timeSeenByDr;
	}

	/**
	 * returns the Patient's urgency level during this Visit.
	 * @return the Patient's urgency level during this Visit.
	 */
	public int getUrgency() {
		return urgency;
	}
	
	/**
	 * sets the Patient's urgency level during this Visit to urgencyLevel.
	 * @param urgencyLevel the value to which the Patient's urgency level 
	 * during this Visit is set.
	 */
	public void setUrgency(Integer urgencyLevel){
		this.urgency = urgencyLevel;
	}
	
	/**
	 * returns a string representation of this Visit.
	 */
	@ Override
	public String toString(){
		String str = "@" + seenByDr.toString() + "@" 
				+ timeSeenByDr + 
				"@" + urgency + "@" + "\\";
		return str;
	}
	
	/**
	 * Sets the Patient's seen by doctor status during this visit to seenByDr
	 * and the time at which he/she was seen by a doctor to timeSeenByDr.
	 * @param seenByDr the value to which the Patient's seen by doctor status
	 * during this visit is set to.
	 * @param timeSeenByDr the time at which the Patient was seen by 
	 * a doctor during this Visit.
	 */
	public void changeSeenByDr(Boolean seenByDr, String timeSeenByDr){
		this.seenByDr = seenByDr;
		this.timeSeenByDr = timeSeenByDr;
	}

}
