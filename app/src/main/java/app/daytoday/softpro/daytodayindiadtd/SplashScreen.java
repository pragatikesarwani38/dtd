package app.daytoday.softpro.daytodayindiadtd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView=(ImageView)findViewById(R.id.logo); // Declare an imageView to show the animation.
        // Create the animation.

        Thread th =new Thread()
        {
            @Override
            public void run()
            {
                try {
                    Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            startActivity(new Intent(SplashScreen.this,WelcomeActivity.class));
                            finish();
                            // HomeActivity.class is the activity to go after showing the splash screen.
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    imageView.startAnimation(anim);

                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               /* Intent i=new Intent(SplashScreen.this,WelcomeActivity.class);
                startActivity(i);
                finish();*/
            }
        };
        th.start();
    }
}
