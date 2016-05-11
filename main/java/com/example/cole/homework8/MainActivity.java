/*
    Cole Howell, Manoj Bompada
    MainActivity.java
    ITCS 4180
 */

package com.example.cole.homework8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.Console;

public class MainActivity extends AppCompatActivity {

    public static final String EMAIL = "user email" ;
    Button login, createAccount;
    TextView email, password;
    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Stay In Touch (Login)");
        Firebase.setAndroidContext(this);

        mRef = new Firebase("https://homework-8.firebaseio.com/");

        login = (Button) findViewById(R.id.loginButton);
        createAccount = (Button) findViewById(R.id.createAccount);
        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);

        AuthData authData = mRef.getAuth();
        if (authData != null) {
            Intent intent = new Intent(MainActivity.this, ConversationsActivity.class);
            startActivity(intent);
            finish();
        } else {
            // no user authenticated
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef.authWithPassword(email.getText().toString(), password.getText().toString(), new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Intent intent = new Intent(MainActivity.this, ConversationsActivity.class);
                        intent.putExtra(EMAIL, email.getText().toString());
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(MainActivity.this, "Login Failed! Invalid Username/Password", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
