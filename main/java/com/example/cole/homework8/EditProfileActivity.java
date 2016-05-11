/*
    Cole Howell, Manoj Bompada
    EditProfileActivity.java
    ITCS 4180
 */

package com.example.cole.homework8;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    public static final String LOGGEDUSER ="loguser" ;
    String oldEmail;
    String oldPassword;
    Button update, cancel;
    TextView fullName, emailtxt, phone, password, nameDescription;
    private Firebase mRef;
    User loggeduser;
    ImageView profpic;
    User usernew;
    String username;
    private static  int RESULT_LOAD_IMAGE = 1;
    User editLogUser;
    User changedLogUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setTitle("Edit Profile");
        Firebase.setAndroidContext(this);

        mRef = new Firebase("https://homework-8.firebaseio.com/users");
       Log.d("demo", "login email = " + mRef.getAuth().getProviderData().get("email") ) ;

        update = (Button) findViewById(R.id.updateProfile);
        cancel = (Button) findViewById(R.id.cancelUpdate);
        fullName = (TextView) findViewById(R.id.fullNameEditProfile);
        emailtxt = (TextView) findViewById(R.id.emailEditProfile);
        phone = (TextView) findViewById(R.id.phoneNumbeEditProfile);
        password = (TextView) findViewById(R.id.passwordEdit);
        nameDescription = (TextView) findViewById(R.id.nameDescription);
        profpic = (ImageView) findViewById(R.id.profileImage);

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                editLogUser = dataSnapshot.getValue(User.class);
                Log.d("demo", "child event listener logusers = " + editLogUser.toString());

                Log.d("demo", "provider    = " + mRef.getAuth().getProviderData().get("email"));

                if (editLogUser.getEmail().equals(mRef.getAuth().getProviderData().get("email"))) {
                    changedLogUser = editLogUser;

                    Log.d("demo", "Edit logged user " + editLogUser.toString());
                    fullName.setText(editLogUser.getFullname());
                    emailtxt.setText(editLogUser.getEmail());
                    phone.setText(editLogUser.getPhoneno().toString());
                    password.setText(editLogUser.getPassword().toString());
                    nameDescription.setText(editLogUser.getFullname());

                    byte[] decodedString = Base64.decode(editLogUser.getPicture(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    profpic.setImageBitmap(decodedByte);

                    Log.d("demo","changed user befoer assig mail = "+changedLogUser);
                    oldEmail = changedLogUser.getEmail();
                    oldPassword = changedLogUser.getPassword();

                    String sm = oldEmail;
                    String[] split = sm.split("@");
                    username = split[0];
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



        profpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef.changeEmail(oldEmail, oldPassword, emailtxt.getText().toString(), new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {

                    }
                });

                mRef.changePassword(oldEmail, oldPassword, password.getText().toString(), new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {


                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {

                    }
                });
                Toast.makeText(EditProfileActivity.this, "Saved successfully!", Toast.LENGTH_SHORT).show();

                String flname = fullName.getText().toString();
                String eml = emailtxt.getText().toString();
                String pwd = password.getText().toString();
                Integer ph = Integer.parseInt(phone.getText().toString());
                usernew = new User(flname,eml,pwd,changedLogUser.getPicture(),ph);

                Intent intent = new Intent(EditProfileActivity.this, ConversationsActivity.class);
                startActivity(intent);
                finish();

                mRef.child(username).setValue(usernew);
                Log.d("demo"," afetr click upate "+usernew.toString() );
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String flname = fullName.getText().toString();
                String eml = emailtxt.getText().toString();
                String pwd = password.getText().toString();
                Integer ph = Integer.parseInt(phone.getText().toString());
                usernew = new User(flname,eml,pwd,changedLogUser.getPicture(),ph);

                Log.d("demo"," oldEmail="+oldEmail);
                Log.d("demo"," usernew.getPassword()="+usernew.getPassword());
                Log.d("demo","usernew.getEmail()= "+usernew.getEmail());



                mRef.changeEmail(usernew.getEmail(), usernew.getPassword(), oldEmail, new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {

                    }
                });

                mRef.changePassword(usernew.getEmail(), usernew.getPassword(), oldPassword, new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {


                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {

                    }
                });
                mRef.child(username).setValue(changedLogUser);
                Intent intent = new Intent(EditProfileActivity.this, ConversationsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data){
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bm = BitmapFactory.decodeFile(picturePath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

            byte[] b = baos.toByteArray();

            String b64;

            b64 = Base64.encodeToString(b, Base64.DEFAULT);

            ImageView imageView = (ImageView) findViewById(R.id.profileImage);
            changedLogUser.setPicture(b64);
            Log.d("demo", changedLogUser.getPicture());
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
}
