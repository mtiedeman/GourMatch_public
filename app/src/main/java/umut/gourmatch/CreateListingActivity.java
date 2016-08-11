package umut.gourmatch;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.DatePicker;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private DatePicker datepicker;
    private TimePicker timepicker;

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
    private String date;
    private String time;

    private DatabaseReference mDatabase;
    private boolean success = true;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        ImageButton sendBtn = (ImageButton) findViewById(R.id.send);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                title_view = (EditText) findViewById(R.id.title);
                location_title_view = (EditText) findViewById(R.id.location_title);
                address_view = (EditText) findViewById(R.id.address);
                city_view = (EditText) findViewById(R.id.city);
                state_view = (EditText) findViewById(R.id.state);
                zip_code_view = (EditText) findViewById(R.id.zip_code);
                total_seats_view = (EditText) findViewById(R.id.total_seats);
                description_view = (EditText) findViewById(R.id.description);
                datepicker  = (DatePicker)findViewById(R.id.datePick);
                timepicker = (TimePicker)findViewById(R.id.timePick);

                title = title_view.getText().toString();
                location_title = location_title_view.getText().toString();
                address = address_view.getText().toString();
                city = city_view.getText().toString();
                state = state_view.getText().toString();
                zip_code = zip_code_view.getText().toString();
                total_seats = total_seats_view.getText().toString();
                description = description_view.getText().toString();

                //date
                int   day  = datepicker.getDayOfMonth();
                int   month= datepicker.getMonth();
                int   year = datepicker.getYear();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String formatedDate = sdf.format(new Date(year, month, day));

                //time
                int hour = timepicker.getCurrentHour();
                int min = timepicker.getCurrentMinute();

                boolean isFinished = true;

                if (title == null || title.equals("")) {
                    //create toast to warn user to finish input if not already done.
                    Context context = getApplicationContext();
                    CharSequence text = "Title can not be blank";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    isFinished = false;
                    toast.show();
                }
                if (location_title == null || location_title.equals("")) {
                    //create toast to warn user to finish input if not already done.
                    Context context = getApplicationContext();
                    CharSequence text = "Location Title can not be blank";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    isFinished = false;
                    toast.show();
                }
                if (address == null || address.equals("")) {
                    //create toast to warn user to finish input if not already done.
                    Context context = getApplicationContext();
                    CharSequence text = "Address can not be blank";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    isFinished = false;
                    toast.show();
                }
                if (city == null || city.equals("")) {
                    //create toast to warn user to finish input if not already done.
                    Context context = getApplicationContext();
                    CharSequence text = "City can not be blank";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    isFinished = false;
                    toast.show();
                }
                if (state == null || state.equals("")) {
                    //create toast to warn user to finish input if not already done.
                    Context context = getApplicationContext();
                    CharSequence text = "State can not be blank";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    isFinished = false;
                    toast.show();
                }
                if (zip_code == null || zip_code.equals("")) {
                    //create toast to warn user to finish input if not already done.
                    Context context = getApplicationContext();
                    CharSequence text = "Zip Code can not be blank";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    isFinished = false;
                    toast.show();
                }
                if (zip_code.length() != 5) {
                    //create toast to warn user to finish input if not already done.
                    Context context = getApplicationContext();
                    CharSequence text = "Zip Code must contain 5 numbers";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    isFinished = false;
                    toast.show();
                }
                if (total_seats == null || total_seats.equals("")) {
                    //create toast to warn user to finish input if not already done.
                    Context context = getApplicationContext();
                    CharSequence text = "Total Seats can not be blank";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    isFinished = false;
                    toast.show();
                }
                if (description == null || description.equals("")) {
                    //create toast to warn user to finish input if not already done.
                    Context context = getApplicationContext();
                    CharSequence text = "Description can not be blank";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    isFinished = false;
                    toast.show();
                }

                if (isFinished) {
                    //System.out.println("DESCRIPTION: " + description);
                    //   int int_zip_code = Integer.parseInt(zip_code);
                    save_listing();
                    finish();
                }


            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void save_listing() {
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

        if (success) {

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


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CreateListing Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://umut.gourmatch/http/host/path")
        );
        AppIndex.AppIndexApi.start(mClient, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CreateListing Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://umut.gourmatch/http/host/path")
        );
        AppIndex.AppIndexApi.end(mClient, viewAction);
        mClient.disconnect();
    }
}
