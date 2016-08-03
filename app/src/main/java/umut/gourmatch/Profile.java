package umut.gourmatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

/*
When this file and it's corresponding XML are complete, uncomment and remove the code indicated in MainActivity.java
 */

public class Profile extends AppCompatActivity {

    private String viewID;
    private String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Collect the profile ID to view
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            viewID = extras.getString("ID");
        } else {
            //ERROR STUFF CAUSE THIS SHOULDN'T HAPPEN
        }

        //Sends true if user is the owner of the profile
        //Populates first view and continues to corrosponding view builder
        buildFirstView(userID.equals(viewID));

//        if(userID.equals(viewID)) {
//            //User is owner
//            buildOwnerView();
//        } else {
//            //User is not owner
//            buildGuestView();
//        }
    }

    private void buildFirstView(boolean owner) {
        //Create a read that pulls the information needed for the first view
        //If owner == true: set edit buttons to visible and add each listener
        ///Each button click should fire off a dialogFragment to be used to edit the corresponding info
        ///Continue to buildOwnerView()
        //Else, make sure no edit buttons are clickable/visable
        ///Continue to buildGuestView

        //Before continuing, don't forget to set the view
    }

    private void buildGuestView() {
        //Set the guest view to visible, pull information, and build
    }

    private void buildOwnerView() {
        //Set the owner view to visible, pull information, and build
    }

    /*
    ***FRAGMENTS
    * Links:
    * https://developer.android.com/reference/android/app/DialogFragment.html
    * http://stackoverflow.com/questions/10905312/receive-result-from-dialogfragment
    *
    * Will need:
    * A fragment for editing text
    * A fragment to edit a date (using spinners)
    * A fragment for checkboxes (like allergies)
    * A fragment for a radioGroup (like diets)
    *
    * Since these might be reusable and a lot of the code may be the same, it might be best to create our own class for it
    *
    * The fragments will be used for editing
     */
}
