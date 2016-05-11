/*
    Cole Howell, Manoj Bompada
    ConversationlistAdapter.java
    ITCS 4180
 */

package com.example.cole.homework8;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by Cole on 4/13/2016.
 */
public class ConversationslistAdapter extends ArrayAdapter<User> {

    Context mContext;
    List<User> mData;
    int mResource;
    Integer phnumber;

    public ConversationslistAdapter(Context context, int resource, List<User> objects) {
        super(context, resource, objects);

        this.mContext = context;
        this.mData = objects;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(mResource, parent, false);
            holder = new Holder();
            holder.contactPhoto = (ImageView) convertView.findViewById(R.id.profilepic);
            holder.contactName = (TextView) convertView.findViewById(R.id.fname);
            holder.read_status = (ImageView) convertView.findViewById(R.id.status);
            holder.phone = (ImageView) convertView.findViewById(R.id.phone);

            convertView.setTag(holder);

        }

        holder = (Holder) convertView.getTag();

        ImageView contactPhoto = holder.contactPhoto;

        Log.d("demo", mData.get(position).getPicture());

        byte[] decodedString = Base64.decode(mData.get(position).getPicture(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        contactPhoto.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 100, 100, false));

        TextView contactName = holder.contactName;
        contactName.setText(mData.get(position).getFullname());

        ImageView phone = holder.phone;
        phnumber = mData.get(position).getPhoneno();

        ImageView status = holder.read_status;

        status.setImageResource(R.drawable.red);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phnumber));

                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mContext.startActivity(intent);

            }
        });

        return convertView;
    }

    private class Holder{
        ImageView contactPhoto, read_status, phone;
        TextView contactName;
    }
}
