package com.example.tiswamemp.BDEAssignedBDM;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.tiswamemp.DatePickerFragment;
import com.example.tiswamemp.Lead;
import com.example.tiswamemp.Profiles.BDMProfileActivity;
import com.example.tiswamemp.R;
import com.example.tiswamemp.SignOutBottomSheetDialog;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.Calendar;

public class BDEAssignedBDMActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

   androidx.appcompat.widget.Toolbar toolbar;
   FirebaseAuth firebaseAuth;
   FirebaseFirestore firebaseFirestore;
   LottieAnimationView Loading, Empty;
   String name;
   TextView HeadText;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_b_d_e_assigned_b_d_m);

      firebaseAuth = FirebaseAuth.getInstance();
      firebaseFirestore = FirebaseFirestore.getInstance();

      Loading = findViewById(R.id.anim);

      // Empty = findViewById(R.id.anim2);

      toolbar = findViewById(R.id.head_text);
      setSupportActionBar(toolbar);

      fetchBDEName();

      HeadText = findViewById(R.id.head);


   }

   private void fetchBDEName() {

      assert firebaseAuth.getCurrentUser() != null;

      String id = firebaseAuth.getCurrentUser().getUid();
      firebaseFirestore.collection("Employees").document(id).get()
              .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

         @Override
         public void onSuccess(DocumentSnapshot documentSnapshot) {

            assert documentSnapshot != null;

            name = documentSnapshot.getString("name");
            showAllLeads(name);


         }
      }).addOnFailureListener(new OnFailureListener() {
         @Override
         public void onFailure(@NonNull Exception e) {

            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

         }
      });

   }

   private void showAllLeads(String name) {

      RecyclerView recyclerView = findViewById(R.id.r_view);

      CollectionReference collectionReference = firebaseFirestore.collection("Leads");

      Query query = collectionReference.whereEqualTo("bde_name",name).whereEqualTo("bdm_assigned_status","yes")
              .orderBy("name", Query.Direction.ASCENDING);

      FirestoreRecyclerOptions<Lead> options = new FirestoreRecyclerOptions.Builder<Lead>()
              .setQuery(query, Lead.class)
              .build();

      AssignedBDMAdapter assignedBDMAdapter = new AssignedBDMAdapter(options);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      recyclerView.setAdapter(assignedBDMAdapter);
      assignedBDMAdapter.startListening();
      HeadText.setVisibility(View.VISIBLE);
      Loading.setVisibility(View.GONE);
      String head = "Showing all leads";
      HeadText.setText(head);


   }

   private void showDoneDeals(String name){

      RecyclerView recyclerView = findViewById(R.id.r_view);

      CollectionReference collectionReference = firebaseFirestore.collection("Leads");

      Query query = collectionReference.whereEqualTo("bde_name",name).whereEqualTo("bdm_assigned_status","yes")
              .whereEqualTo("deal_status","done");

      FirestoreRecyclerOptions<Lead> options = new FirestoreRecyclerOptions.Builder<Lead>()
              .setQuery(query, Lead.class)
              .build();

      AssignedBDMAdapter assignedBDMAdapter = new AssignedBDMAdapter(options);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      recyclerView.setAdapter(assignedBDMAdapter);
      assignedBDMAdapter.startListening();

      String head = "Showing all closed deals";
      HeadText.setText(head);


   }

   private void closedDealsWithDate(String name,String date){

      RecyclerView recyclerView = findViewById(R.id.r_view);

      CollectionReference collectionReference = firebaseFirestore.collection("Leads");

      Query query = collectionReference.whereEqualTo("bde_name",name).whereEqualTo("bdm_assigned_status","yes")
              .whereEqualTo("deal_status","done").whereEqualTo("meeting_date",date);

      FirestoreRecyclerOptions<Lead> options = new FirestoreRecyclerOptions.Builder<Lead>()
              .setQuery(query, Lead.class)
              .build();

      AssignedBDMAdapter assignedBDMAdapter = new AssignedBDMAdapter(options);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      recyclerView.setAdapter(assignedBDMAdapter);
      assignedBDMAdapter.startListening();

   }

   private void showPendingDeals(String name){

      RecyclerView recyclerView = findViewById(R.id.r_view);

      CollectionReference collectionReference = firebaseFirestore.collection("Leads");

      Query query = collectionReference.whereEqualTo("bde_name",name).whereEqualTo("bdm_assigned_status","yes")
              .whereEqualTo("deal_status","pending");

      FirestoreRecyclerOptions<Lead> options = new FirestoreRecyclerOptions.Builder<Lead>()
              .setQuery(query, Lead.class)
              .build();

      AssignedBDMAdapter assignedBDMAdapter = new AssignedBDMAdapter(options);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      recyclerView.setAdapter(assignedBDMAdapter);
      assignedBDMAdapter.startListening();

      String head = "Showing all pending leads";
      HeadText.setText(head);


   }


   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.assigned_bdm_menu,menu);
      return super.onCreateOptionsMenu(menu);
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {

      switch (item.getItemId()) {

         case R.id.show_all_leads:

            showAllLeads(name);
            Toast.makeText(getApplicationContext(),"Showing all leads",Toast.LENGTH_SHORT).show();
            break;

         case R.id.show_done_deals:

            showDoneDeals(name);
            Toast.makeText(getApplicationContext(),"Showing closed deals",Toast.LENGTH_SHORT).show();
            break;

         case R.id.show_done_deals_with_date:

            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
            break;

         case R.id.show_pending_deals:

            showPendingDeals(name);
            Toast.makeText(getApplicationContext(),"Showing pending deals",Toast.LENGTH_SHORT).show();
            break;

         default:
            return super.onOptionsItemSelected(item);

      }

      return true;

   }

   @Override
   public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
      Calendar c = Calendar.getInstance();
      c.set(Calendar.YEAR, year);
      c.set(Calendar.MONTH, month);
      c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
      String date = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());


      String head = "Showing all closed deals of " + date;
      HeadText.setText(head);

      closedDealsWithDate(name,date);

   }

}