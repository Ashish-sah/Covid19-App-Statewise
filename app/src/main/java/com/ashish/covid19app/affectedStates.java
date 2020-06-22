 package com.ashish.covid19app;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.util.Locale.filter;

 public class affectedStates extends AppCompatActivity {
    EditText edtSearch;
    ListView listView;
    SimpleArcLoader simpleArcLoader;
    //data from api come inside this list
    public static List<StateModel> stateModelList=new ArrayList<>();
    // calling object of StateModel Class and custom adapter
    StateModel stateModel;
     MyCustomAdapter myCustomAdapter;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_states);
        edtSearch = findViewById(R.id.edtSearch);
        listView = findViewById(R.id.listView);
        simpleArcLoader = findViewById(R.id.loader);

        getSupportActionBar().setTitle("Covid19 Data Statewise");
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         getSupportActionBar().setDisplayShowHomeEnabled(true);
        fetchData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            startActivity(new Intent(getApplicationContext(),DetatilActivity.class).putExtra("position",position));
            }
        });
        //code to search
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myCustomAdapter.getFilter().filter(s);
                myCustomAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



     private void fetchData() {
        String url="https://api.covidindiatracker.com/state_data.json";
        simpleArcLoader.start();
         StringRequest request=new StringRequest(Request.Method.GET, url,
                 new Response.Listener<String>() {
                     @Override
                     public void onResponse(String response) {

                         try {
                          JSONArray   jsonArray = new JSONArray(response);
                             for (int i=0;i<jsonArray.length();i++) {
                                 JSONObject jsonObject = jsonArray.getJSONObject(i);
                                 String loc = jsonObject.getString("state");
                                 String active=jsonObject.getString("active");
                                 String confirmed=jsonObject.getString("confirmed");
                                 String recovered=jsonObject.getString("recovered");
                                 String deaths=jsonObject.getString("deaths");

                                 //calling object of StateModel
                                 stateModel = new StateModel(loc, active,confirmed,recovered, deaths);
                                 stateModelList.add(stateModel);
                             }
                                 //initializing custom adapter and adding to listview to show data
                                 myCustomAdapter=new MyCustomAdapter(affectedStates.this,stateModelList);
                                 listView.setAdapter(myCustomAdapter);
                                 simpleArcLoader.stop();
                                 simpleArcLoader.setVisibility(View.GONE);

                         } catch (JSONException e) {
                             e.printStackTrace();
                             simpleArcLoader.stop();
                             simpleArcLoader.setVisibility(View.GONE);
                         }
                     }
                 }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Toast.makeText(affectedStates.this,error.getMessage(),Toast.LENGTH_LONG).show();

             }
         });
         //Now we declare queue  to handle the above response
         RequestQueue requestQueue= Volley.newRequestQueue(this);
         requestQueue.add(request);
         //the data which we get goes to above onResponse method which is in json format
     }
     //to show the b   ack item in action bar
     @Override
     public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         if(item.getItemId()==android.R.id.home)
             finish();
         return super.onOptionsItemSelected(item);
     }

 }
