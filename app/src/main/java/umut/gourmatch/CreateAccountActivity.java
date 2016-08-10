package umut.gourmatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccountActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "CreateAccountActivity.j"; //Tag cannot be longer than this
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private EditText passText;
    private EditText passConfirmText;
    private EditText emailText;
    private Button signIn;
    private LoginButton fbLogo;
    private SignInButton googleLogo;
    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Facebook auth
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_create_account);

        passText = (EditText) findViewById(R.id.CA_PassInput);
        passConfirmText = (EditText) findViewById(R.id.CA_PassConfirmInput);
        emailText = (EditText) findViewById(R.id.CA_EmailInput);
        signIn = (Button) findViewById(R.id.CA_SignUpButton);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    checkLogin();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailText.getText().toString().isEmpty()) {
                    Toast.makeText(CreateAccountActivity.this, "No email address entered.",
                            Toast.LENGTH_SHORT).show();
                } else
                if(passText.getText().toString().isEmpty()) {
                    Toast.makeText(CreateAccountActivity.this, "No password entered.",
                            Toast.LENGTH_SHORT).show();
                } else
                if(passConfirmText.getText().toString().isEmpty()) {
                    Toast.makeText(CreateAccountActivity.this, "Please confirm password.",
                            Toast.LENGTH_SHORT).show();
                } else
                if(passText.getText().toString().indexOf(' ') != -1){
                    Toast.makeText(CreateAccountActivity.this, "No spaces in your password",
                            Toast.LENGTH_SHORT).show();
                } else
                if(passText.getText().toString().length() < 6){
                    Toast.makeText(CreateAccountActivity.this, "Password must be 6 characters long",
                            Toast.LENGTH_SHORT).show();
                } else
                if(passConfirmText.getText().toString().equals(passText.getText().toString())) {
                    login(emailText.getText().toString(), passText.getText().toString());
                }
                else {
                    Toast.makeText(CreateAccountActivity.this, "Password doesn't match confirmed password.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // intitalize the facebook button
        fbLogo = (LoginButton) findViewById(R.id.fbLogo);
        callbackManager = CallbackManager.Factory.create();
        fbLogo.setReadPermissions("email", "public_profile");
        fbLogo.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                fbLogin(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
//                // [START_EXCLUDE]
//                updateUI(null);
//                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError",error);
//                // [START_EXCLUDE]
//                updateUI(null);
//                // [END_EXCLUDE]
            }
        });
        // end of facebook intialization

        //initialize the google button
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        googleLogo = (SignInButton) findViewById(R.id.googleLogo);
        googleLogo.setSize(SignInButton.SIZE_WIDE);
        googleLogo.setColorScheme(SignInButton.COLOR_LIGHT);
        googleLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });
        //end of google initialization
    }

    //Code template from Firebase
    public void fbLogin(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(CreateAccountActivity.this, "Facebook authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            checkLogin();
                            // [START_EXCLUDE]
                            //hideProgressDialog();
                            // [END_EXCLUDE]
                        }
                    }
                });
//        Toast.makeText(AuthActivity.this, "Facebook login not yet available.",
//                Toast.LENGTH_SHORT).show();
    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
                Toast.makeText(CreateAccountActivity.this, "Google sign in failed.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Added majority from firebase
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(CreateAccountActivity.this, "Google authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
//                        else {
//                            Log.d(TAG, "Google auth successful. Checking user...");
//                            checkLogin();
//                        }
                    }
                });
    }

    private void login(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(CreateAccountActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void checkLogin() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(!dataSnapshot.hasChild("username")) {
                            Intent intent = new Intent(getApplicationContext(),ProfileBuilderActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //Throw up a toast
                    }
                }
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
