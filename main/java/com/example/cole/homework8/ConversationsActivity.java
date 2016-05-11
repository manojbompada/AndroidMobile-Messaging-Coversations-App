package com.example.cole.homework8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class ConversationsActivity extends AppCompatActivity {
    public static final String USEROBJ ="user" ;
    public static final String LOGGEDUSER = "logged user";
    public static final String CONTACTOBJ = "contact";
    String username;
    private Firebase mRef;
    ListView listView;
    ArrayList<User> userList = new ArrayList<User>();
    ConversationslistAdapter adapter;
    User userObj = new User();
    User luser;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);
        setTitle("Stay In Touch");
        Firebase.setAndroidContext(this);

        mRef = new Firebase("https://homework-8.firebaseio.com/");

        listView = (ListView) findViewById(R.id.listView);

        mRef.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                user = dataSnapshot.getValue(User.class);

                if (user.getEmail().equals(mRef.getAuth().getProviderData().get("email"))) {
                    userObj = user;
                }

                if (!user.getEmail().equals(mRef.getAuth().getProviderData().get("email"))) {

                    userList.add(user);
                    adapter.notifyDataSetChanged();
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

        adapter = new ConversationslistAdapter(this,R.layout.conversation_row_layout,userList);

                listView.setAdapter(adapter);
                adapter.setNotifyOnChange(true);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Log.d("demo", "Position : " + position + " value: " + userList.get(position).toString());
                                    Intent intent = new Intent(ConversationsActivity.this,ViewMessagesActivity.class);
                                    intent.putExtra(CONTACTOBJ,userList.get(position));
                                    intent.putExtra(LOGGEDUSER, userObj);
                                    startActivity(intent);
                        }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.layout, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
                case R.id.action_edit_profile:
                    Intent intent = new Intent(ConversationsActivity.this, EditProfileActivity.class);

                    Log.d("demo","starting edit activity");
                    startActivity(intent);
                    return true;
                case R.id.action_logout:
                    mRef.child("users").unauth();
                    Intent intent1 = new Intent(ConversationsActivity.this, MainActivity.class);
                    startActivity(intent1);
                    finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);

        }

    }
}
