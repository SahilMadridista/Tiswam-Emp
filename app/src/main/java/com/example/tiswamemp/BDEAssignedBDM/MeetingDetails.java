package com.example.tiswamemp.BDEAssignedBDM;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiswamemp.DatePickerFragment;
import com.example.tiswamemp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class MeetingDetails extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbar;
    TextView BusinessName, BusinessAddress, LeadName, LeadEmail, LeadPhone, MeetingDate;
    TextView MeetingTime, ServicesPurchased, AssignedBDM,PaymentMode,BDEName, BDEEmail, BDEPhone, TotalAmount, PaidAmount, RemainingAmount;
    FirebaseFirestore firebaseFirestore;
    CardView ServicesCard, PaymentsCard, BDMCard, BDECard;
    TextView NanText;
    private static final int REQUEST_PHONE_CALL = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_details);

        firebaseFirestore = FirebaseFirestore.getInstance();

        BusinessName = findViewById(R.id.lead_org_text);
        BusinessAddress = findViewById(R.id.lead_org_address);
        LeadName = findViewById(R.id.lead_name_text);
        LeadEmail = findViewById(R.id.lead_email_text);
        LeadPhone = findViewById(R.id.lead_phone_text);
        MeetingDate = findViewById(R.id.d_date_text);
        MeetingTime = findViewById(R.id.d_time_text);
        ServicesPurchased = findViewById(R.id.services_purchased_text);
        AssignedBDM = findViewById(R.id.bdm_name);

        NanText = findViewById(R.id.nan_text);

        BDEName = findViewById(R.id.bde_name_text);
        BDEEmail = findViewById(R.id.bde_email_text);
        BDEPhone = findViewById(R.id.bde_phone_text);

        PaymentMode = findViewById(R.id.payment_mode);
        TotalAmount = findViewById(R.id.total_amount);
        PaidAmount = findViewById(R.id.paid_amount);
        RemainingAmount = findViewById(R.id.remaining_amount);

        ServicesCard = findViewById(R.id.services_card);
        PaymentsCard = findViewById(R.id.payments_card);
        BDMCard = findViewById(R.id.bdm_card);
        BDECard = findViewById(R.id.bde_card);

        Intent i = getIntent();

        final String id = i.getStringExtra("id");
        String business = i.getStringExtra("org");
        String address = i.getStringExtra("address");
        String name = i.getStringExtra("name");
        String phone = i.getStringExtra("phone");
        String email = i.getStringExtra("email");
        String date = i.getStringExtra("date");
        String time = i.getStringExtra("time");

        String bde_name = i.getStringExtra("bde_name");
        String bde_phone = i.getStringExtra("bde_phone");
        String bde_email = i.getStringExtra("bde_email");

        String bdm = i.getStringExtra("bdm");
        String mode = i.getStringExtra("mode");
        String total = i.getStringExtra("total");
        String advance = i.getStringExtra("advance");
        String remaining = i.getStringExtra("remaining");
        String key = i.getStringExtra("key");
        String key2 = i.getStringExtra("key2");

        assert key != null;

        if(key.equals("1")){
            ServicesCard.setVisibility(View.VISIBLE);
            PaymentsCard.setVisibility(View.VISIBLE);
            NanText.setVisibility(View.GONE);
        }

        assert key2 != null;

        if(key2.equals("bdm")){
            BDMCard.setVisibility(View.GONE);
            BDECard.setVisibility(View.VISIBLE);
        }

        BusinessName.setText(business);
        BusinessAddress.setText(address);
        LeadName.setText(name);
        LeadEmail.setText(email);
        LeadPhone.setText(phone);
        MeetingDate.setText(date);
        MeetingTime.setText(time);

        BDEName.setText(bde_name);
        BDEPhone.setText(bde_phone);
        BDEEmail.setText(bde_email);

        AssignedBDM.setText(bdm);
        PaymentMode.setText(mode);
        TotalAmount.setText(total);
        PaidAmount.setText(advance);
        RemainingAmount.setText(remaining);

        showServices(id);

        toolbar = findViewById(R.id.meeting_details_head);
        setSupportActionBar(toolbar);

    }

    private void showServices(String id) {

        DocumentReference documentReference =firebaseFirestore.collection("Leads").document(id);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    assert documentSnapshot != null;
                    ArrayList<String> Sports = (ArrayList<String>) documentSnapshot.get("services_offered");

                    assert Sports != null;

                    StringBuilder sb = new StringBuilder();
                    Iterator<String> iterator = Sports.iterator();

                    if (iterator.hasNext()) {
                        sb.append(iterator.next());

                        while (iterator.hasNext()) {
                            sb.append(", ");
                            sb.append(iterator.next());
                        }
                    }
                    ServicesPurchased.setText(sb);
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.call_email_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.call:

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + LeadPhone.getText().toString().trim()));
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) getApplicationContext(),
                            new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                }
                else
                {
                    startActivity(intent);
                }
                break;

            case R.id.email:

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",LeadEmail.getText().toString().trim(), null));
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                break;

            default:
                return super.onOptionsItemSelected(item);

        }

        return true;

    }

}