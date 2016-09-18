package com.example.sai_praneeth7777.hasportal.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sai_praneeth7777.hasportal.R;
import com.example.sai_praneeth7777.hasportal.UtilStrings;
import com.example.sai_praneeth7777.hasportal.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sai_praneeth7777 on 15-Jun-16.
 */
public class ComplaintActivity extends AppCompatActivity {
    private TextView subject;
    private TextView body, header;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaint);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent i = getIntent();
        String name = i.getStringExtra("name");
        setHead(name);
    }

    private void setHead(String name) {
        TextView txt = (TextView) findViewById(R.id.typename);
        txt.setText(name);
    }

    public void onPostClick(View v) {
        subject = (TextView) findViewById(R.id.tit);
        body = (TextView) findViewById(R.id.desc);
        header = (TextView) findViewById(R.id.typename);
        //Toast.makeText(ComplaintActivity.this,subject.toString().trim(),Toast.LENGTH_SHORT);

        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();

        postMail(subject.getText().toString(), body.getText().toString(), header.getText().toString());


        // To dismiss the dialog
        progress.dismiss();
    }

    private void postMail(final String sub, final String body, final String messname) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = getString(R.string.url_complaint_post);
        // Request a string response from the provided URL.
        final String roll_no = Utils.getprefString(UtilStrings.ROLLNO, this);
        final String name = Utils.getprefString(UtilStrings.NAME, this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Toast.makeText(ComplaintActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ComplaintActivity.this, ThreadActivity.class);
                        startActivity(i);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ComplaintActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sub", sub);
                params.put("body", body);
                params.put("messname", messname);
                params.put("name", name);
                params.put("roll_no", roll_no);
                return params;
            }
        };
        // Add the request to the RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        int MY_SOCKET_TIMEOUT_MS = 10000;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
