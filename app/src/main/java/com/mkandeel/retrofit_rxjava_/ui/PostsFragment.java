package com.mkandeel.retrofit_rxjava_.ui;

import static com.mkandeel.retrofit_rxjava_.Helper.Constants.*;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mkandeel.retrofit_rxjava_.Adapters.CommentsAdapter;
import com.mkandeel.retrofit_rxjava_.Adapters.PostsAdapter;
import com.mkandeel.retrofit_rxjava_.ClickListener;
import com.mkandeel.retrofit_rxjava_.Helper.Direction;
import com.mkandeel.retrofit_rxjava_.Helper.Helper;
import com.mkandeel.retrofit_rxjava_.Pojo.CommentsModel;
import com.mkandeel.retrofit_rxjava_.Pojo.PostsModel;
import com.mkandeel.retrofit_rxjava_.R;
import com.mkandeel.retrofit_rxjava_.ViewModel.CommentsViewModel;
import com.mkandeel.retrofit_rxjava_.ViewModel.PostsViewModel;
import com.mkandeel.retrofit_rxjava_.databinding.FragmentPostsBinding;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment implements ClickListener {

    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private FragmentPostsBinding binding;
    private List<PostsModel> list;
    private PostsAdapter adapter;
    private PostsViewModel postsViewModel;
    private CommentsViewModel commentsViewModel;
    private Helper helper;
    private CommentsAdapter commentsAdapter;
    private List<CommentsModel> comments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        postsViewModel = new ViewModelProvider(this).get(PostsViewModel.class);
        commentsViewModel = new ViewModelProvider(this).get(CommentsViewModel.class);
        helper = Helper.getInstance(this);

        list = new ArrayList<>();
        comments = new ArrayList<>();
        commentsAdapter = new CommentsAdapter(requireContext(), comments, this);
        adapter = new PostsAdapter(this, list, this);
        binding.postsRv.setAdapter(adapter);
        getAllPosts();

        observer = new Observer<List<CommentsModel>>() {
            @Override
            public void onChanged(List<CommentsModel> commentsModels) {
                comments.addAll(commentsModels);
                commentsAdapter.notifyDataSetChanged();
            }
        };

        return binding.getRoot();
    }

    private Observer<List<CommentsModel>> observer;

    @SuppressLint("NotifyDataSetChanged")
    private void getAllPosts() {
        postsViewModel.getAllPosts();
        postsViewModel.getAllPostsData().observe(requireActivity(), new Observer<List<PostsModel>>() {
            @Override
            public void onChanged(List<PostsModel> postsModels) {
                list.addAll(postsModels);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void getCommentsForPost(int postId) {
        commentsViewModel.getComments(postId);
        commentsViewModel.getComments();
        commentsViewModel.getComments().observe(getViewLifecycleOwner(), observer);
    }

    private GestureDetector gestureDetector;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onItemClickListener(int position, @Nullable Bundle extra) {
        comments.clear();
        if (extra != null) {
            String clicked = extra.getString(CLICKEDKEY);
            //Toast.makeText(requireContext(), clicked, Toast.LENGTH_SHORT).show();
            if (clicked != null && !clicked.isEmpty()) {
                if (clicked.equals("CommentsClicked")) {
                    //comments = new ArrayList<>();
                    int postId = extra.getInt(POSTID);
                    Dialog dialog = helper.showDialog(requireContext(),
                            R.layout.comments_layout, Gravity.BOTTOM, true);
                    RecyclerView comments_rv = dialog.findViewById(R.id.comments_rv);
                    comments_rv.setAdapter(commentsAdapter);
                    View view = dialog.findViewById(R.id.view);
                    gestureDetector = new GestureDetector(requireContext(), new OnSwipeListener() {
                        @Override
                        public boolean onSwipe(Direction direction) {
                            if (direction == Direction.down) {
                                //do your stuff
                                dialog.dismiss();
                                dialog.cancel();
                                if (comments.size() > 0) {
                                    comments.clear();
                                    commentsAdapter.notifyDataSetChanged();
                                }
                                //comments = new ArrayList<>();
                            }
                            return true;
                        }

                    });
                    view.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            gestureDetector.onTouchEvent(event);
                            return true;
                        }
                    });

                    /*commentsViewModel.getComments(postId);
                    commentsViewModel.getComments()
                            .observe(requireActivity(), new Observer<List<CommentsModel>>() {
                                @Override
                                public void onChanged(List<CommentsModel> commentsModels) {
                                    //comments.addAll(commentsModels);
                                    for (CommentsModel model:commentsModels) {
                                        if (!comments.contains(model)) {
                                            comments.add(model);
                                        }
                                    }
                                    Log.i("List Data", "onChanged: " + comments.get(0).getBody());
                                    commentsAdapter.notifyDataSetChanged();
                                }
                            });*/
                    getCommentsForPost(postId);
                } else if (clicked.equals("itemClicked")) {
                    ViewPostFragment fragment = new ViewPostFragment();
                    FragmentManager manager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragmentContainerView, fragment)
                            .addToBackStack(null);
                    transaction.commit();
                } else if (clicked.equals("UserClicked")) {

                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onLongItemClickListener(int position, @Nullable Bundle extra) {
        if (extra != null) {
            String clicked = extra.getString(CLICKEDKEY);

        }
    }

    public class OnSwipeListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            // Grab two events located on the plane at e1=(x1, y1) and e2=(x2, y2)
            // Let e1 be the initial event
            // e2 can be located at 4 different positions, consider the following diagram
            // (Assume that lines are separated by 90 degrees.)
            //
            //
            //         \ A  /
            //          \  /
            //       D   e1   B
            //          /  \
            //         / C  \
            //
            // So if (x2,y2) falls in region:
            //  A => it's an UP swipe
            //  B => it's a RIGHT swipe
            //  C => it's a DOWN swipe
            //  D => it's a LEFT swipe
            //

            float x1 = e1.getX();
            float y1 = e1.getY();

            float x2 = e2.getX();
            float y2 = e2.getY();

            Direction direction = getDirection(x1, y1, x2, y2);
            return onSwipe(direction);
        }

        /**
         * Override this method. The Direction enum will tell you how the user swiped.
         */
        public boolean onSwipe(Direction direction) {
            return false;
        }

        /**
         * Given two points in the plane p1=(x1, x2) and p2=(y1, y1), this method
         * returns the direction that an arrow pointing from p1 to p2 would have.
         *
         * @param x1 the x position of the first point
         * @param y1 the y position of the first point
         * @param x2 the x position of the second point
         * @param y2 the y position of the second point
         * @return the direction
         */
        public Direction getDirection(float x1, float y1, float x2, float y2) {
            double angle = getAngle(x1, y1, x2, y2);
            return Direction.fromAngle(angle);
        }

        /**
         * Finds the angle between two points in the plane (x1,y1) and (x2, y2)
         * The angle is measured with 0/360 being the X-axis to the right, angles
         * increase counter clockwise.
         *
         * @param x1 the x position of the first point
         * @param y1 the y position of the first point
         * @param x2 the x position of the second point
         * @param y2 the y position of the second point
         * @return the angle between two points
         */
        public double getAngle(float x1, float y1, float x2, float y2) {

            double rad = Math.atan2(y1 - y2, x2 - x1) + Math.PI;
            return (rad * 180 / Math.PI + 180) % 360;
        }


        /**
         * Returns a direction given an angle.
         * Directions are defined as follows:
         * <p>
         * Up: [45, 135]
         * Right: [0,45] and [315, 360]
         * Down: [225, 315]
         * Left: [135, 225]
         *
         * @param angle an angle from 0 to 360 - e
         * @return the direction of an angle
         */

    }
}