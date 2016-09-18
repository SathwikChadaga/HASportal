package com.example.sai_praneeth7777.hasportal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sai_praneeth7777.hasportal.Adapters.AdapterClass;
import com.example.sai_praneeth7777.hasportal.Adapters.ThreadAdapter;
import com.example.sai_praneeth7777.hasportal.Objects.ObjectClass;
import com.example.sai_praneeth7777.hasportal.Objects.ThreadObject;
import com.example.sai_praneeth7777.hasportal.R;
import com.example.sai_praneeth7777.hasportal.UtilStrings;
import com.example.sai_praneeth7777.hasportal.Utils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sai_praneeth7777 on 18-Jun-16.
 */
public class ThreadActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threadlist);
        getContent();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(ThreadActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ThreadActivity.this, MainActivity.class);
        startActivity(i);
    }

    JSONObject obj;
    JSONArray arr;
    ThreadAdapter content_adapter;
    ListView listView;

    private void getContent() {
        final String roll_no = Utils.getprefString(UtilStrings.ROLLNO, this);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = getString(R.string.url_get_threads);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //Toast.makeText(ThreadActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
                        content_adapter = new ThreadAdapter(ThreadActivity.this, R.layout.threadlistrow);
                        listView = (ListView) findViewById(R.id.threadList12);
                        //  listView.setOnItemClickListener(new ListAction());

                        listView.setAdapter(content_adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                TextView textViewId = (TextView) view.findViewById(R.id.thread_id);
                                String thread_id = textViewId.getText().toString();
                                //Toast.makeText(view.getContext(),text,Toast.LENGTH_SHORT).show();
                                getThreadMessages(thread_id);
                            }
                        });

                        try {
                            arr = new JSONArray(response.toString());
                            String subject, user, date, time, body, thread_id, messName;
                            for (int i = 0; i < arr.length(); i++) {
                                obj = arr.getJSONObject(i);
                                subject = obj.getString("subject");
                                body = obj.getString("body");
                                user = obj.getString("user");
                                date = obj.getString("date");
                                time = obj.getString("time");
                                thread_id = obj.getString("thread_id");
                                messName = obj.getString("mess_name");
                                ThreadObject content = new ThreadObject(subject, body, date, time, user, thread_id, messName);
                                content_adapter.add(content);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ThreadActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rollno", roll_no);
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

    private void getThreadMessages(final String thread_id) {
        Intent i = new Intent(this, MessageChatActivity.class);
        i.putExtra("thread_id", thread_id);
        startActivity(i);
    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        refreshList();
    }

    public void refreshList() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);

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
