package com.example.sai_praneeth7777.hasportal.Adapters;

/**
 * Created by sai_praneeth7777 on 18-Jun-16.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sai_praneeth7777.hasportal.Activities.ComplaintActivity;
import com.example.sai_praneeth7777.hasportal.Objects.ObjectClass;
import com.example.sai_praneeth7777.hasportal.Objects.ThreadObject;
import com.example.sai_praneeth7777.hasportal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sai_praneeth7777 on 13-Jun-16.
 */
public class ThreadAdapter extends ArrayAdapter {
    List list= new ArrayList<ThreadObject>();
    public ThreadAdapter(Context context, int resource) {
        super(context, resource);
    }
    public void add(ThreadObject object){
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

        if(row == null){
            LayoutInflater layoutInflater =(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.threadlistrow,parent,false);
            contentHolder = new contentHolder();
            contentHolder.txsubject = (TextView) row.findViewById(R.id.threadSubject);
            contentHolder.txbody = (TextView) row.findViewById(R.id.threadBody);
            contentHolder.txdate = (TextView) row.findViewById(R.id.threadDate);
            contentHolder.txtime = (TextView) row.findViewById(R.id.threadTime);
            contentHolder.txthread_id= (TextView)row.findViewById(R.id.thread_id);
            contentHolder.txmessname = (TextView)row.findViewById(R.id.threadName);
            contentHolder.txuser = (TextView) row.findViewById(R.id.threadUser);
            row.setTag(contentHolder);
        }
        else {
            contentHolder = (contentHolder) row.getTag();
        }

        final ThreadObject content=(ThreadObject) this.getItem(position);
        contentHolder.txsubject.setText(content.getSubject());
        contentHolder.txbody.setText(content.getBody());
        contentHolder.txtime.setText(content.getTime());
        contentHolder.txdate.setText(content.getDate());
        //contentHolder.txuser.setText(content.getUser());
        contentHolder.txthread_id.setText(content.getId());
        contentHolder.txmessname.setText(content.getMessName());
        contentHolder.txuser.setText(content.getUser());
        return row;
    }

    static class contentHolder{
        TextView txsubject;
        TextView txbody;
        TextView txdate;
        TextView txtime;
        TextView txthread_id;
        TextView txmessname;
        TextView txuser;
    }
}
