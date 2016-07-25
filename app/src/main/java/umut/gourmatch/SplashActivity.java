package umut.gourmatch;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;

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
        FirebaseCrash.log("Splash: pause");
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
                    FirebaseCrash.log("Splash: getting FirebaseAuth instance");
                    FirebaseAuth auth = FirebaseAuth.getInstance();

                    FirebaseCrash.log("Splash: checking authentication");
                    if (auth.getCurrentUser() != null) {
                        FirebaseCrash.log("Splash: user already logged in; go to Main");
                        //Go to main screen if already logged in
                        Log.d(TAG, "User is already logged in.");
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        FirebaseCrash.log("Splash: user not logged in; go to Auth");
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
