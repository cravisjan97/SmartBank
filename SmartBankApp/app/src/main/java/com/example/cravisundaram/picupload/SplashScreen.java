package com.example.cravisundaram.picupload;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class SplashScreen extends Activity{

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash_screen);
            final TextView appName = (TextView) findViewById(R.id.app_text);
            final ImageView logo = (ImageView) findViewById(R.id.logo);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startAnimations(logo, appName);
                }
            }, 500);
        }

        private void startAnimations(final ImageView logo, final TextView appName) {


            Animation animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.zoom_logo);
            logo.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    logo.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                   appName.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SplashScreen.this.startActivity(new Intent(SplashScreen.this, MainActivity.class));
                            SplashScreen.this.finish();

                        }
                    },1000);
                    }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }
}
