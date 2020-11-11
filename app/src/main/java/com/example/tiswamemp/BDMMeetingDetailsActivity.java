package com.example.tiswamemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class BDMMeetingDetailsActivity extends AppCompatActivity {

    TextView BusinessName, BusinessAddress, LeadName, LeadEmail, LeadPhone, MeetingDate;
    TextView MeetingTime, ServicesPurchased, AssignedBDM, BDEName, BDEEmail, BDEPhone, PaymentMode, TotalAmount, PaidAmount, RemainingAmount;
    CardView ServicesCard;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_d_m_meeting_details);

        BusinessName = findViewById(R.id.lead_org_text);
        BusinessAddress = findViewById(R.id.lead_org_address);
        LeadName = findViewById(R.id.lead_name_text);
        LeadEmail = findViewById(R.id.lead_email_text);
        LeadPhone = findViewById(R.id.lead_phone_text);
        MeetingDate = findViewById(R.id.d_date_text);
        MeetingTime = findViewById(R.id.d_time_text);
        BDEName = findViewById(R.id.bde_name_text);
        BDEEmail = findViewById(R.id.bde_email_text);
        BDEPhone = findViewById(R.id.bde_phone_text);
        /*PaymentMode = findViewById(R.id.payment_mode);
        TotalAmount = findViewById(R.id.total_amount);
        PaidAmount = findViewById(R.id.paid_amount);
        RemainingAmount = findViewById(R.id.remaining_amount);*/

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

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

        ServicesCard = findViewById(R.id.services_offered_card);

        final LinearLayout expandableView = findViewById(R.id.expandableView);
        final RelativeLayout cardView = findViewById(R.id.cardView);

        final Button expand = findViewById(R.id.expand);
        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (expandableView.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.VISIBLE);
                    expand.setBackgroundResource(R.drawable.ic_up);
                } else {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.GONE);
                    expand.setBackgroundResource(R.drawable.ic_down);
                }

            }
        });

        final LinearLayout expandableView_management = findViewById(R.id.expandableView_management);
        final Button expand_management = findViewById(R.id.expand_management);
        expand_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (expandableView_management.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView_management.setVisibility(View.VISIBLE);
                    expand_management.setBackgroundResource(R.drawable.ic_up);
                } else {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView_management.setVisibility(View.GONE);
                    expand_management.setBackgroundResource(R.drawable.ic_down);
                }

            }
        });

        final LinearLayout expandableView_marketing = findViewById(R.id.expandableView_marketing);
        final Button expand_marketing = findViewById(R.id.expand_marketing);
        expand_marketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (expandableView_marketing.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView_marketing.setVisibility(View.VISIBLE);
                    expand_marketing.setBackgroundResource(R.drawable.ic_up);
                } else {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView_marketing.setVisibility(View.GONE);
                    expand_marketing.setBackgroundResource(R.drawable.ic_down);
                }

            }
        });

        final LinearLayout expandableView_telecom = findViewById(R.id.expandableView_telecom);
        final Button expand_telecom = findViewById(R.id.expand_telecom);
        expand_telecom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (expandableView_telecom.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView_telecom.setVisibility(View.VISIBLE);
                    expand_telecom.setBackgroundResource(R.drawable.ic_up);
                } else {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView_telecom.setVisibility(View.GONE);
                    expand_telecom.setBackgroundResource(R.drawable.ic_down);
                }

            }
        });

        final LinearLayout expandableView_advertising = findViewById(R.id.expandableView_advertising);
        final Button expand_advertising = findViewById(R.id.expand_advertising);
        expand_advertising.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (expandableView_advertising.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView_advertising.setVisibility(View.VISIBLE);
                    expand_advertising.setBackgroundResource(R.drawable.ic_up);
                } else {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView_advertising.setVisibility(View.GONE);
                    expand_advertising.setBackgroundResource(R.drawable.ic_down);
                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bdm_meeting_accept_reject_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.delete_lead:

                Toast.makeText(getApplicationContext(),"Delete lead clicked",Toast.LENGTH_SHORT).show();
                break;

            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

}