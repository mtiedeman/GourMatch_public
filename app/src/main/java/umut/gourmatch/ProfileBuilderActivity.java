package umut.gourmatch;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import umut.gourmatch.User;


public class ProfileBuilderActivity extends AppCompatActivity {
    private final Firebase ref = new Firebase("https://gourmatch.firebaseio.com/users");
    private DatabaseReference mDatabase;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        /*
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mBirthyear = (EditText)findViewById(R.id.birth);
        mUsername = (EditText)findViewById(R.id.username);
        mButton = (Button) findViewById(R.id.next);

        mButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        username = mUsername.getText().toString();
                        if(check_user(username)){
                            //go to next intent
                        }
                        else{
                            //bring up x
                        }
                    }
                });

        setContentView(R.layout.activity_profile_builder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    public void check_user(String username){
        /*
        Boolean used = false;
        mDatabase.child("usernames").child(username).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if(user == null || ){
                            //user is null, so it is ok

                        }
                        else{
                            // username exists
                            //for now
                            Toast.makeText(NewPostActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        //uncomment above toast later
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
        */
    }

}
