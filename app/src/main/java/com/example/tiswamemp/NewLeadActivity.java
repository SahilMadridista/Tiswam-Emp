package com.example.tiswamemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.DateFormat;
import java.util.Calendar;

public class NewLeadActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

   String date="",time="";
   String name,phone,email;
   Button DateButton,TimeButton,DoneButton;
   private FirebaseFirestore firebaseFirestore;
   private FirebaseAuth firebaseAuth;
   EditText LeadName,LeadEmail,LeadPhone,LeadBusinessName,LeadBusinessAddress;
   ProgressDialog progressDialog;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_new_lead);

      firebaseAuth = FirebaseAuth.getInstance();
      firebaseFirestore = FirebaseFirestore.getInstance();

      assert firebaseAuth.getCurrentUser() != null;
      String userID = firebaseAuth.getCurrentUser().getUid();
      firebaseFirestore.collection("Employees").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
         @Override
         public void onComplete(@NonNull Task<DocumentSnapshot> task) {

            if(task.isSuccessful()){

               DocumentSnapshot documentSnapshot = task.getResult();
               assert documentSnapshot != null;
               name = documentSnapshot.getString("name");
               phone = documentSnapshot.getString("phone");
               email = documentSnapshot.getString("email");


            }
         }
      });


      /*final int year = calendar.get(Calendar.YEAR);
      final int month = calendar.get(Calendar.MONTH);
      final int day = calendar.get(Calendar.DAY_OF_MONTH);*/

      DateButton = findViewById(R.id.meeting_date_btn);
      DateButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");

            /*DatePickerDialog datePickerDialog = new DatePickerDialog(NewLeadActivity.this, new DatePickerDialog.OnDateSetListener() {
               @Override
               public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                  date = i2 + " / " + i1 + " / " + i;
                  DateButton.setText(date);
               }
            },year,month,day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();*/

         }

      });

      TimeButton = findViewById(R.id.meeting_time_btn);
      TimeButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "time picker");
         }
      });

      LeadName = findViewById(R.id.lead_name_et);
      LeadEmail = findViewById(R.id.lead_email_et);
      LeadPhone = findViewById(R.id.lead_phone_et);
      LeadBusinessName = findViewById(R.id.lead_business_et);
      LeadBusinessAddress = findViewById(R.id.lead_address_et);

      DoneButton = findViewById(R.id.done_btn);
      DoneButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            checkDetails();
         }
      });

      progressDialog = new ProgressDialog(this);
      progressDialog.setMessage("Creating new lead...");


   }

   private void checkDetails() {

      if(LeadName.getText().toString().trim().isEmpty()){
         LeadName.setError("Please enter name");
         LeadName.requestFocus();
         return;
      }

      if(LeadEmail.getText().toString().trim().isEmpty()){
         LeadEmail.setError("Please enter email");
         LeadEmail.requestFocus();
         return;
      }

      if(!Patterns.EMAIL_ADDRESS.matcher(LeadEmail.getText().toString().trim()).matches()){
         LeadEmail.setError("Please enter a valid email address");
         LeadEmail.requestFocus();
         return;
      }

      if(LeadPhone.getText().toString().trim().isEmpty()){
         LeadPhone.setError("Please enter phone number");
         LeadPhone.requestFocus();
         return;
      }

      if(LeadPhone.getText().toString().trim().length() != 10){
         LeadPhone.setError("Please enter valid phone number");
         LeadPhone.requestFocus();
         return;
      }

      if(LeadBusinessName.getText().toString().trim().isEmpty()){
         LeadBusinessName.setError("Please enter business name");
         LeadBusinessName.requestFocus();
         return;
      }

      if(LeadBusinessAddress.getText().toString().trim().isEmpty()){
         LeadBusinessAddress.setError("Please enter business name");
         LeadBusinessAddress.requestFocus();
         return;
      }

      if(date.isEmpty()){
         Toast.makeText(getApplicationContext(),"Please select meeting date.",Toast.LENGTH_SHORT).show();
         return;
      }

      if(time.isEmpty()){
         Toast.makeText(getApplicationContext(),"Please select meeting time.",Toast.LENGTH_SHORT).show();
         return;
      }

      storeDetails();


   }

   private void storeDetails() {

      progressDialog.show();

      Lead lead = new Lead();

      lead.name = LeadName.getText().toString().trim();
      lead.email = LeadEmail.getText().toString().trim();
      lead.phone = LeadPhone.getText().toString().trim();
      lead.business_name = LeadBusinessName.getText().toString().trim();
      lead.business_address = LeadBusinessAddress.getText().toString().trim();
      lead.bde_name = name;
      lead.bde_email = email;
      lead.bde_phone = phone;
      lead.meeting_date = date;
      lead.meeting_time = time;
      lead.bdm = "no";
      lead.bdm_assigned_status = "no";

      firebaseFirestore.collection("Leads").document()
              .set(lead).addOnSuccessListener(new OnSuccessListener<Void>() {
         @Override
         public void onSuccess(Void aVoid) {

            Toast.makeText(getApplicationContext(),"New lead created.",
                    Toast.LENGTH_SHORT).show();
            clearEditText();
            progressDialog.dismiss();

         }
      }).addOnFailureListener(new OnFailureListener() {
         @Override
         public void onFailure(@NonNull Exception e) {

            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();

         }
      });


   }

   private void clearEditText() {

      LeadPhone.setText(null);
      LeadEmail.setText(null);
      LeadBusinessName.setText(null);
      LeadBusinessAddress.setText(null);
      DateButton.setText(R.string.meeting_date);
      TimeButton.setText(R.string.meeting_time);
      LeadName.setText(null);


   }


   @Override
   public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
      Calendar c = Calendar.getInstance();
      c.set(Calendar.YEAR, year);
      c.set(Calendar.MONTH, month);
      c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
      date = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
      DateButton.setText(date);
   }

   @Override
   public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
      time = hourOfDay + " : " + minute;
      TimeButton.setText(time);
   }


}