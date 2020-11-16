package com.example.tiswamemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class BDMMeetingDetailsActivity extends AppCompatActivity {

    TextView BusinessName, BusinessAddress, LeadName, LeadEmail, LeadPhone, MeetingDate;
    TextView MeetingTime, BDEName, BDEEmail, BDEPhone;
    CardView ServicesCard;
    androidx.appcompat.widget.Toolbar toolbar;
    Spinner PaymentModeSpinner;
    FirebaseFirestore firebaseFirestore;
    CheckBox WebsiteCheckbox, E_commerceCheckbox, MobileAppCheckbox, PortalCheckbox, GraphicsCheckbox, GoogleBusinessCheckbox, MLMSoftwareCheckbox;
    CheckBox SchoolCheckbox, CRMCheckbox, HospitalCheckbox, HotelCheckbox;
    CheckBox SocialMediaCheckbox, SMSCheckbox, WhatsAppCheckbox, EmailCheckbox;
    CheckBox C2CCheckbox, TeleconferencingCheckbox, IVRCheckbox;
    CheckBox OnlineCheckbox, BrandCheckbox, GoogleAdvertisingCheckbox, FBCheckbox, VideoCheckbox, VoiceCheckbox, GoogleAdsCheckbox;
    Button DoneButton;
    String id, business, phone;
    EditText TotalAmount, PaidAmount, RemainingAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_d_m_meeting_details);

        firebaseFirestore = FirebaseFirestore.getInstance();

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

        DoneButton  =findViewById(R.id.add_services_button);
        DoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoneDeal();
            }
        });

        /*PaymentMode = findViewById(R.id.payment_mode);
        TotalAmount = findViewById(R.id.total_amount);
        PaidAmount = findViewById(R.id.paid_amount);
        RemainingAmount = findViewById(R.id.remaining_amount);*/

        PaymentModeSpinner  = findViewById(R.id.payment_mode_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.mode, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PaymentModeSpinner.setAdapter(adapter);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();

        id = i.getStringExtra("id");
        business = i.getStringExtra("org");
        String address = i.getStringExtra("address");
        String name = i.getStringExtra("name");
        phone = i.getStringExtra("phone");
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

        initializeDevelopmentCheckboxes();
        initializeManagementCheckboxes();
        initializeMarketingCheckboxes();
        initializeTelecomCheckboxes();
        initializeAdvertisingCheckboxes();
        initializeEditText();


    }

    private void initializeDevelopmentCheckboxes() {

        WebsiteCheckbox = findViewById(R.id.website_checkbox);
        E_commerceCheckbox = findViewById(R.id.e_commerce_checkbox);
        MobileAppCheckbox = findViewById(R.id.mobile_app_checkbox);
        PortalCheckbox = findViewById(R.id.portal_checkbox);
        GraphicsCheckbox = findViewById(R.id.graphics_checkbox);
        GoogleBusinessCheckbox = findViewById(R.id.google_busi_checkbox);
        MLMSoftwareCheckbox = findViewById(R.id.mlm_checkbox);

    }

    private void initializeManagementCheckboxes(){

        SchoolCheckbox = findViewById(R.id.school_checkbox);
        CRMCheckbox = findViewById(R.id.crm_checkbox);
        HospitalCheckbox = findViewById(R.id.hospital_checkbox);
        HotelCheckbox = findViewById(R.id.hotel_checkbox);

    }

    private void initializeMarketingCheckboxes(){

        SocialMediaCheckbox = findViewById(R.id.social_checkbox);
        SMSCheckbox = findViewById(R.id.sms_checkbox);
        WhatsAppCheckbox = findViewById(R.id.whatsapp_checkbox);
        EmailCheckbox = findViewById(R.id.email_checkbox);


    }

    private void initializeTelecomCheckboxes(){

        C2CCheckbox = findViewById(R.id.c2c_checkbox);
        TeleconferencingCheckbox = findViewById(R.id.teleconferencing_checkbox);
        IVRCheckbox = findViewById(R.id.ivr_checkbox);

    }

    private void initializeAdvertisingCheckboxes(){
        OnlineCheckbox = findViewById(R.id.online_checkbox);
        BrandCheckbox = findViewById(R.id.brand_checkbox);
        GoogleAdvertisingCheckbox = findViewById(R.id.google_adver_checkbox);
        FBCheckbox = findViewById(R.id.fb_checkbox);
        VideoCheckbox = findViewById(R.id.video_checkbox);
        VoiceCheckbox = findViewById(R.id.voice_checkbox);
        GoogleAdsCheckbox = findViewById(R.id.google_ads_checkbox);

    }

    private void initializeEditText(){
        TotalAmount = findViewById(R.id.total_amount_et);
        PaidAmount = findViewById(R.id.paid_amount_et);
        RemainingAmount = findViewById(R.id.remaining_amount_et);
    }

    private void DoneDeal() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Adding services...");

        ArrayList<String> ServicesList = new ArrayList<>();

        if(WebsiteCheckbox.isChecked()){
            ServicesList.add("Website development");
        }

        if(E_commerceCheckbox.isChecked()){
            ServicesList.add("E-commerce development");
        }

        if(MobileAppCheckbox.isChecked()){
            ServicesList.add("Mobile app development");
        }

        if(PortalCheckbox.isChecked()){
            ServicesList.add("Portal development");
        }

        if(GraphicsCheckbox.isChecked()){
            ServicesList.add("Graphics development");
        }

        if(GoogleBusinessCheckbox.isChecked()){
            ServicesList.add("Google business development");
        }

        if(MLMSoftwareCheckbox.isChecked()){
            ServicesList.add("MLM software");
        }

        if(SchoolCheckbox.isChecked()){
            ServicesList.add("School management system");
        }

        if(CRMCheckbox.isChecked()){
            ServicesList.add("Customer relationship management");
        }

        if(HospitalCheckbox.isChecked()){
            ServicesList.add("Hospital management system");
        }

        if(HotelCheckbox.isChecked()){
            ServicesList.add("Hotel management system");
        }

        if(SocialMediaCheckbox.isChecked()){
            ServicesList.add("Social media marketing");
        }

        if(SMSCheckbox.isChecked()){
            ServicesList.add("SMS marketing");
        }

        if(WhatsAppCheckbox.isChecked()){
            ServicesList.add("WhatsApp marketing");
        }

        if(EmailCheckbox.isChecked()){
            ServicesList.add("Email marketing");
        }

        if(C2CCheckbox.isChecked()){
            ServicesList.add("C2C service");
        }

        if(TeleconferencingCheckbox.isChecked()){
            ServicesList.add("Teleconferencing service");
        }

        if(IVRCheckbox.isChecked()){
            ServicesList.add("IVR service");
        }

        if(OnlineCheckbox.isChecked()){
            ServicesList.add("Online advertising");
        }

        if(BrandCheckbox.isChecked()){
            ServicesList.add("Brand creation");
        }

        if(GoogleAdvertisingCheckbox.isChecked()){
            ServicesList.add("Google advertising");
        }

        if(FBCheckbox.isChecked()){
            ServicesList.add("Facebook promotion");
        }

        if(VideoCheckbox.isChecked()){
            ServicesList.add("Video marketing");
        }

        if(VoiceCheckbox.isChecked()){
            ServicesList.add("Voice SMS");
        }

        if(GoogleAdsCheckbox.isChecked()){
            ServicesList.add("Google ads");
        }

        if(ServicesList.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please select at least a service for the customer",Toast.LENGTH_SHORT).show();
            return;
        }

        if(PaymentModeSpinner.getSelectedItem().toString().trim().equals("Payment mode")){
            Toast.makeText(getApplicationContext(),"Please select a payment mode",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TotalAmount.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter deal amount",Toast.LENGTH_SHORT).show();
            return;
        }

        if(PaidAmount.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter paid amount",Toast.LENGTH_SHORT).show();
            return;
        }

        if(RemainingAmount.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter remaining amount",Toast.LENGTH_SHORT).show();
            return;
        }

        if(Integer.parseInt(TotalAmount.getText().toString().trim()) < Integer.parseInt(PaidAmount.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"Paid amount can't be greater than total amount.",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        for(int i = 0;i<ServicesList.size();i++){

            firebaseFirestore.collection("Leads").document(id).update("services_offered",
                    FieldValue.arrayUnion(ServicesList.get(i))).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    progressDialog.setMessage("Uploading payment details...");

                }
            });

        }

        Map<String,Object> lead = new LinkedHashMap<>();

        lead.put("deal_amount",TotalAmount.getText().toString().trim());
        lead.put("deal_status","done");
        lead.put("payment_mode",PaymentModeSpinner.getSelectedItem().toString().trim());
        lead.put("advance_amount",PaidAmount.getText().toString().trim());
        lead.put("remaining_amount",RemainingAmount.getText().toString().trim());

        firebaseFirestore.collection("Leads").document(id)
                .update(lead).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                startActivity(new Intent(getApplicationContext(),BDMHomePage.class));
                finish();
                Toast.makeText(getApplicationContext(),"Deal done with " + business,Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
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

        if (item.getItemId() == R.id.delete_lead) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Do you really want to delete this lead's details ?").setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialogInterface, int i) {

                            firebaseFirestore.collection("Leads").document(id)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            dialogInterface.cancel();
                                            startActivity(new Intent(getApplicationContext(), BDMHomePage.class));
                                            Toast.makeText(getApplicationContext(), "Lead deleted", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialogInterface.cancel();
                                }
                            });

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }

        return super.onOptionsItemSelected(item);

    }

}