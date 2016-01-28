package ca.mcgill.ecse428.restoguys.connoisseur;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class History extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        ListView listView = (ListView) findViewById(R.id.listView);
        //have list here for now just to display something , later will be retrieved from db or file
        String[] restaurantArray = {"Resto1","Resto2","Resto3"};
        ArrayAdapter adapter = new ArrayAdapter<String>(this,  R.layout.activity_listview, restaurantArray);
        listView.setAdapter(adapter);


    }

    public void openRestoinfo(View view) {
        TextView t = (TextView) view;
        CharSequence resto = t.getText();
    }

}
