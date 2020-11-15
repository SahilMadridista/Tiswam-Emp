package com.example.tiswamemp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tiswamemp.Test.Test;
import com.example.tiswamemp.Test.TestAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

   FirebaseFirestore firebaseFirestore;
   Button button;
   ArrayList<String> list = new ArrayList<>();

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_test);

      button = findViewById(R.id.btn);

      firebaseFirestore = FirebaseFirestore.getInstance();
      CollectionReference collectionReference = firebaseFirestore.collection("Development");
      Query query = collectionReference.orderBy("name", Query.Direction.ASCENDING);

      FirestoreRecyclerOptions<Test> options = new FirestoreRecyclerOptions.Builder<Test>()
              .setQuery(query, Test.class)
              .build();

      final TestAdapter testAdapter = new TestAdapter(options);

      RecyclerView recyclerView = findViewById(R.id.test_recyclerview);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      recyclerView.setAdapter(testAdapter);
      testAdapter.startListening();


   }
}