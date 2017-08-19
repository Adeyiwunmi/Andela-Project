package com.example.leniovo.andelaproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_POSITION ="position" ;
    private ArrayList<User> users;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private UserAdapter userAdapter;
    private final String URL_githubUsers = "https://api.github.com/search/users?q=location:lagos+language:java";
    private final String Tag = "Get Lagos programmers ";
    String userNameParse = "Username", avatarParse = "avatar", htmlParse = "html";
    private static final String KEY_USERSLIST= "userslist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.IDrecyclerView);
     if (savedInstanceState != null){
         users = savedInstanceState.getParcelableArrayList(KEY_USERSLIST);
         setUpRecycler(users);
         recyclerView.scrollToPosition(savedInstanceState.getInt(KEY_POSITION));

     } else {

         checkAndLoad();
     }

    }

    private void setUpRecycler(ArrayList<User> userArrayList) {

        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        userAdapter = new UserAdapter(users, getApplicationContext());
        recyclerView.setAdapter(userAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
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

    }
private  void loadFromNetwork(){


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
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                            String username = jsonObject1.getString("login");
                            String avatar = jsonObject1.getString("avatar_url");
                            String html = jsonObject1.getString("html_url");
                            User user = new User(username, avatar, html);
                            users.add(user);
                        }
                        setUpRecycler(users);

                    } catch (JSONException e) {
                        e.printStackTrace();

                        Toast.makeText(getApplicationContext(), "unable to parse", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onError(ANError anError) {
                }
            });

}

private void checkAndLoad(){
    if (Utility.isNetworkConnected(getApplicationContext())){
        loadFromNetwork();
    }
    else {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(getString(R.string.no_network_alert));
        builder.setTitle(getString(R.string.no_network_title));
        builder.setCancelable(false);
        builder.setPositiveButton(getString(R.string.try_again), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
           checkAndLoad();
            }
        });
        builder.setNegativeButton(getString(R.string.end_actv), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
           finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_USERSLIST, users);
      outState.putInt(KEY_POSITION,recyclerView.getVerticalScrollbarPosition());
    }
}
