package com.cmpe451.interesthub.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.activities.LoginActivity;
import com.cmpe451.interesthub.activities.UserActivity;
import com.cmpe451.interesthub.adapters.UserFragmentsAdapter;
import com.cmpe451.interesthub.adapters.UserGroupListAdapter;
import com.cmpe451.interesthub.adapters.UserHomeGroupListAdapter;
import com.cmpe451.interesthub.adapters.UserHomeListAdapter;
import com.cmpe451.interesthub.adapters.UserProfileTabsAdapter;
import com.cmpe451.interesthub.models.Interest;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    InterestHub hub;
    Button userProfileGroups;
    Button userProfileTimeline;
    Button logout;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private UserProfileTabsAdapter viewPagerAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UserProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfile.
     */

    // TODO: Rename and change types and number of parameters
    public static UserProfile newInstance(String param1, String param2) {
        UserProfile fragment = new UserProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        hub = (InterestHub) getActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String img;
        String a;
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        TextView userName = view.findViewById(R.id.user_name);
        userName.setText(hub.getSessionController().getUser().getUsername());
        logout =  view.findViewById(R.id.logoutbutton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hub.getSessionController().setGroups(null);
                hub.getSessionController().setToken(null);
                hub.getSessionController().setUser(null);
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        if (!hub.getSessionController().getUser().getEmail().equals(null)) {
            TextView userEmail = view.findViewById(R.id.user_email);
            userEmail.setText(hub.getSessionController().getUser().getEmail());
        }
        if (!hub.getSessionController().getUser().getProfile().getAbout().equals(null)) {
            TextView userDesc = view.findViewById(R.id.user_desc);
            userDesc.setText(hub.getSessionController().getUser().getProfile().getAbout());
        }
        if (!hub.getSessionController().getUser().getProfile().getInterests().equals(null)) {
            TextView userInterests = view.findViewById(R.id.user_interests);
            String interest="";
            List<Interest> interestList =hub.getSessionController().getUser().getProfile().getInterests();
            for(Interest i : interestList)
                interest+= i .getLabel() +",";
            if(interest.length()>2)
                interest = interest.substring(0,interest.length()-1);
            userInterests.setText(interest);
        }
        //Print interests as list, tag adapter etc?
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        final ImageView profileImg = view.findViewById(R.id.profile_image);
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserActivity activiy = (UserActivity) getActivity();
                Runnable ppload = new Runnable() {
                    @Override
                    public void run() {
                        String a=hub.getSessionController().getUser().getProfile().getPhoto();
                        if (a!=null){
                            String img = hub.getSessionController().getUser().getProfile().getPhoto();
                            Picasso.with(getContext()).load(img).resize(200, 200).into(profileImg);
                        }
                    }
                };
                activiy.pickImage(ppload);
            }
        });

       // Picasso.with(getContext()).load("https://avatars1.githubusercontent.com/u/15267081?s=460&v=4").resize(200, 200).into(profileImg);
        a=hub.getSessionController().getUser().getProfile().getPhoto();
        if (a!=null){
            img = hub.getSessionController().getUser().getProfile().getPhoto();
             Picasso.with(getContext()).load(img).resize(200, 200).into(profileImg);
        }
        tabLayout = (TabLayout) view.findViewById(R.id.TabLayoutProfile);
        viewPager = (ViewPager) view.findViewById(R.id.ViewPagerProfile);
        viewPagerAdapter = new UserProfileTabsAdapter(getFragmentManager(),0);
     
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.removeAllTabs();
        final TabLayout.Tab home = tabLayout.newTab();
        final TabLayout.Tab followers = tabLayout.newTab();
        final TabLayout.Tab following = tabLayout.newTab();


        home.setText("My Posts");
        followers.setText("Followers");
        following.setText("Following");

        tabLayout.addTab(home,0);
        tabLayout.addTab(followers,1);
        tabLayout.addTab(following,2);



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.setTabTextColors(Color.BLACK,Color.BLACK);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        return view;
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
