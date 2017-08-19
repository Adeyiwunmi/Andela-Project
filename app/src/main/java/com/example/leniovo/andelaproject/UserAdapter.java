package com.example.leniovo.andelaproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import java.util.ArrayList;

/**
 * Created by LENIOVO on 4/19/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    ArrayList<User> userList ;
    Context context;

    public UserAdapter(ArrayList<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }


    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.user_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.profileNameText.setText(user.getUsername());
        holder.profilePhotoImage.setDefaultImageResId(R.drawable.github_image_first);
        holder.profilePhotoImage.setErrorImageResId(R.drawable.github_octocat);
        holder.profilePhotoImage.setImageUrl(user.getAvatarURl());
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ANImageView profilePhotoImage;
        public TextView profileNameText;


        public ViewHolder(View itemView) {
            super(itemView);
            profileNameText = (TextView) itemView.findViewById(R.id.IDTextView);
            profilePhotoImage = (ANImageView)itemView.findViewById(R.id.IDimageView);
        }
    }
}
