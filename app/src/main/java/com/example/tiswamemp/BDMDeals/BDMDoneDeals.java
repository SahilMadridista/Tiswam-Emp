package com.example.tiswamemp.BDMDeals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.tiswamemp.BDEAssignedBDM.AssignedBDMAdapter;
import com.example.tiswamemp.DatePickerFragment;
import com.example.tiswamemp.Lead;
import com.example.tiswamemp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

public class BDMDoneDeals extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    androidx.appcompat.widget.Toolbar toolbar;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_d_m_done_deals);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.done_deals_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        fetchName();

    }

    private void fetchName() {

        assert firebaseAuth.getCurrentUser() != null;

        String id = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("Employees").document(id).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        assert documentSnapshot != null;

                        name = documentSnapshot.getString("name");
                        setUpRecyclerview(name);


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setUpRecyclerview(String name){

        RecyclerView recyclerView = findViewById(R.id.done_deals_r_view);
        CollectionReference collectionReference = firebaseFirestore.collection("Leads");

        Query query = collectionReference.whereEqualTo("bdm",name).whereEqualTo("deal_status","done")
                .orderBy("name", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Lead> options = new FirestoreRecyclerOptions.Builder<Lead>()
                .setQuery(query, Lead.class)
                .build();

        DoneDealsAdapter assignedBDMAdapter = new DoneDealsAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(assignedBDMAdapter);
        assignedBDMAdapter.startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.select_date_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.choose_date:

                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");

                break;

            case R.id.clear_date:

                setUpRecyclerview(name);
                Toast.makeText(getApplicationContext(),"Date filter cleared",Toast.LENGTH_SHORT).show();
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
        toolbar.setTitle(date);
        showMeetingsOfDate(name,date);


    }

    private void showMeetingsOfDate(String name, String date) {

        RecyclerView recyclerView = findViewById(R.id.done_deals_r_view);
        CollectionReference collectionReference = firebaseFirestore.collection("Leads");

        Query query = collectionReference.whereEqualTo("bdm",name).whereEqualTo("meeting_date",date)
                .whereEqualTo("deal_status","done")
                .orderBy("name", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Lead> options = new FirestoreRecyclerOptions.Builder<Lead>()
                .setQuery(query, Lead.class)
                .build();

        DoneDealsAdapter assignedBDMAdapter = new DoneDealsAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(assignedBDMAdapter);
        assignedBDMAdapter.startListening();

    }


}