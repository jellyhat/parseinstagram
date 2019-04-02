package com.example.parseinstagram.fragments;

import android.util.Log;

import com.example.parseinstagram.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends PostsFragment {
    private String TAG = "ProfileFragment";


    protected void queryPosts(){
        adapter.clear();
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Query Error");
                    return;
                }
                mPosts.addAll(posts);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                for(int i =0; i < posts.size(); i++) {
                    Log.d(TAG, "Post:" + posts.get(i).getKeyDescription() );
                }
            }
        });
    }
}
