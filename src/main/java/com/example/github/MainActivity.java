package com.example.github;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

    TextView result;
    EditText name;
    Button btn;
    ImageView img;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        result = findViewById(R.id.text);
        img = findViewById(R.id.img);

        requestQueue = Volley.newRequestQueue(MainActivity.this);
    }

    public void submit(View view) {
        gitHubApi();
    }

    private void gitHubApi() {

        if(!name.getText().toString().trim().isEmpty()) {
            String url = "https://api.github.com/users/" + name.getText().toString().trim();
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String res = "";
                        String url = response.getString("url");
                        res += "REPO URL IS" + url + ",";
                        String name = response.getString("login");
                        res += "Name is " + name + ",";
                        String email = response.getString("email");
                        res += "Email is " + email;
                        Glide.with(img).load(response.getString("avatar_url")).into(img);
                        result.setText(res);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this,"Sorry api failed",Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        } else {
            Toast.makeText(MainActivity.this,"Please select your repo name",Toast.LENGTH_SHORT).show();
        }
    }
}