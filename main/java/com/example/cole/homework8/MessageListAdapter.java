/*
    Cole Howell, Manoj Bompada
    MessageListAdapter.java
    ITCS 4180
 */

package com.example.cole.homework8;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Manoj on 4/16/2016.
 */
public class MessageListAdapter extends ArrayAdapter<Message> {
    Context mContext;
    List<Message> mData;
    int mResource;

    public MessageListAdapter(Context context, int resource, List<Message> objects) {
        super(context, resource, objects);

        this.mContext = context;
        this.mData = objects;
        this.mResource = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(mResource, parent, false);
            holder = new Holder();
            holder.hdelbtn = (ImageView) convertView.findViewById(R.id.delimageView);
            holder.hflname = (TextView) convertView.findViewById(R.id.flname);
            holder.hmsg = (TextView) convertView.findViewById(R.id.msg);
            holder.htimestmp = (TextView) convertView.findViewById(R.id.datetxt);

            convertView.setTag(holder);
        }

        holder = (Holder) convertView.getTag();

        TextView flname = holder.hflname;
        flname.setText(mData.get(position).getSender());

        TextView msg = holder.hmsg;
        msg.setText(mData.get(position).getMsg_txt());

        TextView timestmp = holder.htimestmp;
        timestmp.setText(mData.get(position).getTimestamp());

        ImageView delbtn = holder.hdelbtn;

        if(ViewMessagesActivity.msgloguser.fullname.equals(mData.get(position).getSender())){
            convertView.setBackgroundColor(Color.parseColor("#D3D3D3"));
            delbtn.setImageResource(R.drawable.delete1);

            Map<String, String> post = new HashMap<String, String>();

        }

        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewMessagesActivity.mRefmsg.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        Message msg = dataSnapshot.getValue(Message.class);

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
                });;

            }
        });


        return convertView;
    }

    private class Holder{
        ImageView hdelbtn;
        TextView hflname,hmsg,htimestmp;
    }
}