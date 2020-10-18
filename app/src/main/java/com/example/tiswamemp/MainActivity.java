package com.example.tiswamemp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      final ImageView img = findViewById(R.id.imageView);
      TextView one = findViewById(R.id.textView);
      TextView two = findViewById(R.id.textView2);
      final LinearLayout linearLayout = findViewById(R.id.linearLayout);

      img.animate().alpha(1).setDuration(1000);
      one.animate().alpha(1).setDuration(1000);
      two.animate().alpha(1).setDuration(1000);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         getWindow().getSharedElementEnterTransition().setDuration(1000);
         getWindow().getSharedElementReturnTransition().setDuration(1000)
                 .setInterpolator(new DecelerateInterpolator());
      }

      final Button CreateAccountButton = findViewById(R.id.create_acc_text);
      CreateAccountButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Fade fade = new Fade();
            View decor = getWindow().getDecorView();
            fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
            Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    MainActivity.this, img, Objects.requireNonNull(ViewCompat.getTransitionName(img)));
            startActivity(intent, options.toBundle());

         }
      });

      final Button button = findViewById(R.id.button);
      button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

            Fade fade = new Fade();
            View decor = getWindow().getDecorView();
            fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    MainActivity.this, img, Objects.requireNonNull(ViewCompat.getTransitionName(img)));
            startActivity(intent, options.toBundle());

         }
      });

      SharedPreferences preferences = getSharedPreferences("MyPref", MODE_PRIVATE);
      final int loginStatus = preferences.getInt("login", SharedPrefConsts.NO_LOGIN);

      new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {

            if(loginStatus == SharedPrefConsts.BDE_LOGIN){
               Intent i = new Intent(getApplicationContext(),BDEHomePage.class);
               startActivity(i);
               finish();
            }

            if(loginStatus == SharedPrefConsts.BDM_LOGIN){
               Intent i = new Intent(getApplicationContext(),BDMHomePage.class);
               startActivity(i);
               finish();
            }

            if(loginStatus == SharedPrefConsts.IT_LOGIN){
               Intent i = new Intent(getApplicationContext(),ITHomePage.class);
               startActivity(i);
               finish();
            }

            else {
               linearLayout.animate().alpha(1).setDuration(1000);
            }

         }
      },1000);



   }

}