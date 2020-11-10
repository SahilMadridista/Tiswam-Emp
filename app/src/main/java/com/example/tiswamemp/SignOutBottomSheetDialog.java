package com.example.tiswamemp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class SignOutBottomSheetDialog extends BottomSheetDialogFragment {

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      final View v = inflater.inflate(R.layout.sign_out_bottom_sheet, container, false);

      final Context context = v.getContext();
      final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

      Button SignOut = v.findViewById(R.id.confirm_sign_out);
      SignOut.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

            SharedPreferences preferences = context.getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("login", SharedPrefConsts.NO_LOGIN);
            editor.apply();

            firebaseAuth.signOut();
            startActivity(new Intent(context,MainActivity.class));
            Objects.requireNonNull(getActivity()).finish();

         }
      });

      return v;
   }


}
