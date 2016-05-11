/*
    Cole Howell, Manoj Bompada
    SignupActivity.java
    ITCS 4180
 */

package com.example.cole.homework8;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    Button signUp, cancel;
    TextView fullName, email, password, confirmPassword, phoneNumber;
    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Stay In Touch (SignUp)");
        Firebase.setAndroidContext(this);

        mRef = new Firebase("https://homework-8.firebaseio.com/");

        signUp = (Button) findViewById(R.id.signUpButton);
        cancel = (Button) findViewById(R.id.cancelButton);
        fullName = (TextView) findViewById(R.id.fullName);
        email = (TextView) findViewById(R.id.emailSignUp);
        password = (TextView) findViewById(R.id.passwordSignup);
        phoneNumber = (TextView) findViewById(R.id.phoneNumber);
        confirmPassword = (TextView) findViewById(R.id.passwordConfirm);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.defaultcontact);
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        final byte[] image=stream.toByteArray();
        final String img_str = Base64.encodeToString(image, 0);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mRef.createUser(email.getText().toString(), password.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> stringObjectMap) {

                        Map<String, String> post = new HashMap<String, String>();

                        Firebase usersRef = mRef.child("users");

                        User user = new User(fullName.getText().toString(),email.getText().toString(),password.getText().toString(),img_str,Integer.parseInt(phoneNumber.getText().toString()));

                        String s = user.getEmail();
                        String[] split = s.split("@");
                        String username = split[0];
                        usersRef.child(username).setValue(user);

                        Log.d("demo", "user created " + user.toString());

                        Toast.makeText(SignupActivity.this, "User has been created successfully!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        switch (firebaseError.getCode()) {
                            case FirebaseError.EMAIL_TAKEN:
                                Toast.makeText(SignupActivity.this, "This email address is already taken. Select another one", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
