/*
    Cole Howell, Manoj Bompada
    ViewMessagesActivity.java
    ITCS 4180
 */

package com.example.cole.homework8;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ViewMessagesActivity extends AppCompatActivity {

    public static final String CONTACTUSER = "contact" ;
    public static final String LOGUSER = "user" ;
    public static User msgloguser;
    private User msgcontactobj;
    ListView msglistView;
    Button sndBtn;
    EditText sendmsg;
    MessageListAdapter adapter;
    ArrayList<Message> msgList = new ArrayList<Message>();
    public static Firebase mRefmsg;
    Message msgobj;
    Message msg;
    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_messages);

        Firebase.setAndroidContext(this);
        mRefmsg = new Firebase("https://homework-8.firebaseio.com/Messages/");

        if (getIntent().getExtras() != null) {
            msgloguser = (User) getIntent().getExtras().getSerializable(ConversationsActivity.LOGGEDUSER);

            msgcontactobj = (User) getIntent().getExtras().getSerializable(ConversationsActivity.CONTACTOBJ);
        }

        setTitle(msgcontactobj.fullname);

        msglistView = (ListView) findViewById(R.id.msglistView);
        sndBtn = (Button) findViewById(R.id.sendbutton);
        sendmsg = (EditText) findViewById(R.id.sendmsgtextView);

        mRefmsg.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                msg = dataSnapshot.getValue(Message.class);

                if ((msg.getSender().equals(msgloguser.getFullname()) && msg.getReceiver().equals(msgcontactobj.getFullname())) ||
                        (msg.getSender().equals(msgcontactobj.getFullname()) && msg.getReceiver().equals(msgloguser.getFullname()))) {

                    msgList.add(msg);

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
                msgList.remove(msg);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        sndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> post = new HashMap<String, String>();

                msgobj = new Message(timeStamp, false, sendmsg.getText().toString(), msgcontactobj.getFullname(), msgloguser.getFullname());

                mRefmsg.push().setValue(msgobj);
            }
        });

        adapter = new MessageListAdapter(this,R.layout.message_row_layout,msgList);
        msglistView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.viewmsgmenulayout, menu);


        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view_contact:

                Intent intent = new Intent(ViewMessagesActivity.this, ViewContactsActivity.class);
                intent.putExtra(CONTACTUSER, msgcontactobj);
                intent.putExtra(LOGUSER, msgloguser);
                startActivity(intent);
                return true;

            case R.id.action_call_contact:

                Intent intentcall = new Intent(Intent.ACTION_CALL);
                intentcall.setData(Uri.parse("tel:" + msgcontactobj.getPhoneno()));

                if (ActivityCompat.checkSelfPermission(ViewMessagesActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                     TODO: Consider calling
//                        ActivityCompat#requestPermissions
//                     here to request the missing permissions, and then overriding
//                       public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                                              int[] grantResults)
//                     to handle the case where the user grants the permission. See the documentation
//                     for ActivityCompat#requestPermissions for more details.
                }

                startActivity(intentcall);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
