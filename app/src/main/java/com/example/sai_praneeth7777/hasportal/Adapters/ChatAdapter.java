package com.example.sai_praneeth7777.hasportal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sai_praneeth7777.hasportal.Objects.ChatObject;

import com.example.sai_praneeth7777.hasportal.Objects.ThreadObject;
import com.example.sai_praneeth7777.hasportal.R;
import com.example.sai_praneeth7777.hasportal.UtilStrings;
import com.example.sai_praneeth7777.hasportal.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sai_praneeth7777 on 29-Jun-16.
 */

public class ChatAdapter extends ArrayAdapter {
    List list= new ArrayList<ThreadObject>();
    public ChatAdapter(Context context, int resource) {
        super(context, resource);
    }
    public void add(ChatObject object){
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row= convertView;
        contentHolder contentHolder;
        final ChatObject content=(ChatObject) this.getItem(position);
        if(row == null){
            LayoutInflater layoutInflater =(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final String roll_no = Utils.getprefString(UtilStrings.ROLLNO, this.getContext());
            //Toast.makeText(this.getContext(),"1:"+content.getUser()+"::2:"+roll_no,Toast.LENGTH_LONG).show();
            if (content.getUser().equals(roll_no)) {
                row = layoutInflater.inflate(R.layout.message_item_right, parent, false);
                contentHolder = new contentHolder();
                contentHolder.txbody = (TextView) row.findViewById(R.id.txtMsg);
                contentHolder.txuser = (TextView) row.findViewById(R.id.lblMsgYou);
                contentHolder.txtime = (TextView) row.findViewById(R.id.lblMsgDateright);
                row.setTag(contentHolder);
            }
            else{
                row = layoutInflater.inflate(R.layout.message_item_left, parent, false);
                contentHolder = new contentHolder();
                contentHolder.txbody = (TextView) row.findViewById(R.id.txtMsg);
                contentHolder.txuser = (TextView) row.findViewById(R.id.lblMsgFrom);
                contentHolder.txtime = (TextView) row.findViewById(R.id.lblMsgDateleft);
                row.setTag(contentHolder);
            }
        }
        else {
            contentHolder = (contentHolder) row.getTag();
        }



        contentHolder.txbody.setText(content.getBody());
        contentHolder.txtime.setText(content.getDate()+" "+content.getTime());
        contentHolder.txuser.setText(content.getUser());

        return row;
    }

    static class contentHolder{
        TextView txbody;
        TextView txuser;
        TextView txtime;
    }
}
