package com.ashish.covid19app;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.w3c.dom.Text;

public class DetatilActivity extends AppCompatActivity {
  private  int positionStates;
  TextView active,confirmed,recovered,deaths;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detatil);
        Intent intent=getIntent();
        positionStates=intent.getIntExtra("position",0);

        getSupportActionBar().setTitle("Details of " +affectedStates.stateModelList.get(positionStates).getLoc());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        active=findViewById(R.id.tvactivecases);
        confirmed=findViewById(R.id.tvConfirmed);
        recovered=findViewById(R.id.tvRecovered);
        deaths=findViewById(R.id.deaths);
        //getting data with the help of statemodel class and getter and setter
     active.setText(affectedStates.stateModelList.get(positionStates).getActive());
       confirmed.setText(affectedStates.stateModelList.get(positionStates).getConfirmed());
        recovered.setText(affectedStates.stateModelList.get(positionStates).getRecovered());
        deaths.setText(affectedStates.stateModelList.get(positionStates).getDeaths());



    }
    //to show the back item in action bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
