package umut.gourmatch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthActivity extends AppCompatActivity {

    private static final String TAG = "AuthActivity.java";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private EditText passText;
    private EditText emailText;
    private TextView rPass;
    private TextView createAcc;
    private Button logIn;
    private ImageView fbLogo;
    private ImageView googleLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        //Facebook auth
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


        //Start firebase code
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
        //End firebase

//        // intitalize the facebook button
//        callbackManager = CallbackManager.Factory.create();
//        ImageView loginButton = (ImageView) view.findViewById(R.id.fbLogo);
//        ImageView.setReadPermissions("email", "public_profile");
//        ImageView.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG, "facebook:onSuccess:" + loginResult);
//                handleFacebookAccessToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d(TAG, "facebook:onCancel");
//                // [START_EXCLUDE]
//                updateUI(null);
//                // [END_EXCLUDE]
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.d(TAG, "facebook:onError","facebook:onError");
//                // [START_EXCLUDE]
//                updateUI(null);
//                // [END_EXCLUDE]
//            }
//        });
//        // end of facebook intialization

        //set up associations
        passText = (EditText) findViewById(R.id.authPassInput);
        emailText = (EditText) findViewById(R.id.authEmailInput);
        rPass = (TextView) findViewById(R.id.authPassReset);
        createAcc = (TextView) findViewById(R.id.authCreateAcc);
        logIn = (Button) findViewById(R.id.authSignInButton);
        fbLogo = (ImageView) findViewById(R.id.fbLogo);
        googleLogo = (ImageView) findViewById(R.id.googleLogo);


        //Listeners
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });
        rPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add code to reset password
                passReset();
            }
        });

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add code to create account
                Intent intent = new Intent(AuthActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });


    }

    private void passReset() {
        //new ResetPassDialogFragment();
        LayoutInflater factory = LayoutInflater.from(this);
        final View dialogLayout = factory.inflate(R.layout.dialog_reset_password, null);
        new AlertDialog.Builder(AuthActivity.this)
                .setView(dialogLayout)
                .setMessage(R.string.rest_pass_subtitle)
                .setTitle(R.string.reset_pass_title)
                .setPositiveButton(R.string.reset, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText emailText = (EditText) dialogLayout.findViewById(R.id.PR_email);
                        String emailAddress= emailText.getText().toString();
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        if(emailAddress != null  && !emailAddress.isEmpty()) {
                            auth.sendPasswordResetEmail(emailAddress)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "Email sent.");
                                                Toast.makeText(AuthActivity.this, "Email sent.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            dialog.dismiss();
                        } else {
                            Toast.makeText(AuthActivity.this, "Entry cannot be blank.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    //Added from Firebase code
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    //Added from Firebase code
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void login(View view) {
        //Check email and password combo
        String password = passText.getText().toString();
        String email = emailText.getText().toString();
        if(email.isEmpty()) {
            Toast.makeText(AuthActivity.this, "No email address entered.",
                    Toast.LENGTH_SHORT).show();
        } else
        if(password.isEmpty()) {
            Toast.makeText(AuthActivity.this, "No password entered.",
                    Toast.LENGTH_SHORT).show();
        } else {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail", task.getException());
                                Toast.makeText(AuthActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }
                    });
        }


    }
//    // [START auth_with_facebook]
//    private void handleFacebookAccessToken(AccessToken token) {
//        Log.d(TAG, "handleFacebookAccessToken:" + token);
//        // [START_EXCLUDE silent]
//        showProgressDialog();
//        // [END_EXCLUDE]
//
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
//
//                        // If sign in fails, display a message to the user. If sign in succeeds
//                        // the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener.
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "signInWithCredential", task.getException());
//                            Toast.makeText(FacebookLoginActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
//                            // [START_EXCLUDE]
//                            //hideProgressDialog();
//                            // [END_EXCLUDE]
//                        }
//                    }
//                });
//    }
//    // [END auth_with_facebook]

    public void fbLogin(View view) {
        Toast.makeText(AuthActivity.this, "Facebook login not yet available.",
                Toast.LENGTH_SHORT).show();
    }

    public void googleLogin(View view) {
        Toast.makeText(AuthActivity.this, "Google+ login not yet available.",
                Toast.LENGTH_SHORT).show();
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

//    //Addded majority from firebase
//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
//
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
//
//                        // If sign in fails, display a message to the user. If sign in succeeds
//                        // the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener.
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "signInWithCredential", task.getException());
//                            Toast.makeText(AuthActivity.this, "Google authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                        // ...
//                    }
//                });
//    }

}
