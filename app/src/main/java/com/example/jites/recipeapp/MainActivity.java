package com.example.jites.recipeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText edit;
    Button mButton;
    CheckBox check1, check2, check3,check4;
    JSONArray hits;

    final String app_id = "b1c20ee0";
    final String app_key = "5222b593e391b606d16ef8bfbc449c27";
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = findViewById(R.id.editText);
        mButton = findViewById(R.id.search);
        check1 = findViewById(R.id.checkBox1);
        check2 = findViewById(R.id.checkBox2);
        check3 = findViewById(R.id.checkBox3);
        check4 = findViewById(R.id.checkBox4);


        mQueue = Volley.newRequestQueue(this);
        mButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Log.v("EditText", edit.getText().toString());
                        String api_url = "https://api.edamam.com/search?" + "app_id=" + app_id + "&app_key=" + app_key + "&q=" + edit.getText().toString();
                        jsonParse(api_url);

                    }
                });


    }

    private void jsonParse(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray hits = response.getJSONArray("hits");
                    //Log.v("Length", Integer.toString(hits.length()));
                        for (int i=0;i<hits.length();i++){
                            JSONObject recipe = hits.getJSONObject(i);
                            int totalTime = recipe.getJSONObject("recipe").getInt("totalTime");

                            if(check1.isChecked()){
                                if(totalTime > 0 && totalTime <= 30){
                                    Log.v("Recipe: ",recipe.getJSONObject("recipe").getString("label"));
                                    Log.v("Total Time: ", Integer.toString(recipe.getJSONObject("recipe").getInt("totalTime")));
                                }

                            }
                            if(check2.isChecked()){
                                if(totalTime > 30 && totalTime <= 90){
                                    Log.v("Recipe: ",recipe.getJSONObject("recipe").getString("label"));
                                    Log.v("Total Time: ", Integer.toString(recipe.getJSONObject("recipe").getInt("totalTime")));
                                }

                            }
                            if(check3.isChecked()){
                                if(totalTime > 90 && totalTime <= 180){
                                    Log.v("Recipe: ",recipe.getJSONObject("recipe").getString("label"));
                                    Log.v("Total Time: ", Integer.toString(recipe.getJSONObject("recipe").getInt("totalTime")));
                                }

                            }
                            if(check4.isChecked()){
                                if(totalTime > 180){
                                    Log.v("Recipe: ",recipe.getJSONObject("recipe").getString("label"));
                                    Log.v("Total Time: ", Integer.toString(recipe.getJSONObject("recipe").getInt("totalTime")));
                                }

                            }
                            //Log.v("Recipe: ",recipe.getJSONObject("recipe").getString("label"));
                            //Log.v("Total time: ", recipe.getJSONObject("recipe").getString("totalTime"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
            }
        });
        mQueue.add(jsonObjectRequest);
        //return hits;
    }

}
