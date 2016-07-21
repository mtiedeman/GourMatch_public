package umut.gourmatch;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity
{
    private static final String TAG = "SplashActivity.java";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Enables the use of Firebase
        Firebase.setAndroidContext(this);

        //pause for .5 second(s) before checking whether the user is logged in
        Thread timerThread = new Thread(){
            public void run()
            {
                try
                {
                    sleep(500);
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
                        Log.d(TAG, "User is already logged in.");
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        //Go to authentication screen if not logged in
                        Log.d(TAG, "User is not logged in.");
                        Intent intent = new Intent(getApplicationContext(),AuthActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
