package com.example.parseinstagram.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parseinstagram.Post;
import com.example.parseinstagram.PostsAdapter;
import com.example.parseinstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {
    private String TAG = "PostsFragment";
    protected SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> mPosts;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvPosts = view.findViewById(R.id.rvView);
        swipeRefreshLayout = view.findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_blue_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

        mPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), mPosts);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("twitter client", "being refreshing");
                queryPosts();
            }
        });
    }

    protected void queryPosts(){
        adapter.clear();
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
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
