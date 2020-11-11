package com.example.tiswamemp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class BDMPendingMeetingsAdapter extends FirestoreRecyclerAdapter<Lead,BDMPendingMeetingsAdapter.BDMPendingMeetingsViewHolder> {

   Context context;

   public BDMPendingMeetingsAdapter(@NonNull FirestoreRecyclerOptions<Lead> options) {
      super(options);
   }

   @Override
   protected void onBindViewHolder(@NonNull final BDMPendingMeetingsViewHolder holder, int position, @NonNull Lead model) {


      final int REQUEST_PHONE_CALL = 1;

      holder.OrganizationName.setText(model.getBusiness_name());
      holder.OrganizationAddress.setText(model.getBusiness_address());
      holder.LeadName.setText(model.getName());
      holder.MeetingTime.setText(model.getMeeting_time());

      final String phone = model.getPhone();

      holder.Call.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
               ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
            }
            else
            {
               context.startActivity(intent);
            }

         }
      });

      holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

            DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());

            String id = snapshot.getId();
            String org = snapshot.getString("business_name");
            String address = snapshot.getString("business_address");
            String name = snapshot.getString("name");
            String phone = snapshot.getString("phone");
            String email = snapshot.getString("email");
            String date = snapshot.getString("meeting_date");
            String time = snapshot.getString("meeting_time");
            String bde_name = snapshot.getString("bde_name");
            String bde_email = snapshot.getString("bde_email");
            String bde_phone = snapshot.getString("bde_phone");
            String bdm = snapshot.getString("bdm");

            Intent i = new Intent(context,BDMMeetingDetailsActivity.class);

            i.putExtra("id",id);
            i.putExtra("org",org);
            i.putExtra("address",address);
            i.putExtra("name",name);
            i.putExtra("phone",phone);
            i.putExtra("email",email);
            i.putExtra("date",date);
            i.putExtra("time",time);
            i.putExtra("bde_name",bde_name);
            i.putExtra("bde_phone",bde_phone);
            i.putExtra("bde_email",bde_email);
            i.putExtra("bdm",bdm);

            context.startActivity(i);


         }
      });

   }

   @NonNull
   @Override
   public BDMPendingMeetingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bdm_meeting_card,
              parent, false);
      context = v.getContext();
      return new BDMPendingMeetingsViewHolder(v);
   }

   static class BDMPendingMeetingsViewHolder extends RecyclerView.ViewHolder{

      TextView OrganizationName, OrganizationAddress, LeadName, MeetingTime;
      Button Call;

      public BDMPendingMeetingsViewHolder(@NonNull View itemView) {
         super(itemView);

         OrganizationName = itemView.findViewById(R.id.org_name);
         OrganizationAddress = itemView.findViewById(R.id.org_address);
         LeadName = itemView.findViewById(R.id.bdm_card_lead_name);
         MeetingTime = itemView.findViewById(R.id.meeting_time);

         Call = itemView.findViewById(R.id.call_button);

      }



   }

}
