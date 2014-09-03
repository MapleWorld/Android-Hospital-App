Our Android Application Project is located in the Triage folder.
Import the whole Triage folder into eclipse and launch it with emulator.
Then go to Window -> Show View -> Other -> File explorer 
and drag the passwords.txt file under data/data/...../file
or add it using adb shell 
adb push passwords data/data/...../file

The first Interface is the Login interface. 
Login with the username and password in the passwords.txt (Case Sensitive),
You will then be taken to the second interface (Display).
In the Display Interface, there are Four buttons and one text field.
You can either Register a New Patient or Find an existing patient 
in the database by typing in their Health Card Number. 
You can also check the patient list that have not seen by the doctor if you are a nurse.
Register New Patient: Takes you to the register interface where you have to
type in the information about the new patient, such as name, health card number, and birthday(Can't be age 0).
After this, you will be taken to the Vital Signs Interface to record your vital signs.

Finally, you will end up in the Patient Status Interface, and you can either 
update patient's vital signs again, return to Display Interface, 
view patient history, or confirm this patient already seen the doctor.
You can also write prescription to the patient if you login as a physician.

Find Patient By Health Card Number: 
It will take you directly to the Patient Status Page. 
You can only do four things in this interface - 
Update Patient Vital Sign if you login as a nurse
You can view this patient past history record
You can return back to the Display Interface.
Or write prescription to the patient if you login as a physician.


Bonus:
- We have created a customized logo and layout design to make the app more 
u
ser-friendly. 
- We have also included input validation, with messages that
are displayed 
when invalid input is entered (using the Toast class).
- We also used the List view class to display data.