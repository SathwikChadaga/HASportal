package com.example.sai_praneeth7777.hasportal.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sai_praneeth7777.hasportal.Activities.ComplaintActivity;
import com.example.sai_praneeth7777.hasportal.Objects.ObjectClass;
import com.example.sai_praneeth7777.hasportal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sai_praneeth7777 on 13-Jun-16.
 */
public class AdapterClass extends ArrayAdapter {
    List list = new ArrayList<ObjectClass>();

    public AdapterClass(Context context, int resource) {
        super(context, resource);
    }

    public void add(ObjectClass object) {
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
        row = convertView;
        contentHolder contentHolder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.listrow, parent, false);
            contentHolder = new contentHolder();
            contentHolder.txname = (TextView) row.findViewById(R.id.mess);
            final TextView btn = (TextView) row.findViewById(R.id.mess);
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), ComplaintActivity.class);
                    i.putExtra("name", btn.getText().toString());
                    v.getContext().startActivity(i);
                    Toast.makeText(v.getContext(), btn.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            row.setTag(contentHolder);
        } else {
            contentHolder = (contentHolder) row.getTag();
        }

        ObjectClass content = (ObjectClass) this.getItem(position);
        contentHolder.txname.setText(content.getName());
        return row;
    }

    static class contentHolder {
        TextView txname;
    }
}
