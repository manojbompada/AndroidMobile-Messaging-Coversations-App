/*
    Cole Howell, Manoj Bompada
    ViewContactsActivity.java
    ITCS 4180
 */

package com.example.cole.homework8;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class ViewContactsActivity extends AppCompatActivity {

    private User loguser;
    private User contactobj;
    TextView name, username, email, phoneNumber;
    ImageView contactPhoto;
    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts);
        setTitle("View Contact");

        mRef = new Firebase("https://homework-8.firebaseio.com/users");

        if (getIntent().getExtras() != null){
            loguser = (User) getIntent().getExtras().getSerializable(ViewMessagesActivity.LOGUSER);
            contactobj = (User) getIntent().getExtras().getSerializable(ViewMessagesActivity.CONTACTUSER);
        }

        name = (TextView) findViewById(R.id.vcFullName);
        username = (TextView) findViewById(R.id.vcContactName);
        email = (TextView) findViewById(R.id.vcEmail);
        phoneNumber = (TextView) findViewById(R.id.vcPhoneNum);
        contactPhoto = (ImageView) findViewById(R.id.vcPhoto);

        byte[] decodedString = Base64.decode(contactobj.getPicture(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        name.setText(contactobj.getFullname());
        username.setText(contactobj.getFullname());
        email.setText(contactobj.getEmail());
        phoneNumber.setText(String.valueOf(contactobj.getPhoneno()));
        contactPhoto.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 150, 150, false));

    }
}
