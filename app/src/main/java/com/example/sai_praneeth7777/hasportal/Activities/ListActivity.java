package com.example.sai_praneeth7777.hasportal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sai_praneeth7777.hasportal.Adapters.AdapterClass;
import com.example.sai_praneeth7777.hasportal.Objects.ObjectClass;
import com.example.sai_praneeth7777.hasportal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sai_praneeth7777 on 11-Jun-16.
 */
public class ListActivity extends AppCompatActivity {
    ListView listView1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        String type = i.getStringExtra("type");
        fetchData(type);

        listView1 = (ListView) findViewById(R.id.listView);
        //  listView1.setOnItemClickListener(new ListAction());
    }

    JSONObject obj;
    JSONArray arr;
    AdapterClass content_adapter;
    ListView listView;


    private void fetchData(String type) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = getString(R.string.url_fetch_list);
        Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + type,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //                          Toast.makeText(ListActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
                        content_adapter = new AdapterClass(ListActivity.this, R.layout.list);
                        listView = (ListView) findViewById(R.id.listView);
                        //  listView.setOnItemClickListener(new ListAction());
                        listView.setAdapter(content_adapter);
                        try {
                            arr = new JSONArray(response.toString());
                            String name;
                            for (int i = 0; i < arr.length(); i++) {
                                obj = arr.getJSONObject(i);
                                name = obj.getString("name");
                                ObjectClass content = new ObjectClass(name);
                                content_adapter.add(content);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
//
//    class ListAction implements AdapterView.OnItemClickListener{
//      @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
//          ViewGroup vg =(ViewGroup)view;
//          Button bt = (Button) findViewById(R.id.mess);
//          Toast.makeText(ListActivity.this,bt.getText().toString(),Toast.LENGTH_SHORT).show();
//      }
//    };


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
