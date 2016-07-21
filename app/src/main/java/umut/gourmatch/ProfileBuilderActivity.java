package umut.gourmatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import umut.gourmatch.User;


public class ProfileBuilderActivity extends AppCompatActivity {
    private ArrayList<String> allergy_names = new ArrayList<String>();
    private ArrayList<String> allergy_info = new ArrayList<String>();
    private ArrayList<String> diet_names = new ArrayList<String>();
    private ArrayList<String> diet_info = new ArrayList<String>();
    // lacto, lacto_ovo, ovo, pesce, vegan
    private Boolean[] allergies = new Boolean[8];
    private Boolean[] diets = new Boolean[5];
    private final Firebase ref = new Firebase("https://gourmatch.firebaseio.com/users");
    private DatabaseReference mDatabase;
    private String username;
    private String firstName;
    private String lastName;
    private int birthYear;
    private int birthMonth;
    private int birthDay;
    private Boolean used = false;
    private String TAG = "ProfileBuilderActivity.java";
//    private DatePicker datePicker;
//    private EditText mUsername;
//    private Button mNextButton;
//    private EditText mFirstName;
//    private EditText mLastName;
    private FirebaseAuth mAuth;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Firebase.setAndroidContext(this);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("allergies").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot allergy : dataSnapshot.getChildren()){
                            String allergy_name = allergy.getKey();
                            allergy_names.add(allergy_name);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                        Toast.makeText(ProfileBuilderActivity.this, "Failed to pull allergies from database.",
                                Toast.LENGTH_SHORT).show();

                    }
                }
        );

        mDatabase.child("dietary").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot diet : dataSnapshot.getChildren()){
                            String info = (String) diet.getValue();
                            String allergy_name = diet.getKey();
                            diet_info.add(info);
                            diet_names.add(allergy_name);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                        Toast.makeText(ProfileBuilderActivity.this, "Failed to load diets.",
                                Toast.LENGTH_SHORT).show();

                    }
                }
        );
        create_basic();


/*
        mAllergyButtonL = (Button) findViewById(R.id.next);
        mAllergyButtonR = (Button) findViewById(R.id.next);

        mAllergyButtonR.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                    }
                });

        mAllergyButtonL.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                    }
                });


        mDietBackButton = (Button) findViewById(R.id.next);

        mDietBackButton.setOnClickListerner(

        )
*/
    }



    public void check_user(){
        mDatabase.child("usernames").child(username).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //get username
                        if(!dataSnapshot.exists()){
                            Log.e(TAG, "User " + username + " is taken");
                            Toast.makeText(ProfileBuilderActivity.this,
                                    "Error: Username is taken.",
                                    Toast.LENGTH_SHORT).show();
                            used = false;
                        }
                        else{
                            used = true;
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );

    }

    public void create_allergies(){
//        for( int i = 0; i < allergy_names.size(); i++){
//            CheckBox myCheck = new CheckBox(this);
//            myCheck.setText(allergy_names.get(i));
//            myCheck.setId(i);
//            LinearLayout allergy_layout;// =  (LinearLayout) findViewById(R.id.);
//            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//            //allergy_layout.addView(myCheck, lp);
//            myCheck.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    CheckBox b = (CheckBox) view;
//                    int allergy_id = b.getId() - 1;
//                    if(allergies[allergy_id]){
//                        allergies[allergy_id] = false;
//                    }
//                    else{
//                        allergies[allergy_id] = true;
//                    }
//                }
//
//            });
//        }
    }

    public void create_diets(){
        RadioGroup group = new RadioGroup(this);
        group.setOrientation(RadioGroup.VERTICAL);
        for(int i = 0; i < diet_names.size(); i++) {
            String diet_name = diet_names.get(i);
            String info = diet_info.get(i);
            RadioButton btn = new RadioButton(this);
            btn.setText(diet_name + ": " + info);
            btn.setId(i + 9);
            group.addView(btn);
        }
//        ((ViewGroup) findViewById(R.id.radiogroup)).addView(group);


    }

    public void create_basic() {
        setContentView(R.layout.activity_profile_builder_basic);
            DatePicker datePicker;
            final EditText mUsername = (EditText)findViewById(R.id.basic_username);;
            Button mNextButton;
            EditText mFirstName;
            EditText mLastName;
        //mBirthyear = (EditText)findViewById(R.id.birth);
        datePicker = (DatePicker) findViewById(R.id.birthdayPick);
        mFirstName = (EditText)findViewById(R.id.basic_firstname);
        mLastName = (EditText)findViewById(R.id.basic_lastname);
        mNextButton = (Button) findViewById(R.id.Next);
        if(username != null) {
            mUsername.setText(username);
            mFirstName.setText(firstName);
            mLastName.setText(lastName);
            datePicker.init(birthYear, birthMonth, birthDay, null);
        } else {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            datePicker.init(year, month, day, null);
        }

//        mNextButton.setOnClickListener(
//                new View.OnClickListener(){
//                    public void onClick(View view){
//                        if(userId.isEmpty()) {
//                            Log.e(TAG, "User ID is unexpectedly null");
//                            Toast.makeText(ProfileBuilderActivity.this,
//                                    "Error: NO USER ID.",
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            username = mUsername.getText().toString();
//                            check_user();
//                            if (!used) {
//                                //Go to food allergies
//                                create_allergies();
//                            }
//                        }
//                    }
//                });
    }

    public void add_user() {
//        int id_num = ((RadioGroup) findViewById(R.id.radiogroup)).getCheckedRadioButtonId() - 9;
//        diets[id_num] = true;
//        User user = new User(mBirthyear.getText().toString(), mFirstname, mLastName, mGender, allergies, diets, username);

    }
    // on the last click

//    public User(String birthYear, String firstName, String lastName, String gender, Boolean[] allergies, Boolean[] dietary)
//
//
//
}
