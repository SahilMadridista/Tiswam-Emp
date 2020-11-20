package com.example.tiswamemp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiswamemp.BDMDeals.BDMDoneDeals;
import com.example.tiswamemp.Profiles.BDMProfileActivity;
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

public class BDMHomePage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

   FirebaseAuth firebaseAuth;
   androidx.appcompat.widget.Toolbar toolbar;
   FirebaseFirestore firebaseFirestore;
   RelativeLayout LoadingLayout,NoResultLayout, Parent;
   String name;
   TextView DateText;

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

      DateText = findViewById(R.id.today_date);
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
               name = documentSnapshot.getString("name");
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

            startActivity(new Intent(getApplicationContext(), BDMProfileActivity.class));
            break;

         case R.id.schedule:

            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");

            break;

         case R.id.show_today_meetings:

            Calendar calendar = Calendar.getInstance();
            final String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

            DateText = findViewById(R.id.today_date);
            DateText.setText(currentDate);

            showTodayMeetings(currentDate,name);

            break;

         case R.id.done_deals:

            startActivity(new Intent(getApplicationContext(), BDMDoneDeals.class));
            break;

         case R.id.signout:

            // ---------------  This is for sending data to bottom sheet ( next 6 lines ) --------------------- //

            /*SignOutBottomSheetDialog bottomSheet = new SignOutBottomSheetDialog();
            Bundle bundle = new Bundle();
            String msg = "Football is life";
            bundle.putString("message", msg );
            bottomSheet.setArguments(bundle);
            bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");*/


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

   private void showTodayMeetings(final String currentDate, String name) {

      CollectionReference collectionReference = firebaseFirestore.collection("Leads");
      Query query = collectionReference.whereEqualTo("deal_status","pending")
              .whereEqualTo("bdm",name).whereEqualTo("meeting_date",currentDate);

      query.addSnapshotListener(new EventListener<QuerySnapshot>() {
         @Override
         public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

            assert queryDocumentSnapshots != null;

            if(queryDocumentSnapshots.size() == 0){

               NoResultLayout.setVisibility(View.VISIBLE);
               String snack = "You don't have any meetings for " + currentDate;
               showSnackBar(snack);

            }

            else{

               String snack = "You have " + queryDocumentSnapshots.size() + " meetings for " + currentDate;
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

   @Override
   public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
      Calendar c = Calendar.getInstance();
      c.set(Calendar.YEAR, year);
      c.set(Calendar.MONTH, month);
      c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
      String date = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
      DateText.setText(date);
      showTodayMeetings(date,name);

   }

}