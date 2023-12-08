package com.mkandeel.retrofit_rxjava_.Adapters;

import static com.mkandeel.retrofit_rxjava_.Helper.Constants.CLICKEDKEY;
import static com.mkandeel.retrofit_rxjava_.Helper.Constants.POSTID;
import static com.mkandeel.retrofit_rxjava_.Helper.Constants.USERID;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.mkandeel.retrofit_rxjava_.ClickListener;
import com.mkandeel.retrofit_rxjava_.Pojo.PostsModel;
import com.mkandeel.retrofit_rxjava_.Pojo.UsersModel;
import com.mkandeel.retrofit_rxjava_.R;
import com.mkandeel.retrofit_rxjava_.ViewModel.UserViewModel;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private List<PostsModel> list;
    private Context context;
    private Fragment fragment;
    private ClickListener listener;
    private UserViewModel userViewModel;

    public PostsAdapter(Fragment fragment, List<PostsModel> list, ClickListener listener) {
        this.fragment = fragment;
        this.context = fragment.getContext();
        this.list = list;
        this.listener = listener;
        this.userViewModel = new ViewModelProvider(fragment).get(UserViewModel.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void Bind(@NonNull ViewHolder holder, int position) {
        userViewModel.getDataUserByID(list.get(position).getUserId());
        userViewModel.getUsersData().observe(this.fragment.requireActivity(), new Observer<UsersModel>() {
            @Override
            public void onChanged(UsersModel usersModel) {
                if (list.get(position).getUserId() == usersModel.getId()) {
                    holder.txt_post_title.setText(list.get(position).getTitle());
                    holder.txt_user.setText(usersModel.getName());
                    holder.txt_user.setTextColor(Color.rgb(0,0,255));
                    holder.txt_post_body.setText(list.get(position).getBody());
                    holder.txt_comments.setText(context.getResources()
                            .getString(R.string.comments));
//                    holder.txt_id.setText(String.valueOf(list.get(position).getId()));
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_user, txt_post_title, txt_post_body, txt_comments;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_user = itemView.findViewById(R.id.txt_userName);
            txt_post_title = itemView.findViewById(R.id.txt_post_title);
            txt_post_body = itemView.findViewById(R.id.txt_post_body);
            txt_comments = itemView.findViewById(R.id.txt_comments);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extra = new Bundle();
                    extra.putString(CLICKEDKEY, "itemClicked");
                    // open this post in fragment
                    extra.putInt(POSTID,list.get(getAdapterPosition()).getId());
                    listener.onItemClickListener(getAdapterPosition(), extra);
                }
            });

            txt_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extra = new Bundle();
                    extra.putString(CLICKEDKEY,"UserClicked");
                    extra.putInt(USERID,list.get(getAdapterPosition()).getUserId());
                    listener.onItemClickListener(getAdapterPosition(),extra);
                }
            });

            txt_comments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extra = new Bundle();
                    extra.putString(CLICKEDKEY, "CommentsClicked");
                    // get all comments for this post
                    extra.putInt(POSTID,list.get(getAdapterPosition()).getId());
                    listener.onItemClickListener(getAdapterPosition(), extra);
                }
            });


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Bundle extra = new Bundle();
                    extra.putString(CLICKEDKEY, "itemLongClicked");

                    extra.putInt(POSTID,list.get(getAdapterPosition()).getId());
                    listener.onLongItemClickListener(getAdapterPosition(), extra);
                    return true;
                }
            });
        }
    }
}
