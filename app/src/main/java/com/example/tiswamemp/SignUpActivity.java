package com.example.tiswamemp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

public class SignUpActivity extends AppCompatActivity {

   Spinner spinner;
   EditText Name,Phone,Email,Password;
   RelativeLayout Parent;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_sign_up);

      spinner = findViewById(R.id.post_spinner);
      ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
              R.array.posts, R.layout.spinner_item);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spinner.setAdapter(adapter);

      Name = findViewById(R.id.name_et);
      Phone = findViewById(R.id.phone_et);
      Email = findViewById(R.id.email_et);
      Password = findViewById(R.id.password_et);
      Parent = findViewById(R.id.parent_layout);

      Button Next = findViewById(R.id.next_btn);
      Next.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            checkDetails();
         }
      });



   }

   private void checkDetails() {

      if(Name.getText().toString().isEmpty()){
         String snack = "Name can't be empty.";
         showSnack(snack);
         return;
      }

      if(Phone.getText().toString().isEmpty()){
         String snack = "Phone number can't be empty.";
         showSnack(snack);
         return;
      }

      if(Phone.getText().toString().length() != 10){
         String snack = "Please enter a 10 digit phone number.";
         showSnack(snack);
         return;
      }

      if(Email.getText().toString().isEmpty()){
         String snack = "Email address can't be empty.";
         showSnack(snack);
         return;
      }

      if(!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString().trim()).matches()){
         String snack = "Please enter a valid email address";
         showSnack(snack);
         return;
      }

      if(Password.getText().toString().isEmpty()){
         String snack = "Password can't be empty.";
         showSnack(snack);
         return;
      }

      if(Password.getText().toString().length() < 6){
         String snack = "Password must have at least 6 characters.";
         showSnack(snack);
         return;
      }

      Intent intent = new Intent(getApplicationContext(),ProfilePhotoActivity.class);
      intent.putExtra("name",Name.getText().toString().trim());
      intent.putExtra("phone",Phone.getText().toString().trim());
      intent.putExtra("email",Email.getText().toString().trim());
      intent.putExtra("password",Password.getText().toString().trim());
      intent.putExtra("team",spinner.getSelectedItem().toString().trim());
      startActivity(intent);


   }

   private void showSnack(String snack){
      Snackbar snackbar = Snackbar.make(Parent,snack,Snackbar.LENGTH_LONG);
      snackbar.show();
   }

}