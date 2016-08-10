package umut.gourmatch;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CreateListingActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String userId;
    private EditText title_view;
    private EditText location_title_view;
    private EditText address_view;
    private EditText city_view;
    private EditText state_view;
    private EditText zip_code_view;
    private EditText total_seats_view;
    private EditText description_view;
    private String longitude;
    private String latitude;
    private String title;
    private String location_title;
    private String address;
    private String city;
    private String state;
    private String zip_code;
    private String total_seats;
    private String description;
    private DatabaseReference mDatabase;
    private  boolean success = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        ImageButton sendBtn  = (ImageButton)findViewById(R.id.send);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                title_view           = (EditText)findViewById(R.id.title);
                location_title_view  = (EditText)findViewById(R.id.location_title);
                address_view         = (EditText)findViewById(R.id.address);
                city_view            = (EditText)findViewById(R.id.city);
                state_view           = (EditText)findViewById(R.id.state);
                zip_code_view        = (EditText)findViewById(R.id.zip_code);
                total_seats_view     = (EditText)findViewById(R.id.total_seats);
                description_view     = (EditText)findViewById(R.id.description);

                title = title_view.getText().toString();
                location_title = location_title_view.getText().toString();
                address = address_view.getText().toString();
                city = city_view.getText().toString();
                state = state_view.getText().toString();
                zip_code = zip_code_view.getText().toString();
                total_seats = total_seats_view.getText().toString();
                description = description_view.getText().toString();

                boolean isFinished  = true;

                if(title == null || title.equals("")){
                    //create toast to warn user to finish input if not already done.
                    Context context = getApplicationContext();
                    CharSequence text = "Title can not be blank";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    isFinished  = false;
                    toast.show();
                }
                if(location_title == null || location_title.equals("")){
                    //create toast to warn user to finish input if not already done.
                    Context context = getApplicationContext();
                    CharSequence text = "Location Title can not be blank";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    isFinished  = false;
                    toast.show();
                }
                if(address == null || address.equals("")){
                    //create toast to warn user to finish input if not already done.
                    Context context = getApplicationContext();
                    CharSequence text = "Address can not be blank";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    isFinished  = false;
                    toast.show();
                }
                if(city == null || city.equals("")){
                    //create toast to warn user to finish input if not already done.
                    Context context = getApplicationContext();
                    CharSequence text = "City can not be blank";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    isFinished  = false;
                    toast.show();
                }
                if(state == null ||state.equals("")){
                    //create toast to warn user to finish input if not already done.
                    Context context = getApplicationContext();
                    CharSequence text = "State can not be blank";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    isFinished  = false;
                    toast.show();
                }
                if(zip_code == null || zip_code.equals("")){
                    //create toast to warn user to finish input if not already done.
                    Context context = getApplicationContext();
                    CharSequence text = "Zip Code can not be blank";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    isFinished  = false;
                    toast.show();
                }
                if(zip_code.length() != 5){
                    //create toast to warn user to finish input if not already done.
                    Context context = getApplicationContext();
                    CharSequence text = "Zip Code must contain 5 numbers";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    isFinished  = false;
                    toast.show();
                }
                if(total_seats == null || total_seats.equals("")){
                    //create toast to warn user to finish input if not already done.
                    Context context = getApplicationContext();
                    CharSequence text = "Total Seats can not be blank";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    isFinished  = false;
                    toast.show();
                }
                if(description == null || description.equals("")){
                    //create toast to warn user to finish input if not already done.
                    Context context = getApplicationContext();
                    CharSequence text = "Description can not be blank";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    isFinished  = false;
                    toast.show();
                }

                if(isFinished) {
                    //System.out.println("DESCRIPTION: " + description);
                    //   int int_zip_code = Integer.parseInt(zip_code);
                    save_listing();
                    finish();
                }



            }
        });



    }


    public void save_listing(){
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference listDB = mDatabase.child("listings").push();
        String listID = listDB.getKey();
        //convert
        Geocoder coder = new Geocoder(this);
        try {
            ArrayList<Address> addresses = (ArrayList<Address>) coder.getFromLocationName(address + ", " + city + ", " + state + ", " + zip_code, 50);
            Address add = addresses.get(0);
            latitude = String.valueOf(add.getLatitude());
            longitude = String.valueOf(add.getLongitude());

        } catch (IOException e) {
            Context context = getApplicationContext();
            CharSequence text = "Could not find address!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            success = false;
            toast.show();
            e.printStackTrace();
        }
        //newPostRef.setValue(new Post("gracehop", "Announcing COBOL, a New Programming Language"));

        if(success) {

            listDB.child("title").setValue(title, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        success = false;
                        System.out.println("Data could not be saved " + databaseError.getMessage());
                    } else {
                        System.out.println("Data saved successfully.");
                    }
                }
            });
            listDB.child("location_title").setValue(location_title, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        success = false;
                        System.out.println("Data could not be saved " + databaseError.getMessage());
                    } else {
                        System.out.println("Data saved successfully.");
                    }
                }
            });

            listDB.child("address").setValue(address, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        success = false;
                        System.out.println("Data could not be saved " + databaseError.getMessage());
                    } else {
                        System.out.println("Data saved successfully.");
                    }
                }
            });
            listDB.child("city").setValue(city, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        success = false;
                        System.out.println("Data could not be saved " + databaseError.getMessage());
                    } else {
                        System.out.println("Data saved successfully.");
                    }
                }
            });
            listDB.child("state").setValue(state, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        success = false;
                        System.out.println("Data could not be saved " + databaseError.getMessage());
                    } else {
                        System.out.println("Data saved successfully.");
                    }
                }
            });
            listDB.child("zip_code").setValue(zip_code, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        success = false;
                        System.out.println("Data could not be saved " + databaseError.getMessage());
                    } else {
                        System.out.println("Data saved successfully.");
                    }
                }
            });
            listDB.child("description").setValue(description, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        System.out.println("Data could not be saved " + databaseError.getMessage());
                        success = false;
                    } else {
                        System.out.println("Data saved successfully.");
                    }
                }
            });

            // save the list id to a user
            mDatabase.child("users").child(userId).child("listings").child(listID).setValue(true, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        success = false;
                        System.out.println("Data could not be saved " + databaseError.getMessage());
                    } else {
                        System.out.println("Data saved successfully.");
                    }
                }
            });
        }
        //mDatabase.child(user)



    }



}
