package com.example.tiswamemp.Test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tiswamemp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class TestAdapter extends FirestoreRecyclerAdapter<Test,TestAdapter.TestViewHolder> {

   Context context;

   public TestAdapter(@NonNull FirestoreRecyclerOptions<Test> options) {
      super(options);
   }

   @Override
   protected void onBindViewHolder(@NonNull TestViewHolder holder, int position, @NonNull Test model) {

   }

   @NonNull
   @Override
   public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_single_item,
              parent, false);
      context = v.getContext();
      return new TestViewHolder(v);
   }

   static class TestViewHolder extends RecyclerView.ViewHolder{

      TextView ServiceName;
      CheckBox checkbox;

      public TestViewHolder(@NonNull View itemView) {
         super(itemView);

         ServiceName = itemView.findViewById(R.id.service_text);
         checkbox = itemView.findViewById(R.id.check_box);

      }
   }

}
