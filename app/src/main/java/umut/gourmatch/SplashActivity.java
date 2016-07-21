package umut.gourmatch;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity
{
    private static final String TAG = "SplashActivity.java";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        //background image
//        ImageView myImage = (ImageView) findViewById(R.id.imageid);

        //pause for 1 second(s) before checking whether the user is logged in
        Thread timerThread = new Thread(){
            public void run()
            {
                try
                {
                    sleep(1000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    //Log.d(TAG, "Auth: " + auth.getCurrentUser().toString());
                    if (auth.getCurrentUser() != null) {
                        //Go to main screen if already logged in
                    /*    Intent intent = new Intent(getApplicationContext(),MA.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent); */
                    } else {
                        //Go to authentication screen if not logged in
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
