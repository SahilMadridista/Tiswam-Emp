package com.example.tiswamemp.Test;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class TestAdapter extends FirestoreRecyclerAdapter<Test,TestAdapter.TestViewHolder> {

   public TestAdapter(@NonNull FirestoreRecyclerOptions<Test> options) {
      super(options);
   }

   @Override
   protected void onBindViewHolder(@NonNull TestViewHolder holder, int position, @NonNull Test model) {

   }

   @NonNull
   @Override
   public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      return null;
   }

   static class TestViewHolder extends RecyclerView.ViewHolder{


      public TestViewHolder(@NonNull View itemView) {
         super(itemView);
      }
   }

}
