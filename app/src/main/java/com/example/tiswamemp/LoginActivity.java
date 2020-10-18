package com.example.tiswamemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

   EditText EmailET,PasswordET;
   private FirebaseAuth firebaseAuth;
   Button LoginButton;
   RelativeLayout Parent,MainLayout,LoadingLayout;
   ProgressBar progressBar;
   private FirebaseFirestore firebaseFirestore;
   TextView loading;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_login);

      firebaseAuth = FirebaseAuth.getInstance();
      firebaseFirestore = FirebaseFirestore.getInstance();

      EmailET = findViewById(R.id.login_email);
      PasswordET = findViewById(R.id.login_password);
      LoginButton = findViewById(R.id.login_button);
      Parent = findViewById(R.id.root_layout);
      MainLayout = findViewById(R.id.main_layout);
      LoadingLayout = findViewById(R.id.loading_layout);
      progressBar = findViewById(R.id.progress_bar);
      loading = findViewById(R.id.load);

      LoginButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            checkDetails();
         }
      });

      CheckBox ShowPass = findViewById(R.id.show_pass_check);

      ShowPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
               PasswordET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
               PasswordET.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
         }
      });



   }

   private void checkDetails() {

      if(EmailET.getText().toString().isEmpty()){
         String snack = "Please enter email address.";
         showSnack(snack);
         return;
      }

      if(!Patterns.EMAIL_ADDRESS.matcher(EmailET.getText().toString().trim()).matches()){
         String snack = "Please enter a valid email address";
         showSnack(snack);
         return;
      }

      if(PasswordET.getText().toString().isEmpty()){
         String snack = "Please enter password.";
         showSnack(snack);
         return;
      }

      loginTheUser();

   }

   private void loginTheUser() {

      disableWidgets();

      firebaseAuth.signInWithEmailAndPassword(EmailET.getText().toString().trim(),PasswordET.getText().toString().trim())
              .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){

                       if(Objects.requireNonNull(firebaseAuth.getCurrentUser()).isEmailVerified()){

                          Toast.makeText(LoginActivity.this,"You are logged in",
                                  Toast.LENGTH_LONG).show();
                          enableWidgets();
                          checkPost();

                       }
                       else{
                          Toast.makeText(LoginActivity.this,"Please verify your email first. You can login after verification.",
                                  Toast.LENGTH_LONG).show();
                          enableWidgets();
                       }
                    }else {
                       Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage(),
                               Toast.LENGTH_LONG).show();
                       enableWidgets();
                    }
                 }
              });

   }

   private void checkPost() {

      MainLayout.setVisibility(View.GONE);
      LoadingLayout.setVisibility(View.VISIBLE);

      assert firebaseAuth.getCurrentUser() != null;
      String userID = firebaseAuth.getCurrentUser().getUid();

      firebaseFirestore.collection("Employees").document(userID)
              .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
         @Override
         public void onComplete(@NonNull Task<DocumentSnapshot> task) {

            if(task.isSuccessful()){

               DocumentSnapshot documentSnapshot = task.getResult();
               assert documentSnapshot != null;
               String post = documentSnapshot.getString("team");
               assert post != null;
               openActivity(post);

            }
         }
      });

   }

   private void openActivity(String post) {

      String[] posts = getResources().getStringArray(R.array.posts);
      assert post != null;

      if(post.equals(posts[1])){

         SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
         SharedPreferences.Editor editor = preferences.edit();
         editor.putInt("login", SharedPrefConsts.BDE_LOGIN);
         editor.apply();
         startActivity(new Intent(getApplicationContext(),BDEHomePage.class));
         finish();

      }

      if(post.equals(posts[2])){

         SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
         SharedPreferences.Editor editor = preferences.edit();
         editor.putInt("login", SharedPrefConsts.BDM_LOGIN);
         editor.apply();
         startActivity(new Intent(getApplicationContext(),BDMHomePage.class));
         finish();

      }

      if(post.equals(posts[3])){

         SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
         SharedPreferences.Editor editor = preferences.edit();
         editor.putInt("login", SharedPrefConsts.IT_LOGIN);
         editor.apply();
         startActivity(new Intent(getApplicationContext(),ITHomePage.class));
         finish();

      }

   }

   private void disableWidgets() {

      progressBar.setVisibility(View.VISIBLE);
      LoginButton.setText(R.string.logging);
      EmailET.setEnabled(false);
      PasswordET.setEnabled(false);
      LoginButton.setEnabled(false);

   }

   private void enableWidgets() {

      progressBar.setVisibility(View.GONE);
      LoginButton.setText(R.string.login);
      EmailET.setEnabled(true);
      PasswordET.setEnabled(true);
      LoginButton.setEnabled(true);

   }




   private void showSnack(String snack) {
      Snackbar snackbar = Snackbar.make(Parent,snack,Snackbar.LENGTH_LONG);
      snackbar.show();
   }

}