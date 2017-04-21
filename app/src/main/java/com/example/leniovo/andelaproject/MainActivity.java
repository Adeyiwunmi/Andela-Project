package com.example.leniovo.andelaproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
 ArrayList<User> users ;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    UserAdapter userAdapter;
    final String URL_githubUsers = "https://api.github.com/search/users?q=location:lagos+language:java";
    final String Tag = "Get Lagos programmers ";
    String userNameParse = "Username", avatarParse = "avatar", htmlParse = "html";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setTitle("Getting Java Programmers in lagos");
        progressDialog.setCancelable(false);
        progressDialog.show();
        AndroidNetworking.initialize(getApplicationContext());

        AndroidNetworking.get(URL_githubUsers)
                .setPriority(Priority.HIGH)
                .setTag(Tag)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("items");
                            users = new ArrayList<User>();
                            for(int  i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                                String username = jsonObject1.getString("login");
                                String avatar = jsonObject1.getString("avatar_url");
                                String html = jsonObject1.getString("html_url");
                                User user = new User(username,avatar,html);
                                users.add(user);
                            }

                            recyclerView = (RecyclerView)findViewById(R.id.IDrecyclerView);
                            recyclerView.setHasFixedSize(true);
                            linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(linearLayoutManager);
                            userAdapter = new UserAdapter(users, getApplicationContext());
                            recyclerView.setAdapter(userAdapter);
                            progressDialog.hide();
                           recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                               @Override
                               public void onItemClick(View view, int position) {
                                   User userSend = users.get(position);

                                   Intent intent = new Intent(getApplicationContext(), UserPageActivity.class);
                                   intent.putExtra(userNameParse, userSend.getUsername());
                                   intent.putExtra(avatarParse, userSend.getAvatarURl());
                                   intent.putExtra(htmlParse, userSend.getProfileUrl());
                                   startActivity(intent);
                               }
                           }));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.hide();
                            Toast.makeText(getApplicationContext(), "unable to parse", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), anError.toString(), Toast.LENGTH_LONG).show();
                        progressDialog.hide();
                    }
                });
    }
}
