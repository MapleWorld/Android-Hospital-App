package com.example.triage;

import java.io.FileNotFoundException;

import com.example.triagetwo.R;

import HospitalStuff.Login;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A class that implements the functionality for the first 
 * activity of the application.
 * @author Ou Ye, Lindsey Poad, Marina Tawfik , Dazhen Wang
 */
public class MainActivity extends Activity {
	
	/** The name of the file containing nurses' and doctors' 
	 * passwords.	 
	 */
	public static final String FILENAME = "passwords.txt"; 

	/** 
	 * Auto generated: sets this Activity when created.
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Transition valid user (with a valid user name and password) 
	 * to DisplayActivity.
	 * Check and validates login information.
	 * @param v View
	 */
	public void loginPerson(View v) throws FileNotFoundException{

		EditText userNameText = (EditText) findViewById(R.id.user_name);
		EditText userPasswordText = (EditText) findViewById(
				R.id.user_password);

		String userName = userNameText.getText().toString();
		String userPassword = userPasswordText.getText().toString();
		Login login = new Login();
		String loginUserType = login.readFileCheckLoginInfo(
				this.getApplicationContext().getFilesDir(), 
				FILENAME, userName, userPassword);
		
		if (loginUserType != "false"){
			Intent intent = new Intent(this, DisplayActivity.class);
			intent.putExtra("userType", loginUserType);
			startActivity(intent);
		}
		else{
			userNameText.setText("");
			userPasswordText.setText("");
			Toast msg =  Toast.makeText(this,"Incorrect Username or Password", 
					Toast.LENGTH_LONG );
			msg.show();
		}
	}
}