package com.example.tiswamemp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class BDMHomePage extends AppCompatActivity {

   FirebaseAuth firebaseAuth;
   androidx.appcompat.widget.Toolbar toolbar;
   FirebaseFirestore firebaseFirestore;
   RelativeLayout LoadingLayout,NoResultLayout, Parent;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_b_d_m_home_page);

      firebaseAuth = FirebaseAuth.getInstance();
      firebaseFirestore = FirebaseFirestore.getInstance();
      toolbar = findViewById(R.id.bdm_homepage_toolbar);
      setSupportActionBar(toolbar);

      LoadingLayout = findViewById(R.id.loading_layout);
      NoResultLayout = findViewById(R.id.empty_layout);
      Parent = findViewById(R.id.parent);

      Calendar calendar = Calendar.getInstance();
      final String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
      TextView DateText = findViewById(R.id.today_date);
      DateText.setText(currentDate);

      assert firebaseAuth.getCurrentUser() != null;
      String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

      DocumentReference documentReference = firebaseFirestore.collection("Employees")
              .document(userID);

      documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
         @Override
         public void onComplete(@NonNull Task<DocumentSnapshot> task) {

            if(task.isSuccessful()){
               DocumentSnapshot documentSnapshot = task.getResult();
               assert documentSnapshot != null;
               String name = documentSnapshot.getString("name");
               showTodayMeetings(currentDate,name);

            }else{
               Toast.makeText(getApplicationContext(),
                       Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
            }

         }
      });

   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.bdm_menu,menu);
      return super.onCreateOptionsMenu(menu);
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {

      switch (item.getItemId()) {

         case R.id.bdm_profile:

            Toast.makeText(getApplicationContext(),"Profile button clicked",Toast.LENGTH_SHORT).show();
            break;

         case R.id.schedule:

            Toast.makeText(getApplicationContext(),"Calender button clicked",Toast.LENGTH_SHORT).show();
            break;

         case R.id.done_deals:

            Toast.makeText(getApplicationContext(),"Done deals button clicked",Toast.LENGTH_SHORT).show();
            break;

         case R.id.signout:

            SignOutBottomSheetDialog bottomSheet = new SignOutBottomSheetDialog();
            bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");

         default:
            return super.onOptionsItemSelected(item);

      }

      return true;
   }

   private void showTodayMeetings(String currentDate, String name) {

      CollectionReference collectionReference = firebaseFirestore.collection("Leads");
      Query query = collectionReference.whereEqualTo("deal_status","pending")
              .whereEqualTo("bdm",name).whereEqualTo("meeting_date",currentDate);

      query.addSnapshotListener(new EventListener<QuerySnapshot>() {
         @Override
         public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

            assert queryDocumentSnapshots != null;

            if(queryDocumentSnapshots.size() == 0){

               NoResultLayout.setVisibility(View.VISIBLE);
               String snack = "You don't have any meetings for today yet.";
               showSnackBar(snack);

            }

            else{

               String snack = "You have " + queryDocumentSnapshots.size() + " meetings for today.";
               showSnackBar(snack);

            }

            LoadingLayout.setVisibility(View.GONE);


         }
      });

      FirestoreRecyclerOptions<Lead> options = new FirestoreRecyclerOptions.Builder<Lead>()
              .setQuery(query, Lead.class)
              .build();

      BDMPendingMeetingsAdapter notAssignedBDMAdapter = new BDMPendingMeetingsAdapter(options);

      RecyclerView recyclerView = findViewById(R.id.today_meetings_recyclerview);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      recyclerView.setAdapter(notAssignedBDMAdapter);
      notAssignedBDMAdapter.startListening();


   }

   private void showSnackBar(String snack) {

      Snackbar snackbar = Snackbar.make(Parent,snack,Snackbar.LENGTH_LONG);
      snackbar.show();

   }

}