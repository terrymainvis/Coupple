package com.mycompany.coupple;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class DefineSOActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String name="";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_so);

        try {
            JSONObject obj = new JSONObject(getIntent().getStringExtra("json"));
            name = obj.optString("name");
            Log.i("DefineSOActivity", name);
        } catch (JSONException e) {
            name = "erreur";
            e.printStackTrace();
        }
        TextView textView = new TextView(this);
        textView.setText(name);
        TextView nameContainer = (TextView) findViewById(R.id.user_name);
        nameContainer.setText(name);
    }


   /* public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("DefineSOActivity", "test");
        String name="";
        View view = inflater.inflate(R.layout.activity_define_so, container, false);

        try {
            JSONObject obj = new JSONObject(getIntent().getStringExtra("json"));
            name = obj.optString("name");
            Log.i("DefineSOActivity", name);
        } catch (JSONException e) {
            name = "erreur";
            e.printStackTrace();
        }
        TextView textView = new TextView(this);
        textView.setText(name);
        TextView nameContainer = (TextView) view.findViewById(R.id.user_name);
        nameContainer.setText(name);

        return view;
    }*/

}
