package umut.gourmatch;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {


    private EditText name_view;
    private EditText email_view;
    private EditText message_view;

    private String name;
    private String email;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        name_view = (EditText)findViewById(R.id.Name);
        email_view = (EditText)findViewById(R.id.yourEmail);
        message_view = (EditText)findViewById(R.id.ComposeMessage);

        name = name_view.getText().toString();
        email = email_view.getText().toString();
        message = message_view.getText().toString();

        ImageButton startBtn = (ImageButton) findViewById(R.id.send);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String subject = "Message from User " + name;
                sendEmail(email, subject, message);
            }
        });
    }

    protected void sendEmail(String email, String subject, String message) {
        String[] TO = {email};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            Toast.makeText(ContactActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            //startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ContactActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
