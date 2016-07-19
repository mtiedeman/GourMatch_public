package umut.gourmatch;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //background image
        ImageView myImage = (ImageView) findViewById(R.id.imageid);
        myImage.setAlpha(127);

        //pause for 5 seconds before switching to main activity
        Thread timerThread = new Thread(){
            public void run()
            {
                try
                {
                    sleep(5000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    if (auth.getCurrentUser() != null) {
                        //Go to main screen if already logged in
                        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                    } else {
                        //Go to authentication screen if not logged in
                        //TO BE CHANGED
                        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                    }

                }
            }
        };
        timerThread.start();
    }


    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }


}
