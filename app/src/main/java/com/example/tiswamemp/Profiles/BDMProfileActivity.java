package com.example.tiswamemp.Profiles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.tiswamemp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class BDMProfileActivity extends AppCompatActivity {

    RelativeLayout Details,Loading,ChangePassword;
    CardView ChangePassCard;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    TextView Name, Email, Phone;
    ImageView UserImage;
    EditText PasswordOne, PasswordTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_d_m_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        Details = findViewById(R.id.details_layout);
        Loading = findViewById(R.id.loading_layout);
        ChangePassword = findViewById(R.id.change_pass_layout);
        Name = findViewById(R.id.name_text);
        Email= findViewById(R.id.email_text);
        Phone = findViewById(R.id.phone_text);
        UserImage = findViewById(R.id.user_image);
        ChangePassCard = findViewById(R.id.change_pass_card);

        PasswordOne = findViewById(R.id.change_pass_et_one);
        PasswordTwo = findViewById(R.id.change_pass_et_two);

        assert firebaseAuth.getCurrentUser() != null;
        String id = firebaseAuth.getCurrentUser().getUid();
        showDetails(id);

        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Details.setVisibility(View.GONE);
                ChangePassCard.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInDown)
                        .repeat(0)
                        .duration(500)
                        .playOn(ChangePassCard);
            }
        });

        Button Cancel = findViewById(R.id.cancel_btn);
        Button Done = findViewById(R.id.done_button);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PasswordTwo.setText(null);
                PasswordOne.setText(null);
                ChangePassCard.setVisibility(View.GONE);
                Details.setVisibility(View.VISIBLE);


            }
        });

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Changing password...");

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String one = PasswordOne.getText().toString().trim();
                String two = PasswordTwo.getText().toString().trim();

                if(one.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter a new password.",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(two.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter new password again.",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(two.length() < 6){
                    Toast.makeText(getApplicationContext(),"Password must be at least 6 characters long.",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!(one.equals(two))){
                    Toast.makeText(getApplicationContext(),"Password does not match.",Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.show();

                firebaseAuth.getCurrentUser().updatePassword(one).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            progressDialog.dismiss();
                            PasswordTwo.setText(null);
                            PasswordOne.setText(null);
                            ChangePassCard.setVisibility(View.GONE);
                            Details.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(),"Password changed",Toast.LENGTH_SHORT).show();

                        }

                        else{

                            Toast.makeText(getApplicationContext(),
                                    Objects.requireNonNull(task.getException()).getMessage()
                                    ,Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }

                    }
                });


            }
        });


    }

    private void showDetails(String id) {

        firebaseFirestore.collection("Employees").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                assert documentSnapshot != null;

                Name.setText(documentSnapshot.getString("name"));
                Email.setText(documentSnapshot.getString("email"));
                Phone.setText(documentSnapshot.getString("phone"));
                String URL = documentSnapshot.getString("url");
                Picasso.get().load(URL).into(UserImage);

                Loading.setVisibility(View.GONE);
                Details.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInDown)
                        .duration(500)
                        .repeat(0)
                        .playOn(Details);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(ChangePassCard.getVisibility() == View.VISIBLE){
            ChangePassCard.setVisibility(View.GONE);
            Details.setVisibility(View.VISIBLE);
        }
        else{
            super.onBackPressed();
        }
    }

}