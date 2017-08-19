package com.example.leniovo.andelaproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

public class UserPageActivity extends AppCompatActivity {
 TextView usernameTExt,URLtext;
    ANImageView profileImage;
    Button shareButton;
    private ShareActionProvider shareActionProvider;
    String userNameParse = "Username", avatarParse = "avatar", htmlParse = "html";
   String username = "Username";
    String usernameString,ProfileURl;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        final Intent intent = getIntent();
        usernameString = intent.getStringExtra(userNameParse);
        ProfileURl = intent.getStringExtra(htmlParse);
        String avatarURl = intent.getStringExtra(avatarParse);
        usernameTExt = (TextView)findViewById(R.id.usernameID);
        URLtext = (TextView)findViewById(R.id.idURLlink);
        shareButton = (Button)findViewById(R.id.idSharebutton);
        profileImage = (ANImageView)findViewById(R.id.IdprofileImage);
        URLtext.setPaintFlags(URLtext.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        URLtext.setText(ProfileURl);
        usernameTExt.setText(username+" :@"+ usernameString);
        profileImage.setImageUrl(avatarURl);
        profileImage.setErrorImageResId(R.drawable.github_octocat);
        profileImage.setDefaultImageResId(R.drawable.github_image_first);
        final String shareMessage = "Check out this awesome developer " +
                "\n  @"+usernameString+" " +
                "\n on github via the link : "+ ProfileURl;




        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.setType("text/plain");
                intent1.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(intent1);
            }
        });


        URLtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = intentBuilder.build();
                customTabsIntent.launchUrl(context,Uri.parse(ProfileURl));

            }
        });
    }


}
