package com.example.tiswamemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tiswamemp.BDEAssignedBDM.BDEAssignedBDMActivity;
import com.example.tiswamemp.Profiles.BDEProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BDEHomePage extends AppCompatActivity {

   private FirebaseAuth firebaseAuth;
   private FirebaseFirestore firebaseFirestore;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_b_d_e_home_page);

      firebaseAuth = FirebaseAuth.getInstance();
      firebaseFirestore = FirebaseFirestore.getInstance();

      androidx.appcompat.widget.Toolbar toolbar;
      toolbar = findViewById(R.id.bde_homepage_toolbar);
      setSupportActionBar(toolbar);

      final Button NewLeadButton = findViewById(R.id.new_lead_button);
      NewLeadButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

            /*assert firebaseAuth.getCurrentUser() != null;
            String userID = firebaseAuth.getCurrentUser().getUid();
            firebaseFirestore.collection("Employees").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
               @Override
               public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                  if(task.isSuccessful()){

                     DocumentSnapshot documentSnapshot = task.getResult();
                     assert documentSnapshot != null;
                     String name = documentSnapshot.getString("name");
                     String phone = documentSnapshot.getString("phone");
                     String email = documentSnapshot.getString("email");

                     Intent intent = new Intent(getApplicationContext(),NewLeadActivity.class);
                     intent.putExtra("name",name);
                     intent.putExtra("email",email);
                     intent.putExtra("phone",phone);
                     startActivity(intent);

                  }
               }
            });*/

            startActivity(new Intent(getApplicationContext(),NewLeadActivity.class));

         }
      });

      setUpRecyclerView();

   }

   private void setUpRecyclerView() {




   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.bde_menu,menu);
      return super.onCreateOptionsMenu(menu);
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {

      switch (item.getItemId()) {

         case R.id.profile:

            startActivity(new Intent(getApplicationContext(), BDEProfileActivity.class));
            break;

         case R.id.lead:

            startActivity(new Intent(getApplicationContext(), BDEAssignedBDMActivity.class));
            break;

         case R.id.sign_out:

            SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("login", SharedPrefConsts.NO_LOGIN);
            editor.apply();

            firebaseAuth.signOut();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();


         default:
            return super.onOptionsItemSelected(item);

      }

      return true;
   }


}