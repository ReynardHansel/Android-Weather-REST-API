package com.example.weatherapiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    //** Get/list the components
    //* The component names don't have to be the same/match the button IDs/names in the xml layout */
    Button btn_getDog, btn_getBreedInfo, btn_getBreedList;
    EditText et_dataInput;
    ListView lv_breedList;
    ImageView iv_dogImage;

    String currentBreed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //* Assign values to each control/components on the layout
        btn_getDog = findViewById(R.id.btn_getDog);
        btn_getBreedInfo = findViewById(R.id.btn_getBreedInfo);
        btn_getBreedList = findViewById(R.id.btn_getBreedList);

        et_dataInput = findViewById(R.id.et_dataInput);
        lv_breedList = findViewById(R.id.lv_breedList);
        iv_dogImage = findViewById(R.id.iv_dogImage);

        // String url = "";

        //* Click listeners for each button
        btn_getDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                String url;
                String input = et_dataInput.getText().toString();

                if (input.isEmpty()) {
                    url = "https://dog.ceo/api/breeds/image/random";
                } else {
                    url = "https://dog.ceo/api/breed/" + input + "/images/random";
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        
                        try {
                            String imageUrl = response.getString("message");
                            Glide.with(MainActivity.this).load(imageUrl).into(iv_dogImage);
                            // Toast.makeText(MainActivity.this, imageUrl, Toast.LENGTH_SHORT).show();

                            // Extract breed from the imageUrl
                            String[] parts = imageUrl.split("/");
                            currentBreed = parts[parts.length - 2]; // The breed is the second last part of the URL
                        } catch (JSONException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Dog Breed not found",Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(request);

//                Toast.makeText(MainActivity.this, "You clicked me", Toast.LENGTH_SHORT).show();
            }
        });
        
        btn_getBreedInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "This is a " + currentBreed, Toast.LENGTH_SHORT).show();
            }
        });
        
        btn_getBreedList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You typed: " + et_dataInput.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}